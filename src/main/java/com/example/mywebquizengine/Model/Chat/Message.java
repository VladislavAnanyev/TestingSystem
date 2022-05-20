package com.example.mywebquizengine.Model.Chat;

import com.example.mywebquizengine.Model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @Size(min = 1)
    private String content;

    private Date timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @Transient
    private Integer uniqueCode;

    public Message() {}

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long id) {
        this.messageId = id;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

}