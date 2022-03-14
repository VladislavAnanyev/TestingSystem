package com.example.mywebquizengine.Security;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.Repos.UserRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
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

    public LoggedUser() {}

    @Override
    public void valueBound(HttpSessionBindingEvent event) {


        if (activeUserStore != null) {
            List<String> users = activeUserStore.getUsers();

            LoggedUser user = (LoggedUser) event.getValue();
            if (!users.contains(user.getUsername())) {
                users.add(user.getUsername());
            }


        System.out.println("Начало");

            if (MywebquizengineApplication.ctx.getBean(UserRepository.class).findById(user.getUsername()).isPresent()) {
                User authUser = MywebquizengineApplication.ctx.getBean(UserRepository.class).findById(user.getUsername()).get();
                authUser.setOnline("true");
                MywebquizengineApplication.ctx.getBean(UserRepository.class).save(authUser);
            }

        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {

        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users != null && users.contains(user.getUsername())) {
            users.remove(user.getUsername());

        }

        System.out.println("Конец");
        if (MywebquizengineApplication.ctx.getBean(UserRepository.class).findById(user.getUsername()).isPresent()) {
            User authUser = MywebquizengineApplication.ctx.getBean(UserRepository.class).findById(user.getUsername()).get();
            authUser.setOnline("false");
            MywebquizengineApplication.ctx.getBean(UserRepository.class).save(authUser);
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
