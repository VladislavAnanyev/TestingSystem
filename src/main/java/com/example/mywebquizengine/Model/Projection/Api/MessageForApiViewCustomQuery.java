package com.example.mywebquizengine.Model.Projection.Api;

import com.example.mywebquizengine.Model.Projection.UserCommonView;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface MessageForApiViewCustomQuery {

    Integer getId();

    String getContent();

    @Value("#{new com.example.mywebquizengine.Model.User(target.username, target.firstName, target.lastName, target.avatar)}")
    UserCommonView getSender();

    //@Value("#{new java.util.Date(target.timestamp.getTime())}")
    Date getTimestamp();

    @Value("#{new com.example.mywebquizengine.Model.Chat.Dialog(target.dialogId, target.name, target.image, @dialogRepository.findById(T(Long).parseLong(target.dialogId)).get().getUsers())}")
    DialogWithoutMessages getDialog();

    /*@Value("#{new com.example.mywebquizengine.Model.Chat.Dialog(target.dialogId, target.name, target.image)}")
    DialogForApi getDialog();*/
    //Long getDialogId();
}
