package com.soloviev.contactsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;


public class ContactListActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView mListView;
    /*TODO*/
    MenuItem menuDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(new ContactListViewAdapter(ContactsRepository.getInstance().getContacts()));
        mListView.setOnItemClickListener(this);
      /*TODO emptyView*/
        //mListView.setEmptyView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);

       /*TODO*/
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuDelete = menu.findItem(R.id.delete_contact);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ContactsRepository contactsRepository = ContactsRepository.getInstance();
        switch (id) {
            case R.id.add_contact:
                contactsRepository.addContact(Contact.generateNewContact());
                Toast.makeText(this, "added, now " + (contactsRepository.getCountContacts()), Toast.LENGTH_SHORT).show();

                /*TODO*/

                if (!(menuDelete.isVisible())) {
                    menuDelete.setVisible(true);
                }

                refresh();
                break;
            case R.id.delete_contact:
            {  //contactsRepository.removeContact(contactsRepository.getCountContacts() - 1);
             safeRemoveItem(contactsRepository.getCountContacts() - 1);
//                Toast.makeText(this, "delete_finish_elements"+contactsRepository.getCountContacts(), Toast.LENGTH_SHORT).show();
//                refresh();
//                if (contactsRepository.getCountContacts() == 0) {
//                    item.setVisible(false);
//                }
                break;}
            case R.id.action_confirm_cleaning:
                contactsRepository.removeContacts();
                refresh();
                Toast.makeText(this, "yep, sure", Toast.LENGTH_SHORT).show();
                menuDelete.setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("contact", ((Contact) parent.getItemAtPosition(position)).getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void safeRemoveItem( final int position){ ////TODO ?????
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Warning")
                .setMessage("You sure?")
                .setNegativeButton("NO",null)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactsRepository.getInstance().removeContact(position);
                        Toast.makeText(ContactListActivity.this, "delete_finish_elements"+ContactsRepository.getInstance().getCountContacts(), Toast.LENGTH_SHORT).show();
                        refresh();
                        if (ContactsRepository.getInstance().getCountContacts() == 0) {
                            menuDelete.setVisible(false);
                        }
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}
