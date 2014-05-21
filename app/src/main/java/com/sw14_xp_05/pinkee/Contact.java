package com.sw14_xp_05.pinkee;

/**
 * Created by michael on 21.05.14.
 */
public class Contact {

    private String id;
    private String forename;
    private String name;
    private String email;
    private String picture_link;

    public Contact() {
        this.id = "";
        this.forename = "";
        this.name = "";
        this.email = "";
        this.picture_link = "";
    }

    public Contact(String id, String forename, String name, String email, String picture_link) {
        this.id = id;
        this.forename = forename;
        this.name = name;
        this.email = email;
        this.picture_link = picture_link;
    }


    public String getFullName() {
        return forename + " " + name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
