package com.example.daljit.androidb.model;

import java.util.Arrays;

/**
 * Created by daljit on 18-Jun-16.
 */
public class ContactDetail {

    private String name;
    private Integer phone[];
    private String[] email;
    private String id;
    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getPhone() {
        return phone;
    }

    public void setPhone(Integer[] phone) {
        this.phone = phone;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String getFormattedEmail(){
        StringBuilder email = new StringBuilder();
        email.append(Arrays.toString(getEmail()));
        return email.toString().replace("[","").replace("]","");
    }

    public String getFormattedPhoneNo(){
        StringBuilder phone = new StringBuilder();
        phone.append(Arrays.toString(getPhone()));
        return phone.toString().replace("[","").replace("]","");
    }
    @Override
    public String toString() {
        return "ContactDetail{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}
