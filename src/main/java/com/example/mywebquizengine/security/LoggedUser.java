package com.example.mywebquizengine.security;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.repos.UserRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
public class LoggedUser implements HttpSessionBindingListener {

    private String username;
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {


        if (activeUserStore != null) {
            List<String> users = activeUserStore.getUsers();

            LoggedUser user = (LoggedUser) event.getValue();
            if (!users.contains(user.getUsername())) {
                users.add(user.getUsername());
            }


            System.out.println("Начало");
            User authUser = MywebquizengineApplication.ctx.getBean(UserRepository.class).findUserByEmail(user.getUsername()).get();
            MywebquizengineApplication.ctx.getBean(UserRepository.class).save(authUser);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {

        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users != null) {
            users.remove(user.getUsername());
        }

        System.out.println("Конец");
        User authUser = MywebquizengineApplication.ctx.getBean(UserRepository.class).findUserByEmail(user.getUsername()).get();
        MywebquizengineApplication.ctx.getBean(UserRepository.class).save(authUser);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
