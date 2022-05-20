package com.example.mywebquizengine.Model.Projection;

import java.util.Date;
import java.util.List;

public interface MessageView {
    Integer getId();
    String getContent();
    UserCommonView getSender();
    Date getTimestamp();
}
