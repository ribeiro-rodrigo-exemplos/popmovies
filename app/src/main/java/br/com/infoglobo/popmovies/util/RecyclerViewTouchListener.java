package br.com.infoglobo.popmovies.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rodrigo on 30/10/16.
 */

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

    private Context context;
    private RecyclerViewListener listener;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public RecyclerViewTouchListener(Context context,RecyclerView recyclerView,
                                     RecyclerViewListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.recyclerView = recyclerView;

        criarDetectorDeTouch();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
    {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){}

    private void criarDetectorDeTouch()
    {
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e)
            {
                super.onLongPress(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());

                if(view != null)
                    listener.onClickListener(view,e,recyclerView.getChildAdapterPosition(view));

                return true;
            }
        });
    }
}
