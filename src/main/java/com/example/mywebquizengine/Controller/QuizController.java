package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Test.SimpleJob;
import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.QuizService;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserAnswerService;
import com.example.mywebquizengine.Service.UserService;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.Calendar;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Validated
@Controller
@Service
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;


    @GetMapping(path = "/quizzes")
    public String getQuizzes(Model model, @RequestParam(required = false,defaultValue = "0") @Min(0) Integer page,
                             @RequestParam(required = false,defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) throws IOException {

        Page<Test> page1 = testService.getAllQuizzes(page, pageSize, sortBy);


        model.addAttribute("test", page1.getContent());
        return "getAllQuiz";
    }

    @GetMapping(path = "/myquiz")
    public String getMyQuizzes(@AuthenticationPrincipal Principal principal, Model model,
                               @RequestParam(required = false,defaultValue = "0") @Min(0) Integer page,
                               @RequestParam(required = false,defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy) {

        User user = userService.loadUserByUsernameProxy(principal.getName());
        Page<Test> page1 = testService.getMyQuiz(user.getUsername(), page, pageSize, sortBy);
        model.addAttribute("myquiz", page1.getContent());

        return "myquiz";
    }

    // Проверка наличия неотправленных ответов
    @GetMapping(path = "/checklastanswer/{id}")
    @ResponseBody
    public Boolean checkLastAnswer(@PathVariable String id, @AuthenticationPrincipal Principal principal) {
        if (userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id) != null) {
            return userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id).getCompletedAt() == null;
        } else {
            return false;
        }
    }



    @GetMapping(path = "/add")
    public String addQuiz(Model model) {
        return "addQuiz";
    }


    @PostMapping(path = "/quizzes", consumes={"application/json"})
    public String addQuiz(Model model, @RequestBody @Valid Test test, @AuthenticationPrincipal Principal principal) throws ResponseStatusException {
        try {
            User user = userService.loadUserByUsernameProxy(principal.getName());

            user.setRoles(new ArrayList<>());
            test.setDuration(test.getDuration());
            test.setUser(user);
            for (int i = 0; i < test.getQuizzes().size(); i++) {
                test.getQuizzes().get(i).setTest(test);
            }
            testService.saveTest(test);

            return "redirect:/";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping(path = "/")
    public String home(Model model, HttpServletRequest request) {


        return "home";
    }





    @PostMapping(path = "/quizzes/{id}/solve")
    @ResponseBody
    @Transactional
    public void getAnswerOnTest(@PathVariable String id,
                                  @RequestBody UserTestAnswer userAnswerId) {

        UserTestAnswer userTestAnswer = userAnswerService.findByUserAnswerId(userAnswerId.getUserAnswerId());


        StringBuilder result = new StringBuilder();
        List<UserQuizAnswer> userQuizAnswers = new ArrayList<>();
        int trueAnswers = 0;

        for (int i = 0; i < userTestAnswer.getTest().getQuizzes().size(); i++) {
            UserQuizAnswer quizAnswer = new UserQuizAnswer();
            AnswerChecker answerChecker = new AnswerChecker();

            /*if (userTestAnswer.getTest().getQuizzes() == null) {
                userTestAnswer.getTest().setQuizzes(new ArrayList<>());
            }*/

            answerChecker.quiz = quizService.findQuiz(userTestAnswer.getTest().getQuizzes().get(i).getId());

            quizAnswer.setQuiz(answerChecker.quiz);

            if (userTestAnswer.getUserQuizAnswers().size() == 0) {
                answerChecker.checkAnswer(new ArrayList<>()); // из за Persistence bag (нельзя сделать get(i))
                quizAnswer.setAnswer(new ArrayList<>());
            } else {
                answerChecker.checkAnswer(userTestAnswer.getUserQuizAnswers().get(i).getAnswer());
                quizAnswer.setAnswer(userTestAnswer.getUserQuizAnswers().get(i).getAnswer());
            }

            quizAnswer.setStatus(answerChecker.isSuccess());



            userQuizAnswers.add(quizAnswer);



            if (quizAnswer.getStatus().toString().equals("true")) {
                result.append("1");
                trueAnswers++;
            } else {
                result.append("0");
            }
        }

        userTestAnswer.setUserQuizAnswers(userQuizAnswers);

        userTestAnswer.setPercent(((double) trueAnswers/(double)result.length()) * 100.0);

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        Calendar nowDate = new GregorianCalendar();
        nowDate.setTimeZone(timeZone);

        /*if (userTestAnswer.getCompletedAt() == null) {*/
            simpMessagingTemplate.convertAndSend("/topic/" +
                    userTestAnswer.getUser().getUsername() + userTestAnswer.getUserAnswerId(), result.toString().toCharArray());
            userTestAnswer.setCompletedAt(nowDate);
//        }



        userAnswerService.saveAnswer(userTestAnswer);


        //return String.valueOf(result);
    }




    @GetMapping(path = "/quizzes/{id}/solve")
    public String passTest(Model model, @PathVariable String id,
                           @RequestParam(required = false, defaultValue = "false") String restore,
                           @AuthenticationPrincipal Principal principal) throws SchedulerException {

        Test test = testService.findTest(Integer.parseInt(id));
        UserTestAnswer userTestAnswer;

        UserTestAnswer lastUserTestAnswer = userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id);

        if (Boolean.parseBoolean(restore) && lastUserTestAnswer != null && lastUserTestAnswer.getCompletedAt() == null
                ) {

            model.addAttribute("lastAnswer", lastUserTestAnswer);

            if (test.getDuration() != null) {
                Calendar calendar2 = lastUserTestAnswer.getStartAt();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(calendar2.getTime());
                calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + test.getDuration().getSecond());
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + test.getDuration().getMinute());
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + test.getDuration().getHour());

                model.addAttribute("timeout", calendar.getTime());
            }

        } else {
            userTestAnswer = new UserTestAnswer();
            Calendar calendar = new GregorianCalendar();
            userTestAnswer.setStartAt(calendar);
            userTestAnswer.setTest(testService.findTest(Integer.parseInt(id)));
            userTestAnswer.setUser(userService.loadUserByUsernameProxy(principal.getName()));
            userAnswerService.saveStartAnswer(userTestAnswer);

            model.addAttribute("lastAnswer", userTestAnswer);


            if (test.getDuration() != null) {
                Calendar jobCalendar = new GregorianCalendar();
                jobCalendar.set(Calendar.SECOND, jobCalendar.get(Calendar.SECOND) + test.getDuration().getSecond());
                jobCalendar.set(Calendar.MINUTE, jobCalendar.get(Calendar.MINUTE) + test.getDuration().getMinute());
                jobCalendar.set(Calendar.HOUR, jobCalendar.get(Calendar.HOUR) + test.getDuration().getHour());


                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler scheduler = sf.getScheduler();

                JobDetail job = newJob(SimpleJob.class)
                        .withIdentity(UUID.randomUUID().toString(), "group1")
                        .usingJobData("username", userService.loadUserByUsernameProxy(principal.getName()).getUsername())
                        .usingJobData("test", test.getId())
                        .usingJobData("answer", userTestAnswer.getUserAnswerId())
                        .build();


                CronTrigger trigger = newTrigger()
                        .withIdentity(UUID.randomUUID().toString(), "group1")
                        .withSchedule(cronSchedule(jobCalendar.get(Calendar.SECOND) + " " +
                                jobCalendar.get(Calendar.MINUTE) + " " +
                                jobCalendar.get(Calendar.HOUR_OF_DAY) + " " +
                                jobCalendar.get(Calendar.DAY_OF_MONTH) + " " +
                                (jobCalendar.get(Calendar.MONTH) + 1) + " ? "+
                                jobCalendar.get(Calendar.YEAR)))
                        .build();

                scheduler.scheduleJob(job, trigger);

                System.out.println("Задание выполнится в: " + jobCalendar.getTime());

                model.addAttribute("timeout", jobCalendar.getTime());

                scheduler.start();

            }


        }



        model.addAttribute("test_id", test);
        return "answer";
    }



    @GetMapping("/reg")
    public String login(Map<String, Object> model) {
        return "reg";
    }

    @DeleteMapping(path = "/quizzes/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public void deleteTest(@PathVariable Integer id, @AuthenticationPrincipal Principal principal) {
        testService.deleteTest(id);
    }


    @PutMapping(path = "/update/{id}", consumes={"application/json"})
    @ResponseBody
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public void changeTest(@PathVariable Integer id, @Valid @RequestBody Test test,
                           @AuthenticationPrincipal Principal principal) throws ResponseStatusException {


        testService.updateTest(id, test);

    }



    @GetMapping(path = "/update/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String update(@PathVariable Integer id, Model model, @AuthenticationPrincipal Principal principal) {

        Test tempTest = testService.findTest(id);
        model.addAttribute("oldTest", tempTest);
        return "updateQuiz";

    }





    @GetMapping(path = "/quizzes/{id}/info/")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String getInfo(@PathVariable Integer id, Model model,
                          @RequestParam(required = false,defaultValue = "0") @Min(0) Integer page,
                          @RequestParam(required = false,defaultValue = "2000") @Min(1) @Max(2000) Integer pageSize,
                          @RequestParam(defaultValue = "completed_at") String sortBy, @AuthenticationPrincipal Principal principal) {

        Test test = testService.findTest(id);
        model.addAttribute("quizzes", test.getQuizzes());
        model.addAttribute("chart", userAnswerService.getAnswerStats(id));
        System.out.println(userAnswerService.getAnswerStats(id));
        model.addAttribute("answersOnQuiz", userAnswerService.getPageAnswersById(id, page, pageSize, sortBy).getContent());
        return "info";
    }

    @PostMapping(value = "/answersession/{id}")
    public void getAnswerSession(@AuthenticationPrincipal Principal principal,@RequestBody UserTestAnswer userTestAnswer, @PathVariable String id) {
        User user = userService.loadUserByUsernameProxy(principal.getName());

        userTestAnswer.setUser(user);
        userTestAnswer.setTest(testService.findTest(Integer.parseInt(id)));
        userAnswerService.saveTempAnswer(userTestAnswer);
        throw new ResponseStatusException(HttpStatus.OK);
    }
}
