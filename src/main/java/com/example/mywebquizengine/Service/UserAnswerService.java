package com.example.mywebquizengine.Service;


import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.output.SendAnswerResponse;
import com.example.mywebquizengine.Repos.UserQuizAnswerRepository;
import com.example.mywebquizengine.Repos.UserTestAnswerRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Calendar;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class UserAnswerService {

    @Autowired
    private UserQuizAnswerRepository userQuizAnswerRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserTestAnswerRepository userTestAnswerRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    /*public UserTestAnswer findLastUserTestAnswer(JobDataMap jobDataMap) {
        return userTestAnswerRepository.findLastUserAnswerEager(jobDataMap.getString("username")).get();
    }*/

    public UserTestAnswer findByUserAnswerId(Long userAnswerId) {

        Optional<UserTestAnswer> userTestAnswer = userTestAnswerRepository.findByUserAnswerId(userAnswerId);
        if (userTestAnswer.isPresent()) {
            return userTestAnswer.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    private void saveAnswer(UserTestAnswer userTestAnswer) {
        UserTestAnswer lastUserAnswer = userTestAnswerRepository.findById(userTestAnswer.getUserAnswerId()).get();
        lastUserAnswer.setCompletedAt(userTestAnswer.getCompletedAt());
        lastUserAnswer.setPercent(userTestAnswer.getPercent());
    }

    public Page<UserTestAnswer> getPageAnswersById(Long id, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return userTestAnswerRepository.getAnswersOnMyQuiz(id, paging);
    }

    @Transactional
    public UserTestAnswer getUserTestAnswerById(Long id) {

        return userTestAnswerRepository.findById(id).get();
    }

    @Transactional
    public Map<BigInteger, Double> getAnswerStats(Long id) {

        Test test = testService.findTest(id);
        ArrayList<Long> list = new ArrayList<>();

        for (Quiz quiz : test.getQuizzes()) {
            list.add(quiz.getQuizId());
        }

        List<Object[]> result = userQuizAnswerRepository.getAnswerStat(list);
        Map<BigInteger, Double> map = null;
        if (result != null && !result.isEmpty()) {
            map = new HashMap<>();
            for (Object[] object : result) {
                map.put(((BigInteger) object[0]), (Double) object[1]);
            }
        }
        return map;

    }

    public Double getStatistics(Integer id, Integer answer) {
        return ((double) userQuizAnswerRepository.getTrueAnswers(id, answer) / (double) userQuizAnswerRepository.getCountById(id, answer)) * 100;
    }

    public ArrayList<Long> getAnswersByTestId(Long id) {
        return (ArrayList<Long>) userTestAnswerRepository.getUserAnswersById(id);
    }

    public void saveStartAnswer(UserTestAnswer userTestAnswer) {
        userTestAnswerRepository.save(userTestAnswer);
    }

    public UserTestAnswer checkLastComplete(User user, Long id) {
        Optional<UserTestAnswer> lastUserTestAnswer = userTestAnswerRepository.findLastUserTestAnswer(user.getUserId(), id);
        if (lastUserTestAnswer.isPresent()) {
            return lastUserTestAnswer.get();
        } else return null;

    }

    public boolean checkComplete(Long testId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long countComplete = userTestAnswerRepository.getCountOfCompleteAttempts(user.getUserId(), testId);
        return countComplete >= 1;
    }

    @Autowired
    private CourseService courseService;

    public Integer getPercentageOfComplete(Long courseId) {
        int size = courseService.findCourseById(courseId).getTests().size();
        if (size == 0) {
            return 0;
        } else {
            return userTestAnswerRepository.getPercentageOfComplete(courseId);
        }
    }

    public boolean isAvailable(Test test, Long userId) {
        boolean available = true;
        Integer userAttempts = userTestAnswerRepository.getUserAttempts(test.getTestId(), userId);
        if (test.getAttempts() != null && userAttempts >= test.getAttempts()) {
            available = false;
        }

        Calendar now = new GregorianCalendar();
        if (test.getStartTime() != null) {
            if (now.before(test.getStartTime())) {
                available = false;
            }
        }

        if (test.getEndTime() != null) {
            Calendar endTimePlusOneMinute = test.getEndTime();
            endTimePlusOneMinute.add(Calendar.MINUTE, 1);
            if (now.after(endTimePlusOneMinute)) {
                available = false;
            }
        }

        return available;
    }

    public void checkAnswer(Long userTestAnswerId) {
        UserTestAnswer userTestAnswer = findByUserAnswerId(userTestAnswerId);
        Test test = userTestAnswer.getTest();
        StringBuilder result = new StringBuilder();
        List<UserQuizAnswer> userQuizAnswers = new ArrayList<>();
        int trueAnswers = 0;

        for (int i = 0; i < test.getQuizzes().size(); i++) {
            UserQuizAnswer quizAnswer = new UserQuizAnswer();
            AnswerChecker answerChecker = new AnswerChecker();

            Quiz quiz = quizService.findQuiz(test.getQuizzes().get(i).getQuizId());
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
            } else if (quiz instanceof MapAnswerQuiz) {
                quizAnswer = new MapUserAnswerQuiz();
                MapUserAnswerQuiz mapUserAnswerQuiz = (MapUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i);
                answerChecker.checkAnswer(mapUserAnswerQuiz);
                ((MapUserAnswerQuiz) quizAnswer).setAnswer(((MapUserAnswerQuiz) userTestAnswer.getUserQuizAnswers().get(i)).getAnswer());
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

        double percent = ((double) trueAnswers / (double) test.getQuizzes().size()) * 100.0;

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        Calendar nowDate = new GregorianCalendar();
        nowDate.setTimeZone(timeZone);

        userTestAnswer.setUserQuizAnswers(userQuizAnswers);
        userTestAnswer.setPercent(percent);
        userTestAnswer.setCompletedAt(nowDate);
        saveAnswer(userTestAnswer);

        String destination = "/topic/" + userTestAnswer.getUser().getUserId() + userTestAnswer.getUserAnswerId();

        char[] charsResult = result.toString().toCharArray();
        SendAnswerResponse sendAnswerResponse = new SendAnswerResponse();
        sendAnswerResponse.setPercent(percent);
        if (test.isDisplayAnswers()) {
            sendAnswerResponse.setResult(charsResult);
        }

        simpMessagingTemplate.convertAndSend(destination, sendAnswerResponse);
    }

    public void updateAnswer(Long quizId, Object answer, Long userId) {
        Quiz quiz = quizService.findQuiz(quizId);
        UserTestAnswer lastUserAnswer = userTestAnswerRepository.findLastUserAnswer(userId).get();

        Long userQuizAnswerId = null;
        for (UserQuizAnswer quizAnswer : lastUserAnswer.getUserQuizAnswers()) {
            if (quizAnswer.getQuiz().getQuizId().equals(quizId)) {
                userQuizAnswerId = quizAnswer.getQuizAnswerId();
            }
        }

        UserQuizAnswer userQuizAnswer;
        if (userQuizAnswerId != null) {
            userQuizAnswer = userQuizAnswerRepository.findById(userQuizAnswerId).get();
            if (quiz instanceof MultipleAnswerQuiz) {
                ((MultipleUserAnswerQuiz) userQuizAnswer).setAnswer((List<Integer>) answer);
            } else if (quiz instanceof StringAnswerQuiz) {
                ((StringUserAnswerQuiz) userQuizAnswer).setAnswer((String) answer);
            } else if (quiz instanceof MapAnswerQuiz) {
                ((MapUserAnswerQuiz) userQuizAnswer).setAnswer((HashMap<String, String>) answer);
            }
        } else {
            if (quiz instanceof MultipleAnswerQuiz) {
                MultipleUserAnswerQuiz multipleUserAnswerQuiz = new MultipleUserAnswerQuiz();
                multipleUserAnswerQuiz.setQuiz(quiz);
                multipleUserAnswerQuiz.setAnswer((List<Integer>) answer);

                userQuizAnswer = multipleUserAnswerQuiz;
                lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
            } else if (quiz instanceof StringAnswerQuiz) {
                StringUserAnswerQuiz stringUserAnswerQuiz = new StringUserAnswerQuiz();
                stringUserAnswerQuiz.setQuiz(quiz);
                stringUserAnswerQuiz.setAnswer((String) answer);

                userQuizAnswer = stringUserAnswerQuiz;
                lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
            } else if (quiz instanceof MapAnswerQuiz) {
                MapUserAnswerQuiz mapUserAnswerQuiz = new MapUserAnswerQuiz();
                mapUserAnswerQuiz.setQuiz(quiz);
                mapUserAnswerQuiz.setAnswer((HashMap<String, String>) answer);

                userQuizAnswer = mapUserAnswerQuiz;
                lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
            }
        }

        userTestAnswerRepository.save(lastUserAnswer);
    }

    public String startAnswer(Long testId, String restore, Long userId) throws SchedulerException {
        Test test = testService.findTest(testId);
        UserTestAnswer userTestAnswer;
        if (isAvailable(test, userId)) {
            UserTestAnswer lastUserTestAnswer = checkLastComplete(userService.loadUserByUserIdProxy(userId), testId);

            if (Boolean.parseBoolean(restore) && lastUserTestAnswer != null && lastUserTestAnswer.getCompletedAt() == null) {
                userTestAnswer = lastUserTestAnswer;
            } else {
                userTestAnswer = new UserTestAnswer();
                Calendar calendar = new GregorianCalendar();
                userTestAnswer.setStartAt(calendar);
                userTestAnswer.setTest(testService.findTest(testId));
                userTestAnswer.setUser(userService.loadUserByUserIdProxy(userId));
                saveStartAnswer(userTestAnswer);

                if (test.getDuration() != null) {

                    Calendar jobCalendar = new GregorianCalendar();
                    jobCalendar.set(Calendar.SECOND, jobCalendar.get(Calendar.SECOND) + test.getDuration().getSecond());
                    jobCalendar.set(Calendar.MINUTE, jobCalendar.get(Calendar.MINUTE) + test.getDuration().getMinute());
                    jobCalendar.set(Calendar.HOUR, jobCalendar.get(Calendar.HOUR) + test.getDuration().getHour());

                    if (test.getEndTime() != null) {
                        Calendar nowTimePlusDuration = new GregorianCalendar();
                        nowTimePlusDuration.add(Calendar.SECOND, test.getDuration().getSecond());
                        nowTimePlusDuration.add(Calendar.MINUTE, test.getDuration().getMinute());
                        nowTimePlusDuration.add(Calendar.HOUR, test.getDuration().getHour());

                        if (nowTimePlusDuration.after(test.getEndTime())) {
                            jobCalendar = test.getEndTime();
                        }
                    }

                    SchedulerFactory sf = new StdSchedulerFactory();
                    Scheduler scheduler = sf.getScheduler();

                    JobDetail job = newJob(SimpleJob.class)
                            .withIdentity(UUID.randomUUID().toString(), "group1")
                            .usingJobData("username", userService.loadUserByUserIdProxy(userId).getUsername())
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
}
