package com.pratyay.whatsapp;

public class InstanceMessage {
    private String message;
    private String author;

    public InstanceMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstanceMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
