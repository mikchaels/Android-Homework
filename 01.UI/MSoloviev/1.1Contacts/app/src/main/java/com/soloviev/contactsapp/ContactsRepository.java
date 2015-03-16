package com.soloviev.contactsapp;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private static ContactsRepository sContactsRepository = new ContactsRepository();
    EmptyCheckable mEmptyCheckable;
    private List<Contact> mContacts;

    private ContactsRepository() {
        mContacts = new ArrayList<Contact>();
    }

    public static ContactsRepository getInstance() {
        return sContactsRepository;
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
//            return mContacts.get(mContacts.indexOf(new Contackt(id)));
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
