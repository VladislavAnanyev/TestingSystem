package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Projection.Api.MessageWithDialog;
import com.example.mywebquizengine.Model.Projection.Api.LastDialog;
import com.example.mywebquizengine.Model.Projection.MessageView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, PagingAndSortingRepository<Message, Long> {

    @Query(value = "SELECT * FROM MESSAGES WHERE TIMESTAMP = :time", nativeQuery = true)
    Message getDialogsByTimestamp(String time);

    @Query(value = "SELECT DIALOG_ID FROM USERS_DIALOGS WHERE USER_ID = :username", nativeQuery = true)
    List<Long> getMyDialogsId(String username);

    Page<Message> findAllByDialog_DialogId(Long dialogId, Pageable paging);

    @Query(value = """
            SELECT MESSAGES.MESSAGE_ID, content, DIALOGS.dialog_id as dialogId,
                   MESSAGES.SENDER_USER_ID as username, activation_code,
                   change_password_code, email, first_name as firstName,
                   last_name as lastName, password, image, name ,
                   timestamp as timestamp, AVATAR
            FROM MESSAGES
                     LEFT OUTER JOIN USERS U
                                     on U.USER_ID = MESSAGES.SENDER_USER_ID
                     LEFT OUTER JOIN DIALOGS
                                     on DIALOGS.DIALOG_ID = MESSAGES.DIALOG_ID
            WHERE MESSAGES.TIMESTAMP IN (SELECT MAX(MESSAGES.TIMESTAMP)
                                         FROM MESSAGES GROUP BY MESSAGES.DIALOG_ID ) and MESSAGES.DIALOG_ID IN (SELECT USERS_DIALOGS.DIALOG_ID
                                                                                                               FROM USERS_DIALOGS WHERE USERS_DIALOGS.USER_ID = :userId)
            GROUP BY MESSAGES.MESSAGE_ID
            ORDER BY MESSAGES.TIMESTAMP DESC;
            """, nativeQuery = true)
    List<LastDialog> getDialogsForApi(Long userId);

    MessageWithDialog findMessageByMessageId(Long id);

    Message getByMessageId(Long id);

}
