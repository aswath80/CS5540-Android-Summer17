package com.m2e.cs5540.autopresence.vao;

import java.util.Date;

/**
 * Created by maeswara on 7/15/2017.
 */
public class UserCoordinate {
   private String userId;
   private double currentLatitude;
   private double currentLongitude;
   private Date lastUpdateTime;

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public double getCurrentLatitude() {
      return currentLatitude;
   }

   public void setCurrentLatitude(double currentLatitude) {
      this.currentLatitude = currentLatitude;
   }

   public double getCurrentLongitude() {
      return currentLongitude;
   }

   public void setCurrentLongitude(double currentLongitude) {
      this.currentLongitude = currentLongitude;
   }

   public Date getLastUpdateTime() {
      return lastUpdateTime;
   }

   public void setLastUpdateTime(Date lastUpdateTime) {
      this.lastUpdateTime = lastUpdateTime;
   }
}
