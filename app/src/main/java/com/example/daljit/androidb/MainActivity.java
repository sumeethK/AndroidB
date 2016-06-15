package com.example.daljit.androidb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyNotesDBHandler dbHandler ;
    EditText editText ;
    TextView  textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.text_edit_notes);
        textView = (TextView) findViewById(R.id.text_view_notes);
        textView.setMovementMethod(new ScrollingMovementMethod());
        dbHandler = new MyNotesDBHandler(this,null,null,1);
        displayNotes();


    }

    private void displayNotes() {
        String notes = dbHandler.printAllNotes();
        textView.setText(notes);
        editText.setText("");

    }

    public void loginScreen(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void addButtonClicked(View view){

        MyNotes notes = new MyNotes();
        notes.setNotes(editText.getText().toString());
        dbHandler.addNotes(notes);
        displayNotes();
    }

    public void deleteButtonClicked(View view){

        String id = "1";
        dbHandler.deleteNotes(id);
        displayNotes();
    }
}
