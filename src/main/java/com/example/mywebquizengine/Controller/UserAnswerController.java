package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.dto.UserTestAnswerRequest;
import com.example.mywebquizengine.Repos.UserQuizAnswerRepository;
import com.example.mywebquizengine.Repos.UserTestAnswerRepository;
import com.example.mywebquizengine.Service.QuizService;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserAnswerService;
import com.example.mywebquizengine.Service.UserService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
@Validated
public class UserAnswerController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestAnswerRepository userTestAnswerRepository;

    @Autowired
    private UserQuizAnswerRepository userQuizAnswerRepository;

    // Проверка наличия неотправленных ответов
    @GetMapping(path = "/checklastanswer/{id}")
    @ResponseBody
    public Boolean checkLastAnswer(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        if (userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id) != null) {
            return userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id).getCompletedAt() == null;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    @ResponseBody
    @Transactional
    public void getAnswerOnTest(@PathVariable String id,
                                @RequestBody UserTestAnswer userAnswerId) {

        UserTestAnswer userTestAnswer = userAnswerService.findByUserAnswerId(userAnswerId.getUserAnswerId());
        //UserTestAnswer userTestAnswer = userTestAnswerRepository.findLastUserAnswer("application").get();
        StringBuilder result = new StringBuilder();
        List<UserQuizAnswer> userQuizAnswers = new ArrayList<>();
        int trueAnswers = 0;

        for (int i = 0; i < userTestAnswer.getTest().getQuizzes().size(); i++) {
            UserQuizAnswer quizAnswer = new UserQuizAnswer();
            AnswerChecker answerChecker = new AnswerChecker();

            Quiz quiz = quizService.findQuiz(userTestAnswer.getTest().getQuizzes().get(i).getQuizId());
            answerChecker.quiz = quiz;

            if (quiz instanceof MultipleAnswerQuiz) {
                quizAnswer = new MultipleUserAnswerQuiz();
                MultipleUserAnswerQuiz userQuizAnswer = (MultipleUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i);
                answerChecker.checkAnswer(userQuizAnswer);
                ((MultipleUserAnswerQuiz) quizAnswer).setAnswer(((MultipleUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i)).getAnswer());
            } else if (quiz instanceof StringAnswerQuiz) {
                quizAnswer = new StringUserAnswerQuiz();
                List<UserQuizAnswer> answers = userTestAnswer.getUserQuizAnswers();
                if (answers == null) {
                    StringUserAnswerQuiz answerQuiz = new StringUserAnswerQuiz();
                    answerQuiz.setAnswer("");
                    answerChecker.checkAnswer(answerQuiz);
                } else {
                    answerChecker.checkAnswer(answers.get(i));
                }
                ((StringUserAnswerQuiz) quizAnswer).setAnswer(((StringUserAnswerQuiz) answers.get(i)).getAnswer());
            }

            quizAnswer.setQuiz(quiz);
            quizAnswer.setStatus(answerChecker.isSuccess());
            userQuizAnswers.add(quizAnswer);

            if (quizAnswer.getStatus()) {
                result.append("1");
                trueAnswers++;
            } else {
                result.append("0");
            }
        }

        userTestAnswer.setUserQuizAnswers(userQuizAnswers);

        userTestAnswer.setPercent(((double) trueAnswers / (double) result.length()) * 100.0);

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        Calendar nowDate = new GregorianCalendar();
        nowDate.setTimeZone(timeZone);

        userTestAnswer.setCompletedAt(nowDate);

        userAnswerService.saveAnswer(userTestAnswer);

        String destination =  "/topic/" + userTestAnswer.getUser().getUsername() + userTestAnswer.getUserAnswerId();
        System.out.println("AAA");
        System.out.println(destination);

        simpMessagingTemplate.convertAndSend(destination, result.toString().toCharArray());
    }

    @GetMapping(path = "/quizzes/{id}/solve")
    public String passTest(@PathVariable Long id,
                           @RequestParam(required = false, defaultValue = "false") String restore,
                           @AuthenticationPrincipal Principal principal) throws SchedulerException {


        Test test = testService.findTest(id);
        UserTestAnswer userTestAnswer;
        if (userAnswerService.isAvailable(test, principal.getName())) {
            UserTestAnswer lastUserTestAnswer = userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id);

            if (Boolean.parseBoolean(restore) && lastUserTestAnswer != null && lastUserTestAnswer.getCompletedAt() == null) {
                userTestAnswer = lastUserTestAnswer;
            } else {
                userTestAnswer = new UserTestAnswer();
                Calendar calendar = new GregorianCalendar();
                userTestAnswer.setStartAt(calendar);
                userTestAnswer.setTest(testService.findTest(id));
                userTestAnswer.setUser(userService.loadUserByUsernameProxy(principal.getName()));
                userAnswerService.saveStartAnswer(userTestAnswer);

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
                            .usingJobData("test", test.getTestId())
                            .usingJobData("answer", userTestAnswer.getUserAnswerId())
                            .build();


                    CronTrigger trigger = newTrigger()
                            .withIdentity(UUID.randomUUID().toString(), "group1")
                            .withSchedule(cronSchedule(jobCalendar.get(Calendar.SECOND) + " " +
                                    jobCalendar.get(Calendar.MINUTE) + " " +
                                    jobCalendar.get(Calendar.HOUR_OF_DAY) + " " +
                                    jobCalendar.get(Calendar.DAY_OF_MONTH) + " " +
                                    (jobCalendar.get(Calendar.MONTH) + 1) + " ? " +
                                    jobCalendar.get(Calendar.YEAR)))
                            .build();

                    scheduler.scheduleJob(job, trigger);

                    System.out.println("Задание выполнится в: " + jobCalendar.getTime());

                    scheduler.start();
                }
            }
        } else return "redirect:/courses";

        return "redirect:/test/" + test.getTestId() + "/" + userTestAnswer.getUserAnswerId() + "/solve/";
    }


    @GetMapping(value = "/test/{testId}/{userTestAnswerId}/solve/")
    public String startAnswer(@PathVariable Long testId, @PathVariable Long userTestAnswerId, Model model) {
        UserTestAnswer userTestAnswer = userTestAnswerRepository.findByUserAnswerId(userTestAnswerId).get();

        if (userTestAnswer.getCompletedAt() != null) {
            return "redirect:/courses";
        } else {
            model.addAttribute("lastAnswer", userTestAnswer);
            Test test = testService.findTest(testId);
            model.addAttribute("test_id", test);

            if (test.getDuration() != null) {
                Calendar userTestAnswerStartAt = userTestAnswer.getStartAt();
                Calendar userTestAnswerFinishAt = new GregorianCalendar();
                userTestAnswerFinishAt.setTime(userTestAnswerStartAt.getTime());
                userTestAnswerFinishAt.set(Calendar.SECOND, userTestAnswerFinishAt.get(Calendar.SECOND) + test.getDuration().getSecond());
                userTestAnswerFinishAt.set(Calendar.MINUTE, userTestAnswerFinishAt.get(Calendar.MINUTE) + test.getDuration().getMinute());
                userTestAnswerFinishAt.set(Calendar.HOUR, userTestAnswerFinishAt.get(Calendar.HOUR) + test.getDuration().getHour());
                model.addAttribute("timeout", userTestAnswerFinishAt.getTime());
            }

            return "answer";
        }
    }


    @PostMapping(value = "/answersession/{id}")
    public void getAnswerSession(@AuthenticationPrincipal Principal principal, @Valid @RequestBody UserTestAnswerRequest request, @PathVariable String id) {
        Quiz quiz = quizService.findQuiz(request.getQuizId());
        UserTestAnswer lastUserAnswer = userTestAnswerRepository.findLastUserAnswer(principal.getName()).get();

        Long userQuizAnswerId = null;
        for (UserQuizAnswer quizAnswer : lastUserAnswer.getUserQuizAnswers()) {
            if (quizAnswer.getQuiz().getQuizId().equals(request.getQuizId())) {
                userQuizAnswerId = quizAnswer.getQuizAnswerId();
            }
        }

        UserQuizAnswer userQuizAnswer;
        if (userQuizAnswerId != null) {
            userQuizAnswer = userQuizAnswerRepository.findById(userQuizAnswerId).get();
            if (quiz instanceof MultipleAnswerQuiz) {
                ((MultipleUserAnswerQuiz) userQuizAnswer).setAnswer((List<Integer>) request.getAnswer());
            } else if (quiz instanceof StringAnswerQuiz) {
                ((StringUserAnswerQuiz) userQuizAnswer).setAnswer((String) request.getAnswer());
            }
        } else {
            if (quiz instanceof MultipleAnswerQuiz) {
                MultipleUserAnswerQuiz multipleUserAnswerQuiz = new MultipleUserAnswerQuiz();
                multipleUserAnswerQuiz.setQuiz(quiz);
                multipleUserAnswerQuiz.setAnswer((List<Integer>) request.getAnswer());

                userQuizAnswer = multipleUserAnswerQuiz;
                lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
            } else if (quiz instanceof StringAnswerQuiz) {
                StringUserAnswerQuiz stringUserAnswerQuiz = new StringUserAnswerQuiz();
                stringUserAnswerQuiz.setQuiz(quiz);
                stringUserAnswerQuiz.setAnswer((String) request.getAnswer());

                userQuizAnswer = stringUserAnswerQuiz;
                lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
            }
        }

        userTestAnswerRepository.save(lastUserAnswer);
        throw new ResponseStatusException(HttpStatus.OK);
    }

}
