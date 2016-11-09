package com.fbx.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fbx.demo.FloatingWindow;
import com.monyetmabuk.rajawali.tutorials.R;

public class StartFloatingAgent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_floating_agent);
        startService(new Intent(this, FloatingWindow.class));
        finish();
    }


}
