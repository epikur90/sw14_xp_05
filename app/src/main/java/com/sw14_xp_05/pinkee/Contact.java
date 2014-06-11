package com.sw14_xp_05.pinkee;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by michael on 21.05.14.
 */
public class Contact implements Serializable {

    private String forename;
    private String name;
    private String email;
    private String picture_link;
    private Date lastMessage;
    private String public_key;

    public static final String DB_TABLE = "contact";
    public static final String DB_COL_EMAIL = "email";
    public static final String DB_COL_FORENAME = "forename";
    public static final String DB_COL_NAME = "name";
    public static final String DB_COL_PICTURE = "picture";
    public static final String DB_COL_PUBLIC_KEY = "public_key";


    public Contact() {
        this.forename = "John";
        this.name = "Doe";
        this.email = "john.doe@yourmama.com";
        this.picture_link = "johndoecontactimage";
        this.lastMessage = new Date();
        this.public_key = null;
    }

    public Contact(String forename, String name, String email, String picture_link) {
        this.forename = forename;
        this.name = name;
        this.email = email;
        this.picture_link = picture_link;
        this.lastMessage = new Date();
        this.public_key = null;

    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public String getFullName() {
        return forename + " " + name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture_link() {
        return picture_link;
    }

    public void setPicture_link(String picture_link) {
        this.picture_link = picture_link;
    }

    public String getPublicKey() {
        return public_key;
    }

    public void setPublicKey(String public_key) {
        this.public_key = public_key;
    }

    public String toString() {

        if (forename.equals("") || name.equals(""))
            return email;

        return forename + " " + name;
        //return "Contact [forename=" + forename + ", name=" + name +
                //", email=" + email + ", picture_link=" +picture_link + "]";
    }
}
