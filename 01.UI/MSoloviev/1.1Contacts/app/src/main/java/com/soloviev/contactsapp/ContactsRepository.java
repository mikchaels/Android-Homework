package com.soloviev.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private Context mContext;
    private static ContactsRepository sContactsRepository;
    EmptyCheckable mEmptyCheckable;
    private List<Contact> mContacts;
    private ContactsDataBaseHelper mContactsDataBaseHelper;
    private SQLiteDatabase db;
    private boolean isSaveChange;

    private ContactsRepository(Context context) {
        mContacts = new ArrayList<Contact>();
        mContext = context;
        readSavedContacts();
    }

    public static ContactsRepository getInstance(Context context) {
        if (sContactsRepository == null) {

            sContactsRepository = new ContactsRepository(context);
        }

        return sContactsRepository;

    }

    private void readSavedContacts() {
        int maxID = 0;
        openDB();
        Cursor cursor = db.query(ContactsDataBaseHelper.CONTACTS_TABLE_NAME,
                new String[]{ContactsDataBaseHelper.COLUMN_NAME_ADDRESS,
                        ContactsDataBaseHelper.COLUMN_NAME_BIRTH_DATE,
                        ContactsDataBaseHelper.COLUMN_NAME_EMAIL,
                        ContactsDataBaseHelper.COLUMN_NAME_ID,
                        ContactsDataBaseHelper.COLUMN_NAME_NAME,
                        ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION,
                        ContactsDataBaseHelper.COLUMN_NAME_PHONE}
                , null, null, null, null, null);
        int columnIndexID=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_ID);
        int columnIndexName=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_NAME);
        int columnIndexAddress=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_ADDRESS);
        int columnIndexEmail=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_EMAIL);
        int columnIndexOccupation=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION);
        int columnIndexPhone=cursor.getColumnIndex(ContactsDataBaseHelper.COLUMN_NAME_PHONE);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(columnIndexID);
            Contact contact = new Contact(id);
            contact.setName(cursor.getString(columnIndexName));
            contact.setAddress(cursor.getString(columnIndexAddress));
            contact.setEmail(cursor.getString( columnIndexEmail));
            contact.setOccupation(cursor.getString(columnIndexOccupation));
            contact.setPhone(cursor.getString(columnIndexPhone));
            mContacts.add(contact);
            if (id > maxID) {
                maxID = id;
            }
        }
        Contact.setCounter(+maxID);
        closeDB();
    }

    public void writeInformationByDB() {
        openDB();
        clearDB();
        for (Contact contact : mContacts) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_ADDRESS, contact.getAddress());
            /*TODO DATE*/
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_BIRTH_DATE, ""/*(contact.getBirthDate().toString())*/);
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_EMAIL, contact.getEmail());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_ID, contact.getId());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_NAME, contact.getName());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_OCCUPATION, contact.getOccupation());
            contentValues.put(ContactsDataBaseHelper.COLUMN_NAME_PHONE, contact.getPhone());
            db.insert(ContactsDataBaseHelper.CONTACTS_TABLE_NAME, ContactsDataBaseHelper.COLUMN_NAME_ID, contentValues);
        }
        isSaveChange = true;
        closeDB();
    }

    private void clearDB() {
        mContactsDataBaseHelper.recreateDB(db);
    }

    public void openDB() {
        mContactsDataBaseHelper = new ContactsDataBaseHelper(mContext);
        db = mContactsDataBaseHelper.getWritableDatabase();
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
        isSaveChange = false;
    }

    public Contact getContactByPosition(int position) {
        return mContacts.get(position);
    }

    public void removeContact(int position) {
        mContacts.remove(position);
        onEmptyAction();
        isSaveChange = false;
    }

    public void removeContacts() {
        mContacts.clear();
        onEmptyAction();
        isSaveChange = false;
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
