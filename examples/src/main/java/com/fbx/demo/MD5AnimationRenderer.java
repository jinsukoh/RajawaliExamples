package com.fbx.demo;

import android.content.Context;
import android.view.MotionEvent;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.animation.mesh.SkeletalAnimationObject3D;
import org.rajawali3d.animation.mesh.SkeletalAnimationSequence;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.md5.LoaderMD5Anim;
import org.rajawali3d.loader.md5.LoaderMD5Mesh;
import org.rajawali3d.renderer.Renderer;

/**
 * Created by 1100443 on 2016. 11. 9..
 */

public class MD5AnimationRenderer extends Renderer {

    private DirectionalLight mLight;
    private SkeletalAnimationObject3D mObject;
    private SkeletalAnimationSequence mSequenceWalk;
    private SkeletalAnimationSequence mSequenceIdle;
    private SkeletalAnimationSequence mSequenceArmStretch;
    private SkeletalAnimationSequence mSequenceBend;

    public MD5AnimationRenderer(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {
        mLight = new DirectionalLight(0, -0.2f, -1.0f); // set the direction
        mLight.setColor(1.0f, 1.0f, .8f);
        mLight.setPower(1);

        getCurrentScene().addLight(mLight);
        getCurrentCamera().setZ(8);

        try {
            LoaderMD5Mesh meshParser = new LoaderMD5Mesh(this,
                    R.raw.ingrid_mesh);
            meshParser.parse();

            LoaderMD5Anim animParser = new LoaderMD5Anim("idle", this,
                    R.raw.ingrid_idle);
            animParser.parse();

            mSequenceIdle = (SkeletalAnimationSequence) animParser
                    .getParsedAnimationSequence();

            animParser = new LoaderMD5Anim("walk", this, R.raw.ingrid_walk);
            animParser.parse();

            mSequenceWalk = (SkeletalAnimationSequence) animParser
                    .getParsedAnimationSequence();

            animParser = new LoaderMD5Anim("armstretch", this,
                    R.raw.ingrid_arm_stretch);
            animParser.parse();

            mSequenceArmStretch = (SkeletalAnimationSequence) animParser
                    .getParsedAnimationSequence();

            animParser = new LoaderMD5Anim("bend", this, R.raw.ingrid_bend);
            animParser.parse();

            mSequenceBend = (SkeletalAnimationSequence) animParser
                    .getParsedAnimationSequence();

            mObject = (SkeletalAnimationObject3D) meshParser
                    .getParsedAnimationObject();
            mObject.setAnimationSequence(mSequenceIdle);
            mObject.setFps(24);
            mObject.setScale(.8f);
            mObject.play();

            getCurrentScene().addChild(mObject);
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }

    public void transitionAnimation(int id) {
        switch (id) {
            case 0:
                mObject.transitionToAnimationSequence(mSequenceIdle, 1000);
                break;
            case 1:
                mObject.transitionToAnimationSequence(mSequenceWalk, 1000);
                break;
            case 2:
                mObject.transitionToAnimationSequence(mSequenceArmStretch, 1000);
                break;
            case 3:
                mObject.transitionToAnimationSequence(mSequenceBend, 1000);
                break;
        }
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
