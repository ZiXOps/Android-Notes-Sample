package com.trendtechnology.notes.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

public final class DataBinder {
    @BindingAdapter("bindImageUrl")
    public static void setImageUrl(ImageView imageView, String imageName) {
        Context context = imageView.getContext();
        Bitmap bitmap = StoreImageUtils.loadBitmap(context, imageName);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
