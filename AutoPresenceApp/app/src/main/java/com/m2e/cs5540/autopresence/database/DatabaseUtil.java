package com.m2e.cs5540.autopresence.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseRegistration;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

   public void createUser(User user) {
      User existingUser = getUser(user.getLogin());
      if (existingUser != null) {
         throw new AppException("User with login " + user.getLogin() + " " +
               "already exists in the system!");
      }
      try {
         DatabaseReference usersRef = database.child("users");
         Log.d(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            usersRef.child("user").setValue(user);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException(
               "Error saving user info for user " + user.getLogin() +
                     " into firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
   }

   public void createCourse(Course course) {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.d(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            coursesRef.child("course").setValue(course);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException(
               "Error saving user info for user " + course.getId() +
                     " into firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
   }

   public void createCourseRegistration(CourseRegistration courseRegistration) {
      try {
         DatabaseReference courseRegsRef = database.child(
               "courseRegistrations");
         Log.d(TAG, "$$$ courseRegsRef: " + courseRegsRef);
         if (courseRegsRef != null) {
            courseRegsRef.child("courseRegistration").setValue(
                  courseRegistration);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error saving user info for user " +
               courseRegistration.getUserId() + " - " +
               courseRegistration.getCourseId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public void updateUserCoordinate(UserCoordinate userCoordinate) {
      try {
         DatabaseReference userCoordsRef = database.child("userCoordinates");
         Log.d(TAG, "$$$ userCoordsRef: " + userCoordsRef);
         if (userCoordsRef != null) {
            UserCoordinate currUserCoordinate = getChildOnce(
                  userCoordsRef.orderByChild("userId")
                        .equalTo(userCoordinate.getUserId()),
                  UserCoordinate.class);
            Log.d(TAG, "$$$ currUserCoordinate: " + currUserCoordinate);
            if (currUserCoordinate == null) {
               userCoordsRef.child("userCoordinate").setValue(userCoordinate);
            } else {
               Map<String, Object> updateMap = getUpdateMap(userCoordinate);
               userCoordsRef.updateChildren(updateMap);
            }
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error updating userCoordinate info for user " +
               userCoordinate.getUserId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   private Map<String, Object> getUpdateMap(Object obj) {
      try {
         Map<String, Object> updateMap = new HashMap<>();
         Field[] fields = obj.getClass().getDeclaredFields();
         if (fields != null) {
            for (Field f : fields) {
               f.setAccessible(true);
               updateMap.put(f.getName(), f.get(obj));
            }
         }
         return updateMap;
      } catch (Exception e) {
         Log.e(TAG, "getUpdateMap failed", e);
         throw new AppException(
               "Error creating getUpdateMap for obj " + obj + ".  Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public User getUser(String login) {
      try {
         DatabaseReference usersRef = database.child("users");
         Log.d(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            Query userQuery = usersRef.orderByChild("login").equalTo(login);
            //Log.d(TAG, "$$$ userQuery: " + userQuery.getRef());
            User user = getChildOnce(userQuery, User.class);
            return user;
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error querying user info for login " + login +
               " from firebase. Cause: " + e.getClass().getName() + ": " +
               e.getMessage(), e);
      }
      return null;
   }

   public Course getCourse(String courseId) {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.d(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            Query courseQuery = coursesRef.orderByChild("id").equalTo(courseId);
            Log.d(TAG, "$$$ courseQuery: " + courseQuery);
            Course course = getValueOnce(courseQuery, Course.class);
            return course;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(
               "Error querying course info for courseId " + courseId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   private <T extends Object> T getValueOnce(DatabaseReference dbRef,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            objList.add(dataSnapshot.getValue(valueType));
            wait[0] = false;
            Log.d(TAG, "$$$ object received callback = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] = true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      return objList.size() > 0 ? objList.get(0) : null;
   }

   private <T extends Object> T getChildOnce(DatabaseReference dbRef,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
               objList.add(dataSnapshot.getChildren().iterator().next()
                     .getValue(valueType));
            }
            wait[0] = false;
            Log.d(TAG, "$$$ object received callback = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] = true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      return objList.size() > 0 ? objList.get(0) : null;
   }

   private <T extends Object> T getValueOnce(Query dbQuery,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            objList.add(dataSnapshot.getValue(valueType));
            wait[0] = false;
            Log.d(TAG, "$$$ object received callback = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] = true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      return objList.size() > 0 ? objList.get(0) : null;
   }

   private <T extends Object> T getChildOnce(Query dbQuery,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               objList.add(dataSnapshot.getChildren().iterator().next()
                     .getValue(valueType));
            }
            wait[0] = false;
            Log.d(TAG, "$$$ object received callback = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] = true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      return objList.size() > 0 ? objList.get(0) : null;
   }
}
