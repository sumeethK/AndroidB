package com.androidb.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.daljit.androidb.model.ContactDetail;

import java.util.ArrayList;
import java.util.List;

public class Contacts {

    /**
     *
     * @param contentResolver
     * @return contact list from phone
     */
    public static List<ContactDetail> getContactsFromPhone(ContentResolver contentResolver) {
        List<ContactDetail> contactList = new ArrayList<ContactDetail>();
        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                ContactDetail contactDetail = new ContactDetail();
                contactDetail.setId(cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID)));
                contactDetail.setName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                contactDetail.setImageUri(cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactDetail.getId()}, null);
                    if (pCur != null) {
                        Integer[] phoneArray = new Integer[pCur.getCount()];
                        int phoneCount = 0;
                        while (pCur.moveToNext()) {
                            phoneArray[phoneCount] = Integer.parseInt(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            phoneCount++;
                        }

                        contactDetail.setPhone(phoneArray);
                        pCur.close();
                    }
                }

                Cursor emailCur = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactDetail.getId()}, null);

                if (emailCur != null && emailCur.getColumnCount() > 0) {

                    int emailCount = 0;
                    String[] email = new String[emailCur.getCount()];
                    while (emailCur.moveToNext()) {
                        email[emailCount] = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        // emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        emailCount++;
                    }
                    contactDetail.setEmail(email);
                    emailCur.close();

                }

                contactList.add(contactDetail);
            }

        }
        return contactList;

    }

}