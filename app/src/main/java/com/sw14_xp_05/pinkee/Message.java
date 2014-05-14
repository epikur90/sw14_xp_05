package com.sw14_xp_05.pinkee;

import android.widget.ImageButton;

import java.util.Date;

public class Message {
    private String messageText;
    private String senderEmail;
    private Date dateSent;
    private Date dateReceived;

    public Message(String messageText, String senderEmail, Date dateSent, Date dateReceived){
        this.messageText = messageText;
        this.senderEmail = senderEmail;
        this.dateSent = dateSent;
        this.dateReceived = dateReceived;
    }

    public Message(String messageText){
        this(messageText, null, new Date(), null);
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String toString() {
        return this.messageText;
    }

    public boolean isIncoming() {
        return senderEmail != null;
    }
}
