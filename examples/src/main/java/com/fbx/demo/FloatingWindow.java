package com.fbx.demo;

import android.app.ActionBar;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.monyetmabuk.rajawali.tutorials.R;

import org.rajawali3d.renderer.Renderer;

import static android.R.attr.button;

/**
 * Created by 1100443 on 2016. 11. 8..
 */

public class FloatingWindow extends Service {
    private WindowManager wm;
    private FrameLayout mFrameLayout;
    Renderer mRenderer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final WindowManager.LayoutParams params = setSurface();

        mFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            int x, y;
            float touchedX, touchedY;
            private WindowManager.LayoutParams updateParameters = params;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updateParameters.x;
                        y = updateParameters.y;

                        touchedX = event.getRawX();
                        touchedY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updateParameters.x = (int) (x + (event.getRawX() - touchedX));
                        updateParameters.y = (int) (y + (event.getRawY() - touchedY));
                        wm.updateViewLayout(mFrameLayout, updateParameters);

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @NonNull
    private WindowManager.LayoutParams setSurface() {
        wm = (WindowManager)getSystemService(WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFrameLayout = (FrameLayout) inflater.inflate(R.layout.floating_layout, null);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //mLinearLayout.setBackgroundColor(Color.argb(66, 256, 0, 0));

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(400, 800, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.x = 200;
        params.y = 200;

        params.gravity = Gravity.CENTER | Gravity.CENTER;

        wm.addView(mFrameLayout, params);

        final org.rajawali3d.view.SurfaceView surface = new org.rajawali3d.view.SurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        surface.setTransparent(true);

        LinearLayout characterLayout = (LinearLayout)mFrameLayout.findViewById(R.id.character_layout);
        characterLayout.addView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));


//        renderer = new FBXRenderer(this);
        mRenderer = new MD5AnimationRenderer(this);
        surface.setSurfaceRenderer(mRenderer);
        surface.setTransparent(true);

        setButtonListener(mFrameLayout);
        return params;
    }

    View.OnClickListener mClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!(mRenderer instanceof MD5AnimationRenderer)){
                return;
            }

            if(v.getId() == R.id.button1){
                ((MD5AnimationRenderer)mRenderer).transitionAnimation(0);
            } else if(v.getId() == R.id.button2){
                ((MD5AnimationRenderer)mRenderer).transitionAnimation(1);
            } else if(v.getId() == R.id.button3){
                ((MD5AnimationRenderer)mRenderer).transitionAnimation(2);
            }
        }
    };

    public void setButtonListener(FrameLayout layout) {
        layout.findViewById(R.id.button1).setOnClickListener(mClickListener);
        layout.findViewById(R.id.button2).setOnClickListener(mClickListener);
        layout.findViewById(R.id.button3).setOnClickListener(mClickListener);
    }
}
