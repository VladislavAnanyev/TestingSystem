package com.example.mywebquizengine.Model.Projection;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Named;
import java.util.List;
import java.util.Set;

public interface DialogWithUsersViewPaging {
    Long getDialogId();
    String getName();
    String getImage();
    Set<UserCommonView> getUsers();


    //@Named(value = "messages")
    //@JsonProperty(value = "messages")
    @Value("#{T(com.google.common.collect.Lists).reverse(@messageRepository.findAllByDialog_DialogIdAndStatusNot(target.dialogId, T(com.example.mywebquizengine.Model.Chat.MessageStatus).DELETED, target.paging ).content)}")
    List<MessageView> getMessages();
}
