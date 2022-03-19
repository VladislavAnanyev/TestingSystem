package com.example.mywebquizengine.Model.Projection.Api;

import com.example.mywebquizengine.Model.Projection.UserCommonView;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface MessageForApiViewCustomQuery {

    Integer getId();

    String getContent();

    @Value("#{new com.example.mywebquizengine.Model.User(target.username, target.firstName, target.lastName, target.avatar)}")
    UserCommonView getSender();

    Date getTimestamp();

    @Value("#{new com.example.mywebquizengine.Model.Chat.Dialog(target.dialogId, target.name, target.image, @dialogRepository.findById(T(Long).parseLong(target.dialogId)).get().getUsers())}")
    DialogWithoutMessages getDialog();

}
