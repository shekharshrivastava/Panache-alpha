package com.app.ssoft.panache.Controller;

import com.app.ssoft.panache.Utils.DBHelper;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by shekharshrivastava on 10/04/18.
 */

public class UserRegistrationController {

    public static void addUserData(DatabaseReference mDatabase, String userName, String emailID, String phoneNumber, String gender) {
        DBHelper.getInstance().addUserDetails(mDatabase,userName,emailID,phoneNumber,gender);
    }
}
