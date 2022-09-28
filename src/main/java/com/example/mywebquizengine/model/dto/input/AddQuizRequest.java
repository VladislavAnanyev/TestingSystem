package com.example.mywebquizengine.model.dto.input;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleAnswerQuizRequest.class, name = "MULTIPLE"),
        @JsonSubTypes.Type(value = StringAnswerQuizRequest.class, name = "STRING"),
        @JsonSubTypes.Type(value = MapAnswerQuizRequest.class, name = "MAP")
})
public class AddQuizRequest {

    private String text;
    private String title;
    private String type;
    private FileUploadDTO file;

    public FileUploadDTO getFile() {
        return file;
    }

    public void setFile(FileUploadDTO file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
