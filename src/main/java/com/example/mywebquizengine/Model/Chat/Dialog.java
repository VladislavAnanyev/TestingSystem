package com.example.mywebquizengine.Model.Chat;

import com.example.mywebquizengine.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "DIALOGS")
public class Dialog {

    @Id
    @Column(name = "DIALOG_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long dialogId;

    private String name;

    private String image;

    @Size(min = 2)
    @ManyToMany(mappedBy = "dialogs", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users = new HashSet<>();

    @Transient
    @JsonIgnore
    private Pageable paging;

    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> messages;

    public Dialog() {}

    public Dialog(Long dialogId, String name, String image, Set<User> users) {
        this.dialogId = dialogId;
        this.name = name;
        this.image = image;
        this.users = users;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialog_id) {
        this.dialogId = dialog_id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = new HashSet<>();
        this.users.addAll(users);
        //this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getDialogs().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pageable getPaging() {
        return paging;
    }

    public void setPaging(Pageable paging) {
        this.paging = paging;
    }
}