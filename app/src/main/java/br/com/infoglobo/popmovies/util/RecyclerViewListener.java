package br.com.infoglobo.popmovies.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rodrigo on 30/10/16.
 */

public interface RecyclerViewListener {

    public void onClickListener(View view, MotionEvent event, int position);
}
