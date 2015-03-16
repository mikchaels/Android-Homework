package com.soloviev.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    static Context mContext;
    private static ContactsRepository sContactsRepository = new ContactsRepository(mContext);
    EmptyCheckable mEmptyCheckable;
    private List<Contact> mContacts;
    private SQLiteOpenHelper mContactsDataBaseHelper;
    private SQLiteDatabase db;
    private boolean isSaveChange;

    private ContactsRepository(Context context) {
        mContacts = new ArrayList<Contact>();
        mContactsDataBaseHelper = new ContactsDataBaseHelper(mContext);
        db = mContactsDataBaseHelper.getWritableDatabase();
        readInformationFromDB();
    }

    public static ContactsRepository getInstance(Context context) {
        mContext = context;
        return sContactsRepository;
    }

    private void readInformationFromDB() {
        int maxID = 0;
        Cursor cursor = db.query(ContactsDataBaseHelper.CONTACTS_TABLE_NAME,
                new String[]{ContactsDataBaseHelper.COLUMN_NAME_ADDRESS,
                        ContactsDataBaseHelper.COLUMN_NAME_BIRTH_DATE,
                        ContactsDataBaseHelper.COLUMN_NAME_EMAIL,
                        ContactsDataBaseHelper.COLUMN_NAME_ID,
                        ContactsDataBaseHelper.COLUMN_NAME_NAME,
                        ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION,
                        ContactsDataBaseHelper.COLUMN_NAME_PHONE}
                , null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_ID));
            Contact contact = new Contact(id);
            contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_NAME)));
            contact.setAddress(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_ADDRESS)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_EMAIL)));
            contact.setOccupation(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION)));
            contact.setPhone(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_PHONE)));
            mContacts.add(contact);
            if (id > maxID) {
                maxID = id;
            }
        }
        Contact.setCounter(++maxID);
        isSaveChange = false;
    }

    private void writeInformationByDB() {
        clearDB();
        for (Contact contact : mContacts) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_ADDRESS, contact.getAddress());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_BIRTH_DATE, contact.getBirthDate().toString());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_EMAIL, contact.getEmail());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_ID, contact.getId());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_NAME, contact.getName());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION, contact.getOccupation());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_PHONE, contact.getPhone());
            db.insert(ContactsDataBaseHelper.CONTACTS_TABLE_NAME, ContactsDataBaseHelper.COLUMN_NAME_ID, contentValues);
        }
        isSaveChange = true;
    }

    private void clearDB() {
        mContactsDataBaseHelper.onUpgrade(db, ContactsDataBaseHelper.VERSION, ContactsDataBaseHelper.VERSION);
    }

    public void closeDB() {
        if (isSaveChange) {
            mContactsDataBaseHelper.close();
            db.close();
        } else {
            writeInformationByDB();
            closeDB();
        }
    }

    public List<Contact> getContacts() {
        return mContacts;
    }

    public void addContact(Contact contact) {
        if (!contact.isSetManualContact()) {
            mContacts.add(contact);
        } else {
            /*TODO*/
        }
    }

    public Contact getContactByPosition(int position) {
        return mContacts.get(position);
    }

    public void removeContact(int position) {
        mContacts.remove(position);
        onEmptyAction();
    }

    public void removeContacts() {
        mContacts.clear();
        onEmptyAction();
    }

    public int getCountContacts() {
        return mContacts.size();
    }

    /*
    * возвращает элемент коллекции
    * по ID
    *
    * */
    public Contact getContact(int id) {
        for (Contact contact : mContacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
//        if (mContacts.contains(new Contact(id))) {
//            return mContacts.get(mContacts.indexOf(new Contact(id)));
//        }
        return null;
    }

    public void setEmptyCheckable(EmptyCheckable emptyCheckable) {
        mEmptyCheckable = emptyCheckable;
        onEmptyAction();
    }

    void onEmptyAction() {
        if (mEmptyCheckable != null) {
            if (mContacts.isEmpty()) {
                mEmptyCheckable.actionByContactsEmpty();
            }
        }
    }

    public static interface EmptyCheckable {
        void actionByContactsEmpty();
    }
}
