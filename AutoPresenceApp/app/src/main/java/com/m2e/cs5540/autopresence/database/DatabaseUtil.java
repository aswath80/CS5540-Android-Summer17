package com.m2e.cs5540.autopresence.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2e.cs5540.autopresence.exception.AppException;

/**
 * Created by maeswara on 7/8/2017.
 */
public class DatabaseUtil {
   private static final String TAG = DatabaseUtil.class.getName();
   private DatabaseReference database =
         FirebaseDatabase.getInstance().getReference();
   private static DatabaseUtil databaseUtil = new DatabaseUtil();

   private DatabaseUtil() {

   }

   public static DatabaseUtil getInstance() {
      return databaseUtil;
   }

   public String getUserPassword(String username) {
      DatabaseReference userRef = database.child("users").child(username);
      Log.d(TAG, "$$$$$$$$ userRef = " + userRef);
      if (userRef == null) {
         throw new AppException(
               "User " + username + " not found in the system");
      }
      DatabaseReference passwordRef = userRef.child("encryptedPassword");
      Log.d(TAG, "$$$$$$$$ passwordRef = " + passwordRef);
      String password = getStringValueOnce(passwordRef);
      return password;
   }

   private String getStringValueOnce(DatabaseReference passwordRef) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final String[] password = {null};
      passwordRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            password[0] = dataSnapshot.getValue(String.class);
            wait[0] = false;
            Log.d(TAG, "$$$$$$$$ passwordRef received callback with password " +
                  "= " + password[0]);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getStringValueOnce failed",
                  databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] = true && password[0] == null) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getStringValueOnce failed", exceptions[0]);
      }
      return password[0];
   }
}
