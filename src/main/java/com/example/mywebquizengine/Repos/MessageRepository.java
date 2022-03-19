package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Projection.Api.MessageWithDialog;
import com.example.mywebquizengine.Model.Projection.Api.MessageForApiViewCustomQuery;
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

    Page<MessageView> findAllByDialog_DialogIdAndStatusNot(Long dialogId, MessageStatus status, Pageable paging);

    @Query(value = """
            SELECT MESSAGES.id, content, DIALOGS.dialog_id as dialogId,
                   MESSAGES.SENDER_USERNAME as username, activation_code,
                   change_password_code, email, first_name as firstName,
                   last_name as lastName, password, MESSAGES.status, image, name ,
                   timestamp as timestamp, AVATAR
            FROM MESSAGES
                     LEFT OUTER JOIN USERS U
                                     on U.USERNAME = MESSAGES.SENDER_USERNAME
                     LEFT OUTER JOIN DIALOGS
                                     on DIALOGS.DIALOG_ID = MESSAGES.DIALOG_ID
            WHERE MESSAGES.TIMESTAMP IN (SELECT MAX(MESSAGES.TIMESTAMP)
                                         FROM MESSAGES WHERE MESSAGES.STATUS != 'DELETED' GROUP BY MESSAGES.DIALOG_ID ) and MESSAGES.DIALOG_ID IN (SELECT USERS_DIALOGS.DIALOG_ID
                                                                                                               FROM USERS_DIALOGS WHERE USERS_DIALOGS.USER_ID = :username)
            GROUP BY MESSAGES.ID
            ORDER BY MESSAGES.TIMESTAMP DESC;
            """, nativeQuery = true)
    List<MessageForApiViewCustomQuery> getDialogsForApi(String username);

    MessageWithDialog findMessageById(Long id);

    Message getById(Integer id);

}
