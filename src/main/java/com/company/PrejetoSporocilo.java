package com.company;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrejetoSporocilo {
    private Boolean global;
    private String recipient;
    private String sender;
    private String text;
    private String sentAt;

    private PrejetoSporocilo(){}

    public PrejetoSporocilo(Boolean global, String recipient, String sender, String text, String sentAt) {
        this.global = global;
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
        this.sentAt = sentAt;
    }


    @JsonProperty("global")
    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }


    @JsonProperty("recipient")
    public String getRecipient() {
        return recipient;
    }


    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }


    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("sentAt")
    public String getSentAt() {
        return sentAt;
    }


    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}