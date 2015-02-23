package com.soloviev.contactsapp;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {

    private static int sCounter = 0;

    private int mId;
    private String mPhone;
    private String mName;
    private String mEmail;
    private String mAddress;
    private Date mBirthDate;
    private String mOccupation;
    /*флаг для проверки создания контакта с ручным заданием ID*/
    private boolean setManualContact;


    public Contact() {
        mId = ++sCounter;
        setManualContact = false;
    }
/*создание контакта с ручным вводом  ID- не рекоменд.*/
    Contact(int mId) {
        this.mId = mId;
        setManualContact = true;
    }

    public static Contact generateNewContact() {
        Contact cont = new Contact();
        cont.setAddress("address ");
        cont.setBirthDate(new Date(1, 1, 1900));
        cont.setEmail("vasya" + cont.getId() + "@gmail.com");
        cont.setName("Vasya Pupkin " + cont.getId());
        cont.setOccupation("no job");
        cont.setPhone("12345 ");
        return cont;
    }

    public boolean isSetManualContact() {
        return setManualContact;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact contact = (Contact) o;
        return mId == contact.mId;

    }

    @Override
    public int hashCode() {
        return mId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "mPhone='" + mPhone + '\'' +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mBirthDateView=" + mBirthDate +
                ", mOccupation='" + mOccupation + '\'' +
                '}';
    }
}
