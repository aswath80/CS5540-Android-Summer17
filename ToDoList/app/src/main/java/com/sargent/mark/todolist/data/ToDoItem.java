package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */
public class ToDoItem {
   private String description;
   private String dueDate;
   //[Mani]TODO:Added field done to ToDoItem
   //[Mani]Field that marks a To-do Item as done. Added getter/setter for
   // done below.
   private boolean done;

   public ToDoItem(String description, String dueDate) {
      this.description = description;
      this.dueDate = dueDate;
      //[Mani]Default done flag to false on to-do creation.
      this.done = false;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDueDate() {
      return dueDate;
   }

   public void setDueDate(String dueDate) {
      this.dueDate = dueDate;
   }

   public boolean isDone() {
      return done;
   }

   public void setDone(boolean done) {
      this.done = done;
   }
}
