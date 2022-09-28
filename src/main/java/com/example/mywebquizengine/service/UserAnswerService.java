package com.example.mywebquizengine.service;


import com.example.mywebquizengine.model.projection.AnswerViewForInfo;
import com.example.mywebquizengine.model.projection.UserTestAnswerView;
import com.example.mywebquizengine.model.test.*;
import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.output.SendAnswerResponse;
import com.example.mywebquizengine.repos.UserQuizAnswerRepository;
import com.example.mywebquizengine.repos.UserTestAnswerRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.*;
import java.util.stream.Collectors;

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
    
    @Autowired
    private CourseService courseService;

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

    public List<AnswerViewForInfo> getPageAnswersById(Long id, Long groupId) {
        if (groupId == null) {
            return userTestAnswerRepository.getAnswersOnMyQuiz(id);
        } else {
            return userTestAnswerRepository.getAnswersOnMyQuiz(id, groupId);
        }
    }

    @Transactional
    public UserTestAnswer getUserTestAnswerById(Long id) {

        return userTestAnswerRepository.findById(id).get();
    }

    @Transactional
    public Map<BigInteger, Double> getAnswerStats(Long id, Long groupId) {

        Test test = testService.findTest(id);
        ArrayList<Long> list = test.getQuizzes().stream().map(Quiz::getQuizId).collect(Collectors.toCollection(ArrayList::new));

        List<Object[]> result;
        if (groupId == null) {
            result = userQuizAnswerRepository.getAnswerStat(list);
        } else {
            result = userQuizAnswerRepository.getAnswerStat(list, groupId);
        }
        Map<BigInteger, Double> map = null;
        if (result != null && !result.isEmpty()) {
            map = new HashMap<>();
            for (Object[] object : result) {
                map.put(((BigInteger) object[0]), (Double) object[1]);
            }
        }
        return map;
    }

    @Transactional
    public Map<BigInteger, Double> getTimeAnswerStats(Long id, Long groupId) {

        Test test = testService.findTest(id);
        ArrayList<Long> list = test.getQuizzes().stream().map(Quiz::getQuizId).collect(Collectors.toCollection(ArrayList::new));

        List<Object[]> result;
        if (groupId == null) {
            result = userQuizAnswerRepository.getTimeAnswerStat(list);
        } else {
            result = userQuizAnswerRepository.getTimeAnswerStat(list, groupId);
        }
        System.out.println(list);
        Map<BigInteger, Double> map = null;
        if (result != null && !result.isEmpty()) {
            map = new HashMap<>();
            for (Object[] object : result) {
                map.put(((BigInteger) object[0]), (Double) object[1]);
            }
        }
        return map;

    }

    public List<UserTestAnswerView> getAnswerStat(Long id, Long groupId) {
        if (groupId == null) {
            return userTestAnswerRepository.findAllByTestTestIdAndPercentIsNotNull(id);
        } else {
            return userTestAnswerRepository.findAllByTestTestIdAndPercentIsNotNullAndUserGroupGroupId(id, groupId);
        }
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
        return lastUserTestAnswer.orElse(null);
    }

    public boolean checkComplete(Long testId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long countComplete = userTestAnswerRepository.getCountOfCompleteAttempts(user.getUserId(), testId);
        return countComplete >= 1;
    }

    public Integer getPercentageOfComplete(Long courseId, Long userId) {
        if (userId == null) {
            userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        }
        int size = courseService.findCourseById(courseId).getTests().size();
        if (size == 0) {
            return 0;
        } else {
            return userTestAnswerRepository.getPercentageOfComplete(courseId, userId);
        }
    }

    private boolean isAvailable(Test test, Long userId) {
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
            if (now.after(test.getEndTime())) {
                available = false;
            }
        }

        return available;
    }

    public void checkAnswer(Long userTestAnswerId, Long authUserId) {
        UserTestAnswer userTestAnswer = findByUserAnswerId(userTestAnswerId);
        if (authUserId.equals(userTestAnswer.getUser().getUserId())) {
            Test test = userTestAnswer.getTest();
            StringBuilder result = new StringBuilder();
            List<UserQuizAnswer> userQuizAnswers = new ArrayList<>();
            int trueAnswers = 0;

            for (int i = 0; i < test.getQuizzes().size(); i++) {

                UserQuizAnswer quizAnswer = null;
                for (UserQuizAnswer userQuizAnswer : userTestAnswer.getUserQuizAnswers()) {
                    if (userQuizAnswer.getQuiz().getQuizId().equals(test.getQuizzes().get(i).getQuizId())) {
                        quizAnswer = userQuizAnswer;
                    }
                }

                AnswerChecker answerChecker = new AnswerChecker();
                Quiz quiz = quizService.findQuiz(test.getQuizzes().get(i).getQuizId());
                answerChecker.quiz = quiz;
                answerChecker.checkAnswer(userTestAnswer.getUserQuizAnswers().get(i));

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
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
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

        UserQuizAnswer userQuizAnswer = userQuizAnswerRepository.findById(userQuizAnswerId).get();
            if (quiz instanceof MultipleAnswerQuiz) {
                ((MultipleUserAnswerQuiz) userQuizAnswer).setAnswer((List<Integer>) answer);
            } else if (quiz instanceof StringAnswerQuiz) {
                ((StringUserAnswerQuiz) userQuizAnswer).setAnswer((String) answer);
            } else if (quiz instanceof MapAnswerQuiz) {
                ((MapUserAnswerQuiz) userQuizAnswer).setAnswer((HashMap<String, String>) answer);
            }

        userTestAnswerRepository.save(lastUserAnswer);
    }

    public Long startAnswer(Long testId, String restore, Long userId) throws SchedulerException {
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
                for (Quiz quiz : userTestAnswer.getTest().getQuizzes()) {
                    UserQuizAnswer userQuizAnswer = new UserQuizAnswer();

                    if (quiz instanceof MultipleAnswerQuiz) {
                        MultipleUserAnswerQuiz multipleUserAnswerQuiz = new MultipleUserAnswerQuiz();
                        multipleUserAnswerQuiz.setQuiz(quiz);
                        userQuizAnswer = multipleUserAnswerQuiz;
                    } else if (quiz instanceof StringAnswerQuiz) {
                        StringUserAnswerQuiz stringUserAnswerQuiz = new StringUserAnswerQuiz();
                        stringUserAnswerQuiz.setQuiz(quiz);
                        stringUserAnswerQuiz.setAnswer("");
                        userQuizAnswer = stringUserAnswerQuiz;
                    } else if (quiz instanceof MapAnswerQuiz) {
                        MapUserAnswerQuiz mapUserAnswerQuiz = new MapUserAnswerQuiz();
                        mapUserAnswerQuiz.setQuiz(quiz);
                        userQuizAnswer = mapUserAnswerQuiz;
                    }
                    userQuizAnswer.setDuration(0.0);
                    userTestAnswer.addUserQuizAnswer(userQuizAnswer);
                }
                saveStartAnswer(userTestAnswer);

                if (test.getDuration() != null) {

                    Calendar jobCalendar = getEndTime(test);

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
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return userTestAnswer.getUserAnswerId();
    }

    public static Calendar getEndTime(Test test) {
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
        return jobCalendar;
    }

    @Transactional
    public void updateDuration(Long answerSessionId, Long quizId, Double duration) {

        Optional<UserTestAnswer> answerOptional = userTestAnswerRepository.findById(answerSessionId);
        if (answerOptional.isPresent()) {
            UserTestAnswer userTestAnswer = answerOptional.get();
            for (UserQuizAnswer userQuizAnswer : userTestAnswer.getUserQuizAnswers()) {
                if (userQuizAnswer.getQuiz().getQuizId().equals(quizId)) {
                    userQuizAnswer.addDuration(duration);
                }
            }

        }
    }
}
