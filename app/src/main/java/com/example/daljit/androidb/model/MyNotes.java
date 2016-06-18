package com.example.daljit.androidb.model;

/**
 * Created by daljit on 12-Jun-16.
 */
public class MyNotes {

    private String _id;
    private String _notes;

    public MyNotes() {
    }

    public MyNotes(String notes) {
        this._notes = notes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNotes() {
        return _notes;
    }

    public void setNotes(String notes) {
        this._notes = notes;
    }
}
