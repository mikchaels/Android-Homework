package by.soloviev.mybrowser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 07.02.2015.
 */
public class History implements Serializable {
    private String mAddress;
    private Date mTime;

    public History(String adress) {
        mAddress = adress;
        mTime = new Date();
    }

    public String getAddress() {
        return mAddress;
    }


    public Date getTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return
                mAddress +
                "   :   " + mTime.toString() ;
    }
}

