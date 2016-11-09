package com.fbx.demo;

import android.app.ActionBar;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fbx.demo.FBXRenderer;

import org.rajawali3d.renderer.Renderer;

/**
 * Created by 1100443 on 2016. 11. 8..
 */

public class FloatingWindow extends Service {
    private WindowManager wm;
    private LinearLayout ll;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new LinearLayout(this);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //ll.setBackgroundColor(Color.argb(66, 256, 0, 0));

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(400, 800, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.x = 200;
        params.y = 200;

        params.gravity = Gravity.CENTER | Gravity.CENTER;

        wm.addView(ll, params);

        final org.rajawali3d.view.SurfaceView surface = new org.rajawali3d.view.SurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        surface.setTransparent(true);

        // Add mSurface to your root view
        ll.addView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        Renderer renderer;
        renderer = new FBXRenderer(this);
        surface.setSurfaceRenderer(renderer);
        surface.setTransparent(true);

        ll.setOnTouchListener(new View.OnTouchListener() {

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
                        wm.updateViewLayout(ll, updateParameters);

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
