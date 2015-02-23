package com.soloviev.contactsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ContactListViewAdapter extends BaseAdapter {
    List<Contact> mContacts;

    public ContactListViewAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mContacts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View retView;
        if (convertView==null){
            retView= LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.contact_view_layout, parent, false);
        } else {
            retView=convertView;
        }
        ((TextView)retView.findViewById(R.id.contactview_name)).setText(mContacts.get(position).getName());
        ((TextView)retView.findViewById(R.id.contactview_phone)).setText(mContacts.get(position).getPhone());
        return retView;
    }

}

