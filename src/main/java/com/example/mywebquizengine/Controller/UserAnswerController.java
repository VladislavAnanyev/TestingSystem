package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.UserTestAnswerRequest;
import com.example.mywebquizengine.Service.QuizService;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserAnswerService;
import com.example.mywebquizengine.Service.UserService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Calendar;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
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

            Quiz quiz = quizService.findQuiz(userTestAnswer.getTest().getQuizzes().get(i).getQuizId());
            answerChecker.quiz = quiz;

            if (quiz.getType().equals("MULTIPLE")) {
                quizAnswer = new MultipleUserAnswerQuiz();
                MultipleUserAnswerQuiz userQuizAnswer = (MultipleUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i);
                answerChecker.checkAnswer(userQuizAnswer);
                ((MultipleUserAnswerQuiz) quizAnswer).setAnswer(((MultipleUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i)).getAnswer());
            } else if (quiz.getType().equals("STRING")) {
                quizAnswer = new StringUserAnswerQuiz();
                answerChecker.checkAnswer(userTestAnswer.getUserQuizAnswers().get(i));
                ((StringUserAnswerQuiz) quizAnswer).setAnswer(((StringUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i)).getAnswer());
            }
            quizAnswer.setQuiz(quiz);
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

        userTestAnswer.setPercent(((double) trueAnswers / (double) result.length()) * 100.0);

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        Calendar nowDate = new GregorianCalendar();
        nowDate.setTimeZone(timeZone);

        userTestAnswer.setCompletedAt(nowDate);

        userAnswerService.saveAnswer(userTestAnswer);

        simpMessagingTemplate.convertAndSend("/topic/" +
                userTestAnswer.getUser().getUsername() + userTestAnswer.getUserAnswerId(), result.toString().toCharArray());
    }

    @GetMapping(path = "/quizzes/{id}/solve")
    public String passTest(Model model, @PathVariable String id,
                           @RequestParam(required = false, defaultValue = "false") String restore,
                           @AuthenticationPrincipal Principal principal) throws SchedulerException {

        Test test = testService.findTest(Integer.parseInt(id));
        UserTestAnswer userTestAnswer;

        UserTestAnswer lastUserTestAnswer = userAnswerService.checkLastComplete(userService.loadUserByUsernameProxy(principal.getName()), id);

        if (Boolean.parseBoolean(restore) && lastUserTestAnswer != null && lastUserTestAnswer.getCompletedAt() == null) {

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

                model.addAttribute("timeout", jobCalendar.getTime());

                scheduler.start();

            }
        }

        model.addAttribute("test_id", test);
        return "answer";
    }

    @PostMapping(value = "/answersession/{id}")
    public void getAnswerSession(@AuthenticationPrincipal Principal principal, @RequestBody UserTestAnswerRequest request, @PathVariable String id) {
        User user = userService.loadUserByUsernameProxy(principal.getName());
        UserTestAnswer userTestAnswer = new UserTestAnswer();

        Quiz quiz = quizService.findQuiz(request.getQuizId());
        if (quiz.getType().equals("MULTIPLE")) {
            MultipleUserAnswerQuiz multipleUserAnswerQuiz = new MultipleUserAnswerQuiz();
            multipleUserAnswerQuiz.setQuiz(quiz);
            multipleUserAnswerQuiz.setAnswer((List<Integer>) request.getAnswer());
            userTestAnswer.setUserQuizAnswers(Collections.singletonList(multipleUserAnswerQuiz));
        } else if (quiz.getType().equals("STRING")) {
            StringUserAnswerQuiz stringUserAnswerQuiz = new StringUserAnswerQuiz();
            stringUserAnswerQuiz.setAnswer((String) request.getAnswer());
            stringUserAnswerQuiz.setQuiz(quiz);
            userTestAnswer.setUserQuizAnswers(Collections.singletonList(stringUserAnswerQuiz));
        }
        userTestAnswer.setUser(user);
        userTestAnswer.setTest(testService.findTest(Integer.parseInt(id)));
        userAnswerService.saveTempAnswer(userTestAnswer);
    }

}
