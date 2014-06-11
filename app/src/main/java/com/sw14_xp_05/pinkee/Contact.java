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
    private String pictureLink;
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
        this.pictureLink = "johndoecontactimage";
        this.lastMessage = new Date();
        this.public_key = null;
    }

    public Contact(String forename, String name, String email, String pictureLink) {
        this.forename = forename;
        this.name = name;
        this.email = email;
        this.pictureLink = pictureLink;
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

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
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
                //", email=" + email + ", pictureLink=" +pictureLink + "]";
    }
}
