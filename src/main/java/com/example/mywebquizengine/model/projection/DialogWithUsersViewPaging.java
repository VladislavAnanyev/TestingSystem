package com.example.mywebquizengine.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

public interface DialogWithUsersViewPaging {
    Long getDialogId();

    String getName();

    String getImage();

    Set<UserCommonView> getUsers();

    @Value("#{T(com.google.common.collect.Lists).reverse(@messageRepository.findAllByDialog_DialogId(target.dialogId, target.paging ).content)}")
    List<MessageView> getMessages();
}
