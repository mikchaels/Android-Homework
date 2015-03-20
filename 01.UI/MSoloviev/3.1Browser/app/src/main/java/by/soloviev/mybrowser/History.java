package by.soloviev.mybrowser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 07.02.2015.
 */
public class History implements Serializable {
    private String mAddress;
    private String mTime;

    public History(String address) {
        mAddress = address;
        mTime = new Date().toString();
    }

    public History(String address, String date) {
        mAddress = address;
        mTime = date;
    }

    public String getAddress() {
        return mAddress;
    }


    public String getTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return (mAddress + "   :   " + mTime.toString());
    }
}

