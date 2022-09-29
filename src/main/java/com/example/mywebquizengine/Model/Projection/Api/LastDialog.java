package com.example.mywebquizengine.Model.Projection.Api;

import com.example.mywebquizengine.Model.Projection.ProfileView;
import com.example.mywebquizengine.Model.Projection.UserCommonView;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface LastDialog {

    @Value("#{target.dialogId}")
    Long getDialogId();

    String getContent();

    @Value("#{new com.example.mywebquizengine.Model.User(target.userId, target.firstName, target.lastName, target.avatar, target.email)}")
    ProfileView getLastSender();

    @Value("#{@sqlResultProcessor.getCompanionForLastDialogs(target.dialogId, target.name)}")
    String getName();

    @Value("#{@sqlResultProcessor.getCompanionAvatarForLastDialogs(target.dialogId, target.image)}")
    String getImage();

    Date getTimestamp();

    /*Integer getId();

    String getContent();

    @Value("#{new com.example.mywebquizengine.Model.User(target.username, target.firstName, target.lastName, target.avatar)}")
    UserCommonView getSender();

    Date getTimestamp();

    @Value("#{new com.example.mywebquizengine.Model.Chat.Dialog(target.dialogId, target.name, target.image, @dialogRepository.findById(T(Long).parseLong(target.dialogId)).get().getUsers())}")
    DialogWithoutMessages getDialog();*/

}
