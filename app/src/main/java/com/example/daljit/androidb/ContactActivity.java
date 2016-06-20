package com.example.daljit.androidb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.androidb.util.Contacts;
import com.example.daljit.androidb.adapter.ContactListViewAdapter;
import com.example.daljit.androidb.model.ContactDetail;

import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactListView = (ListView) findViewById(R.id.contact_list_view);
        listViewAdaptor(Contacts.getContactsFromPhone(getContentResolver()));
    }

    private void listViewAdaptor(List<ContactDetail> contactList) {

        contactListView.setAdapter(new ContactListViewAdapter(getApplicationContext(), contactList));
    }

    public void onRefreshButtonClick (View v){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
