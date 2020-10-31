package com.example.retrofittutorial;

import android.net.Uri;

public class Customer {
    private int mId;
    private String mName;
    private int mVisitOrder;
    private Uri mProfilePicture;

    public Customer(int mId, String mName, int mVisitOrder, Uri mProfilePicture) {
        this.mId = mId;
        this.mName = mName;
        this.mVisitOrder = mVisitOrder;
        this.mProfilePicture = mProfilePicture;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public int getmVisitOrder() {
        return mVisitOrder;
    }

    public Uri getmProfilePicture() {
        return mProfilePicture;
    }
}
