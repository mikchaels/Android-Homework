package com.soloviev.contactsapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 06.02.2015.
 */
public class ContactsRepository {
    private static ContactsRepository sContactsRepository = new ContactsRepository();
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
        if (!contact.isSetManualContact()){
        mContacts.add(contact);}
        else {
            /*TODO*/
        }
    }

    public void removeContact(int position) {
        mContacts.remove(position);
    }

    public void removeContacts() {
        mContacts.clear();
    }

    public int getCountContacts() {
        return mContacts.size();
    }
/*
* возвращает элемент коллекции
* имеющий
*
* */
    public Contact getContact(int id) {
//        for (Contact contact : mContacts) {
//            if (contact.getId() == id) {
//                return contact;
//            }
//        }
        if(mContacts.contains(new Contact(id))){
           return mContacts.get( mContacts.indexOf(new Contact(id)));
        }
        /* TODO Exeptions*/
    return null;
    }
}
