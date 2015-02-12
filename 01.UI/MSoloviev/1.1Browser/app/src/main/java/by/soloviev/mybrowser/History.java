package by.soloviev.mybrowser;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 07.02.2015.
 */
public class History implements Serializable {
    private String mAdress;
    private Date mTime;

    public History(String adress) {
        mAdress = adress;
        mTime = new Date();
    }

    public String getAdress() {
        return mAdress;
    }


    public Date getTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return
                mAdress  +
                "   :   " + mTime ;
    }
}

