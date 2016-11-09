package com.fbx.demo;

import android.content.Context;
import android.view.MotionEvent;

import com.monyetmabuk.rajawali.tutorials.R;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.fbx.LoaderFBX;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.Renderer;

/**
 * Created by 1100443 on 2016. 11. 8..
 */

public final class FBXRenderer extends Renderer {
    private Animation3D mAnim;

    public FBXRenderer(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {
        //getCurrentScene().setBackgroundColor(0xffffff);

        mAnim = new RotateOnAxisAnimation(Vector3.Axis.Y, 360);
        mAnim.setDurationMilliseconds(16000);
        mAnim.setRepeatMode(Animation.RepeatMode.INFINITE);
        getCurrentScene().registerAnimation(mAnim);

        try {
            // -- Model by Sampo Rask
            // (http://www.blendswap.com/blends/characters/low-poly-rocks-character/)
            LoaderFBX parser = new LoaderFBX(this,
                    R.raw.lowpolyrocks_character_blendswap);

//            LoaderOBJ parser = new LoaderOBJ(this, R.raw.character_obj);

//            Loader3DSMax parse = new Loader3DSMax(R.raw.character_3ds);

            parser.parse();
            Object3D o = parser.getParsedObject();
            o.setTransparent(true);
            o.setY(-.5f);
            getCurrentScene().addChild(o);

            mAnim.setTransformable3D(o);
            mAnim.play();
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        getCurrentScene().setBackgroundColor(0);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {


    }
}