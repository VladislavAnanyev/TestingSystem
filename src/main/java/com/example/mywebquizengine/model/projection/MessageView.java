package com.example.mywebquizengine.model.projection;

import java.util.Date;
import java.util.List;

public interface MessageView {
    Integer getId();
    String getContent();
    UserCommonView getSender();
    Date getTimestamp();
}
