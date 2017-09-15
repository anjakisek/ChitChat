package com.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.ParseException;
import java.util.Date;

public class PrejetoSporocilo {
    private Boolean global;
    private String recipient;
    private String sender;
    private String text;
    private String sent_at;

    private PrejetoSporocilo(){}

    public PrejetoSporocilo(Boolean global, String recipient, String sender, String text, String sent_at) {
        this.global = global;
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
        this.sent_at = sent_at;
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

    @JsonProperty("sent_at")
    public String getSentAt() {
        return sent_at;
    }


    public void setSentAt(String sent_at) {
        this.sent_at = sent_at;
    }

    public Date getDateActive(){
        ISO8601DateFormat df = new ISO8601DateFormat();
        try {
            return df.parse(sent_at);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}