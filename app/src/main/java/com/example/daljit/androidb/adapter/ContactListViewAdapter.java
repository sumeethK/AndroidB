package com.example.daljit.androidb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daljit.androidb.R;
import com.example.daljit.androidb.model.ContactDetail;

import java.util.List;

/**
 * Created by daljit on 18-Jun-16.
 */
public class ContactListViewAdapter extends BaseAdapter{

    private Context mContext;
    List<ContactDetail> contactlist;

    public ContactListViewAdapter(Context mContext, List<ContactDetail> contactlist) {
        this.mContext = mContext;
        this.contactlist = contactlist;
    }


    @Override
    public int getCount() {
        return contactlist.size();
    }

    @Override
    public Object getItem(int position) {
        return contactlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.contact_list, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_contact_name);
        TextView tvPhone = (TextView)v.findViewById(R.id.tv_contact_phone);
        TextView tvEmail = (TextView)v.findViewById(R.id.tv_contact_email);
        //Set text for TextView
        tvName.setText(contactlist.get(position).getName());
        tvPhone.setText(contactlist.get(position).getFormattedPhoneNo());
        tvEmail.setText(contactlist.get(position).getFormattedEmail());
        //Save product id to tag
        v.setTag(contactlist.get(position).getId());
        return v;
    }
}
