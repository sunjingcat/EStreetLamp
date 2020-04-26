package com.zz.lib.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import android.view.View;

import com.zz.lib.core.R;


/**
 * Created by admin on 2018/5/17.
 */

public class AnimeUtils {

    public static Bundle sceneTransAnime(Context context) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left);
        return options.toBundle();
    }

    public static Bundle sceneTransAnimeExit2(Context context) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.slide_in_left, R.anim.slide_out_right);
        return options.toBundle();
    }

    public static void sceneTransAnimeExit(Activity context) {
        context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static Bundle sceneTransSingleContentAnime(Activity context, View view, String desc) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, desc);
        return options.toBundle();
    }
}
