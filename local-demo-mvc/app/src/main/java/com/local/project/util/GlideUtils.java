package com.local.project.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.local.project.R;

/**
 * Description:
 */
public class GlideUtils {

    /**
     * 普通图片
     *
     * @param url
     * @param imageView
     */
    public static void intoImageView(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.icon_error)
                        .placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 普通图片
     *
     * @param url
     * @param imageView
     */
    public static void intoImageView(Integer url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 圆图片
     *
     * @param url
     * @param imageView
     */
    public static void intoCircleImageView(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).error(R.drawable.icon_error).placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 圆角图片
     *
     * @param url
     * @param imageView
     */
    public static void intoRoundImageView(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(imageView.getContext()))
                        .error(R.drawable.icon_error)
                        .placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 圆角图片
     *
     * @param url
     * @param imageView
     */
    public static void intoRoundImageView(String url, ImageView imageView,int dp) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(imageView.getContext(),dp))
                        .error(R.drawable.icon_error)
                        .placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 圆角图片
     *
     * @param url
     * @param imageView
     */
    public static void intoRoundImageView(int url, ImageView imageView,int dp) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(imageView.getContext(),dp))
                        .error(R.drawable.icon_error)
                        .placeholder(R.drawable.icon_error))
                .into(imageView);
    }

    /**
     * 圆角图片
     *
     * @param url
     * @param imageView
     */
    public static void intoRoundImageView(Integer url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(imageView.getContext()))
                        .error(R.drawable.icon_error)
                        .placeholder(R.drawable.icon_error))
                .into(imageView);
    }

}
