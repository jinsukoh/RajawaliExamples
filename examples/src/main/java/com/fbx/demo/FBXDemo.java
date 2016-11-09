package com.fbx.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup;

import com.monyetmabuk.rajawali.tutorials.R;

import org.rajawali3d.renderer.Renderer;

public class FBXDemo extends Activity {

    Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fbx_demo);
        final org.rajawali3d.view.SurfaceView surface = new org.rajawali3d.view.SurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        surface.setTransparent(true);

        // Add mSurface to your root view
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        renderer = new FBXRenderer(this);
        surface.setSurfaceRenderer(renderer);
        surface.setTransparent(true);

    }


}
