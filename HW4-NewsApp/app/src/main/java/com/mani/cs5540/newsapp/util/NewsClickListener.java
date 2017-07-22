package com.mani.cs5540.newsapp.util;

import java.util.EventListener;

/**
 * Created by maeswara on 6/24/2017.
 */
public interface NewsClickListener extends EventListener {
   //[Mani]TODO: Custom listener that main activity implements to display
   // news in a browser
   public abstract void onNewsClick(NewsClickEvent event);
}
