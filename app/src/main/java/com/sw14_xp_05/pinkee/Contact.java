package com.sw14_xp_05.pinkee;

/**
 * Created by michael on 21.05.14.
 */
public class Contact {

    private String forename;
    private String name;
    private String email;
    private String picture_link;

    public static final String DB_TABLE = "contact";
    public static final String DB_COL_EMAIL = "email";
    public static final String DB_COL_FORENAME = "forename";
    public static final String DB_COL_NAME = "name";
    public static final String DB_COL_PICTURE = "picture";

    public Contact() {
        this.forename = "john";
        this.name = "doe";
        this.email = "john.doe@yourmama.com";
        this.picture_link = "johndoecontactimage";
    }

    public Contact(String forename, String name, String email, String picture_link) {
        this.forename = forename;
        this.name = name;
        this.email = email;
        this.picture_link = picture_link;
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



}
