package com.example.daljit.androidb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidb.util.MyNotesUtil;
import com.example.daljit.androidb.db.MyNotesDBHandler;
import com.example.daljit.androidb.masterdetail.NoteListActivity;
import com.example.daljit.androidb.model.MyNotes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyNotesDBHandler dbHandler;
    EditText editText;
    ListView listView;
    List<MyNotes> notes;
    int noteToDelete =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.text_edit_notes);
        dbHandler = new MyNotesDBHandler(this, null, null, 1);
        listView = (ListView) findViewById(R.id.notes_listView);
        displayNotes();
        registerNotesListClickCallback();


    }

    private void registerNotesListClickCallback() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noteToDelete = position;
                MyNotes note = notes.get(noteToDelete);
                noteToDelete = Integer.parseInt(note.get_id());
                Toast.makeText(MainActivity.this, "Notes ID-" + noteToDelete, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void displayNotes() {
        notes = dbHandler.getAllNotesList();
        listViewAdaptor(notes);
        editText.setText("");

    }

    private void listViewAdaptor(List<MyNotes> notes) {
        List notesList = new ArrayList();
        for (MyNotes note : notes
                ) {
            notesList.add(MyNotesUtil.getTaeaser(note.getNotes()));

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.notes_text_view,
                notesList);
        listView.setAdapter(arrayAdapter);

    }

    public void loginScreen(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void masterScreen(View view) {
        Intent intent = new Intent(this, NoteListActivity.class);
        startActivity(intent);

    }

    public void addButtonClicked(View view) {

        MyNotes notes = new MyNotes();
        notes.setNotes(editText.getText().toString());
        dbHandler.addNotes(notes);
        displayNotes();
    }

    public void deleteButtonClicked(View view) {
        dbHandler.deleteNotes(noteToDelete);
        displayNotes();
    }


}
