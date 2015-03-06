package com.soloviev.contactsapp;

import android.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
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
    TextView mNameView;
    TextView mPhoneView;
    TextView mEmailView;
    TextView mAddressView;
    TextView mBirthDateView;
    TextView mOccupationView;
    Button mSave;
    Contact contact;

    @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNameView = (TextView) getActivity().findViewById(R.id.name);
        mPhoneView = (TextView) getActivity().findViewById(R.id.phone);
        mEmailView = (TextView) getActivity().findViewById(R.id.email);
        mAddressView = (TextView) getActivity().findViewById(R.id.address);
        mBirthDateView = (TextView) getActivity().findViewById(R.id.birthdate);
        mOccupationView = (TextView) getActivity().findViewById(R.id.occupation);
        mSave = (Button) getActivity().findViewById(R.id.save);
        init((int) getActivity().getIntent().getSerializableExtra("contact"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact,container,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact, menu);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getActivity().getMenuInflater().inflate(R.menu.menu_contact, menu);
//        return true;
//
//    }

    public  void init(int id) {
        contact = ContactsRepository.getInstance().getContact(id);
        mNameView.setText(contact.getName());
        mPhoneView.setText(contact.getPhone());
        mEmailView.setText(contact.getEmail());
        mAddressView.setText(contact.getAddress());
        mBirthDateView.setText(DateFormat.getDateFormat(getActivity()).format(contact.getBirthDate()));
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
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
