package com.m2e.cs5540.autopresence.test;

import android.util.Log;

import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by maeswara on 7/15/2017.
 */
public class AppTest {
   private static final String TAG = AppTest.class.getName();

   private String encryptPassword(String password)
         throws NoSuchAlgorithmException {
      MessageDigest digest = MessageDigest.getInstance(
            "MD5");//Create MessageDigest object for MD5
      digest.update(password.getBytes(), 0,
            password.length());//Update input string in message digest
      String encryptedPass = new BigInteger(1, digest.digest()).toString(16);
      return encryptedPass;
   }

   public void testCreateUser() throws Exception {
      DatabaseUtil dbUtil = DatabaseUtil.getInstance();
      User user = new User();
      user.setId(UUID.randomUUID().toString());
      user.setLogin("mani@test.com");
      user.setName("Manikandan Eswaran");
      String encryptedPass = encryptPassword("password");
      Log.d(TAG, "$$$ encryptedPass = "+encryptedPass);
      user.setPassword(encryptedPass);
      dbUtil.createUser(user);
   }
}
