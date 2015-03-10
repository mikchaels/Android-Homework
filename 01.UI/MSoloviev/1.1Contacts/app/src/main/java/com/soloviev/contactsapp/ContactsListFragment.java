package com.soloviev.contactsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by USER on 06.03.2015.
 */
public class ContactsListFragment extends ListFragment {
    /*TODO*/
    MenuItem menuDelete;
    private ContactListViewAdapter mContactListViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactListViewAdapter = new ContactListViewAdapter(ContactsRepository.getInstance().getContacts());
        setListAdapter(mContactListViewAdapter);
//        setEmptyText("Нет контактов");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menuDelete = menu.findItem(R.id.delete_contact);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ContactsRepository contactsRepository = ContactsRepository.getInstance();
        switch (id) {

            case R.id.add_contact:
                contactsRepository.addContact(Contact.generateNewContact());
                Toast.makeText(getActivity(), "added, now " + (contactsRepository.getCountContacts()), Toast.LENGTH_SHORT).show();
                /*TODO*/
                if (!(menuDelete.isVisible())) {
                    menuDelete.setVisible(true);
                }
                /**/
                refresh();
                break;

            case R.id.delete_contact:
                safeRemoveItem(contactsRepository.getCountContacts() - 1);
                break;

            case R.id.action_confirm_cleaning:
                contactsRepository.removeContacts();
                refresh();
                Toast.makeText(getActivity(), "yep, sure", Toast.LENGTH_SHORT).show();
                menuDelete.setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        (mContactListViewAdapter).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        intent.putExtra(ContactFragment.ID_CONTACT, ((Contact) l.getItemAtPosition(position)).getId());

        if (getActivity().findViewById(R.id.layout_plan) != null) {
            getActivity().setIntent(intent);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ContactFragment contactFragment = (ContactFragment) fragmentManager.findFragmentByTag(ContactActivity.TAG_CONTACT_FRAGMENT);
            if (contactFragment != null) {
                fragmentManager.beginTransaction().detach(contactFragment).commit();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.layout_ridht, new ContactFragment(), ContactActivity.TAG_CONTACT_FRAGMENT).commit();
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void safeRemoveItem(final int position) { ////TODO ?????
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning")
                .setMessage("Are you sure?")
                .setNegativeButton("NO", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        ContactFragment contactFragment = (ContactFragment) fragmentManager.findFragmentByTag(ContactActivity.TAG_CONTACT_FRAGMENT);
                        if (position == contactFragment.getIdContact()) {
                            if (contactFragment != null) {
                                fragmentManager.beginTransaction().detach(contactFragment).commit();
                            }
                        }
                        ContactsRepository.getInstance().removeContact(position);
                        Toast.makeText(getActivity(), "delete_finish_elements" + ContactsRepository.getInstance().getCountContacts(), Toast.LENGTH_SHORT).show();
                        refresh();
                        if (ContactsRepository.getInstance().getCountContacts() == 0) {
                            menuDelete.setVisible(false);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
