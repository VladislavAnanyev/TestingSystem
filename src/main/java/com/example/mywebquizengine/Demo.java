package com.example.mywebquizengine;

import com.example.mywebquizengine.repos.UserRepository;
import com.example.mywebquizengine.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

@Component
public class Demo implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) {

        System.out.println(passwordEncoder.encode("12345"));
        /*List<User> users2 = userRepository.findAll();

        for (User user : users2) {
            rabbitAdmin.deleteQueue(user.getUsername());

        }*/

        /*if (rabbitAdmin.getQueueProperties("application") == null) {
            List<User> users = userRepository.findAll();

            for (User user : users) {
                Queue queue = new Queue(user.getUsername(), true, false, false);

                Binding binding = new Binding(user.getUsername(), Binding.DestinationType.QUEUE,
                        "message-exchange", user.getUsername(), null);

                rabbitAdmin.declareQueue(queue);
                rabbitAdmin.declareBinding(binding);


            }
        }*/



        /*List<User> users = userRepository.findAll();

        if (users.get(0).getPhotos().size() == 0) {
            for (User user : users) {
                Photo photo = new Photo();
                photo.setUrl(user.getAvatar());
                photo.setPosition(0);
                //photo.setUser(user);
                user.addPhoto(photo);
            }
        }*/


        /*if (rabbitAdmin.getQueueProperties("applicationmeeting") == null ) {

            List<User> users = userRepository.findAll();

            for (User user: users) {
                Queue queueMeeting = new Queue(user.getUsername() + "meeting", true, false, false);

                Binding bindingMeeting = new Binding(user.getUsername() + "meeting", Binding.DestinationType.QUEUE,
                        "message-exchange", user.getUsername() + "meeting", null);


                rabbitAdmin.declareQueue(queueMeeting);
                rabbitAdmin.declareBinding(bindingMeeting);
            }
        }*/

   /*     List<User> users = userRepository.findAll();



        for (User user: users) {
            Queue queue = new Queue(user.getUsername(), true, false, false);

            Binding binding = new Binding(user.getUsername(), Binding.DestinationType.QUEUE,
                    "message-exchange", user.getUsername(), null);

            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
        }*/


        //Thread.sleep(5000L);

        // scheduler.shutdown(true);

        //Map<Integer, Double> answerStats = userAnswerService.getAnswerStats();
        /*User user = userRepository.findById("application").get();

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        roles.add(Role.ROLE_ADMIN);

        user.setRoles(roles);

        userRepository.save(user);*/
        //answerStats.clear();

        /*List<User> users = userRepository.findAll();

        for (User user : users) {
            rabbitAdmin.deleteQueue(user.getUsername() + "meeting");

        }*/
    }
}
