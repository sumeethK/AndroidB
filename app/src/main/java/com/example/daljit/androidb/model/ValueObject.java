package com.example.daljit.androidb.model;

import android.content.Context;

import com.example.daljit.androidb.model.MyNotes;
import com.example.daljit.androidb.db.MyNotesDBHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueObject {

    public static  List<MyNotes> NOTES = new ArrayList<MyNotes>();
    private static MyNotesDBHandler dbHandler ;
    public static  Map<String, MyNotes> NOTES_MAP = new HashMap<String, MyNotes>();

    public static void setContext(Context context){
        dbHandler = new MyNotesDBHandler(context,null,null,1);
        NOTES=dbHandler.getAllNotesList();
        for (MyNotes note:NOTES
             ) {
            NOTES_MAP.put(note.get_id(),note);

        }

    }
}
