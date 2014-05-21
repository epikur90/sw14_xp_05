package com.sw14_xp_05.pinkee;

import java.util.Date;

public class Message {
    private String messageText;
    private Contact contact;
    private Date dateSent;
    private Date dateReceived;

    public static final String DB_COL_ID = "id";
    public static final String DB_COL_MESSAGETEXT = "messageText";
    public static final String DB_COL_CONTACT = "contact";
    public static final String DB_COL_DATE = "date";

    public Message(String messageText, Contact contact, Date dateSent, Date dateReceived) {
        this.messageText = messageText;
        this.contact = contact;
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

    public Contact getContact() {
        return contact;
    }

    public String getContactID() {
        return "email placeholder";
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
        return contact != null;
    }
}
