package com.soloviev.contactsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by USER on 06.03.2015.
 */
public class ContactFragment extends Fragment {

    public static final String ID_CONTACT = "id_contact";

    TextView mNameView;
    TextView mPhoneView;
    TextView mEmailView;
    TextView mAddressView;
    TextView mBirthDateView;
    TextView mOccupationView;

    Button mSave;

    Contact contact;
    int idContact;

    public static ContactFragment newInstanceFragment(int idContact) {
        Bundle arg = new Bundle(1);
        arg.putInt(ID_CONTACT, idContact);
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(arg);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNameView = (TextView) getActivity().findViewById(com.soloviev.contactsapp.R.id.name);
        mPhoneView = (TextView) getActivity().findViewById(R.id.phone);
        mEmailView = (TextView) view.findViewById(R.id.email);
        mAddressView = (TextView) view.findViewById(R.id.address);
        mBirthDateView = (TextView) view.findViewById(R.id.birthdate);
        mOccupationView = (TextView) view.findViewById(R.id.occupation);
        mSave = (Button) view.findViewById(R.id.save);
        Bundle arg = getArguments();
        if (arg == null) {
            idContact = (int) getActivity().getIntent().getSerializableExtra(ID_CONTACT);
        }

        init(idContact);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact, menu);
    }

    public void init(int id) {
        contact = ContactsRepository.getInstance(getActivity().getApplicationContext()).getContact(id);
        mNameView.setText(contact.getName());
        mPhoneView.setText(contact.getPhone());
        mEmailView.setText(contact.getEmail());
        mAddressView.setText(contact.getAddress());
        /*TODO*/
        //mBirthDateView.setText(DateFormat.getDateFormat(getActivity()).format(contact.getBirthDate()));
        mOccupationView.setText(contact.getOccupation());
    }

    public void editContact() {
        contact.setName(mNameView.getText().toString());
        contact.setPhone(mPhoneView.getText().toString());
        contact.setEmail(mEmailView.getText().toString());
        contact.setAddress(mAddressView.getText().toString());
    /*TODO*/
        //contact.setBirthDate((java.util.Date) mBirthDateView.getText());
        contact.setOccupation(mOccupationView.getText().toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_edit: {
                mSave.setVisibility(View.VISIBLE);
                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editContact();
                        mSave.setVisibility(View.INVISIBLE);
                    }
                });
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public int getIdContact() {
        return idContact;
    }
}
