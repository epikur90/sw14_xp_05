package com.sw14_xp_05.pinkee;

import java.util.Date;

public class Message {
    private String messageText;
    private Contact contact;
    private Date date;

    public static final String DB_TABLE = "message";
    public static final String DB_COL_ID = "id";
    public static final String DB_COL_MESSAGETEXT = "messageText";
    public static final String DB_COL_CONTACT = "contact";
    public static final String DB_COL_DATE = "date";

    public Message(String messageText, Contact contact, Date date) {
        this.messageText = messageText;
        this.contact = contact;
        this.date = date;
    }

    public Message(String messageText, Contact contact){
        this(messageText, contact, new Date());
    }

    public Message(){this("", null,null);}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Contact getContact() {
        return contact;
    }

    public String getContactID() {
        return contact.getEmail();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return this.messageText;
    }

    public boolean isIncoming() {
        return contact != null;
    }
}
