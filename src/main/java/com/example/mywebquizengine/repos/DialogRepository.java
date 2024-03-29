package com.example.mywebquizengine.repos;

import com.example.mywebquizengine.model.chat.Dialog;
import com.example.mywebquizengine.model.projection.DialogWithUsersViewPaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DialogRepository extends CrudRepository<Dialog, Long>, JpaRepository<Dialog, Long>,
        PagingAndSortingRepository<Dialog, Long> {

    /*
    Если при группировке по идентификатору для выбранных пользователей результат группировки - 2
    (то есть было 2 записи с одинаковым id диалога)
    то диалог существует и возвращается его идентификатор
     */
    @Query(value = """
            SELECT DIALOG_ID, COUNT(DIALOG_ID)
            FROM USERS_DIALOGS a
            WHERE (USER_ID = :firstUserId or USER_ID = :secondUserId) AND
                  (SELECT COUNT(DIALOG_ID) FROM USERS_DIALOGS b where b.DIALOG_ID = a.DIALOG_ID) = 2
            GROUP BY DIALOG_ID HAVING COUNT(DIALOG_ID) = 2""", nativeQuery = true)
    Long findDialogByName(Long firstUserId, Long secondUserId);


    DialogWithUsersViewPaging findAllDialogByDialogId(Long id);




}
