package com.androidb.util;

public class MyNotesUtil {

    private MyNotesUtil(){}

    public  static String getTaeaser(String note) {
        if (note.length() > 10) return note.substring(0, 9) + "...";
        else if (note.length() > 5) {
            return note.substring(0, 4) + "...";
        } else {
            return note + "...";
        }

    }

}
