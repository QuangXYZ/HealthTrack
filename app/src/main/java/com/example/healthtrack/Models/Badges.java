package com.example.healthtrack.Models;

import android.media.Image;
import android.widget.TextView;

public class Badges {
    String title;
    int image;

    public Badges(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
