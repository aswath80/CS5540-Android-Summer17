package com.mani.cs5540.newsapp.util;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.util.EventListener;

/**
 * Created by maeswara on 6/24/2017.
 */
public interface NewsClickListener extends EventListener {
    public abstract void onNewsClick(NewsClickEvent event);
}
