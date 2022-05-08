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

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @ElementCollection
    @CollectionTable(
            name="MESSAGES_FILES",
            joinColumns=@JoinColumn(name="MESSAGE_ID")
    )
    private List<MessageFile> files;

    @OneToMany
    @Column(name = "FORWARD_MESSAGE")
    private List<Message> forwardedMessages;

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

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
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

    public List<Message> getForwardedMessages() {
        return forwardedMessages;
    }

    public void setForwardedMessages(List<Message> forwardedMessages) {
        this.forwardedMessages = forwardedMessages;
    }

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public List<MessageFile> getFiles() {
        return files;
    }

    public void setFiles(List<MessageFile> photos) {
        this.files = photos;
    }
}