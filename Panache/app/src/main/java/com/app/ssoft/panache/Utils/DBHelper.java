package com.app.ssoft.panache.Utils;

import com.app.ssoft.panache.Model.UserRegistrationModel;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by shekharshrivastava on 10/04/18.
 */

public class DBHelper {

    // static variable single_instance of type Singleton
    private static DBHelper single_instance = null;

    // variable of type String
    public String s;

    public static DBHelper getInstance() {
        if (single_instance == null)
            single_instance = new DBHelper();

        return single_instance;
    }

    public void addUserDetails(DatabaseReference mDatabase, String userName, String emailId, String phoneNumber, String gender) {
       String userId = mDatabase.push().getKey();
        UserRegistrationModel userRegistration = new UserRegistrationModel(userId, userName, emailId, phoneNumber, gender);
        mDatabase.child(userId).setValue(userRegistration);
    }


}
