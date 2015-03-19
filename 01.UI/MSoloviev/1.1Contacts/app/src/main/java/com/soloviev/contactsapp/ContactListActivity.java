package com.soloviev.contactsapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class ContactListActivity extends FragmentActivity {


    public static final String TAG_CONTACT_ACTIVITY_FRAGMENT = "contact_activity_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ContactsListFragment contactListFragment = (ContactsListFragment) fragmentManager.findFragmentByTag(TAG_CONTACT_ACTIVITY_FRAGMENT);
        if (contactListFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (findViewById(R.id.layout_plan) == null) {
                fragmentTransaction.add( new ContactsListFragment(), TAG_CONTACT_ACTIVITY_FRAGMENT).commit();
            } else {
                fragmentTransaction.add(R.id.layout_left, new ContactsListFragment(), TAG_CONTACT_ACTIVITY_FRAGMENT).commit();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContactsRepository.getInstance(getApplicationContext()).writeInformationByDB();
    }
}



