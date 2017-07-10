package com.m2e.cs5540.autopresence.context;

import android.content.Context;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.exception.AppException;

/**
 * Created by maeswara on 7/8/2017.
 */
public class AppContext {
   private static AppContext appContext = null;
   private ViewGroup mainViewGroup;

   private AppContext(ViewGroup mainViewGroup) {
      this.mainViewGroup = mainViewGroup;
   }

   public static void initContext(ViewGroup mainViewGroup) {
      if(appContext == null) {
         appContext = new AppContext(mainViewGroup);
      }
   }

   public static AppContext getCurrentAppContext() {
      if(appContext == null) {
         throw new AppException("AppContext not initialized!");
      }
      return appContext;
   }

   public ViewGroup getMainViewGroup() {
      return mainViewGroup;
   }

   public Context getMainViewContext() {
      if(mainViewGroup != null) {
         return mainViewGroup.getContext();
      }
      return null;
   }
}
