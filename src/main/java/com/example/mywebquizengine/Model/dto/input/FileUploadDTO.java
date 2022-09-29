package com.example.mywebquizengine.Model.dto.input;

public class FileUploadDTO {
    private String bytes;
    private String filename;

    public String getFilename() {
        return filename;
    }

    public String getBytes() {
        return bytes;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
