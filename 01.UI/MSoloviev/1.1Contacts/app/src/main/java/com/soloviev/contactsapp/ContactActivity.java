package com.soloviev.contactsapp;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class ContactActivity extends FragmentActivity {

    public static final String TAG_CONTACT_FRAGMENT = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ContactFragment contactFragment = (ContactFragment) fragmentManager.findFragmentByTag(TAG_CONTACT_FRAGMENT);
        if (contactFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content, new ContactFragment(), TAG_CONTACT_FRAGMENT).commit();
        }

    }
}