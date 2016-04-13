package com.trendtechnology.notes.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

public final class DataBinder {

    @BindingAdapter("bindImageUrl")
    public static void setImageUrl(ImageView imageView, Uri url) {
        Context context = imageView.getContext();
        imageView.setImageURI(url);
    }
}
