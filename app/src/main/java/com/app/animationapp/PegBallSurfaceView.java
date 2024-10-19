package com.app.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PegBallSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback {

    public static String TAG = PegBallSurfaceView.class.getSimpleName();
    public static PegBallSurfaceView newInstance(Context context, int screenX, int screenY) {
        return new PegBallSurfaceView(context, screenX, screenY);
    }
    private List<BallCollectible> balls;
    private List<PegParticle> pegs;

    public PegBallSurfaceView(Context context) {
        super(context);
    }

    public PegBallSurfaceView(Context context , AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PegBallSurfaceView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public PegBallSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY);
    }

    private void initialize(int screenX, int screenY) {
        setZOrderOnTop(true);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        thread = new CustomThread(this, surfaceHolder);
        //region Peg Ball
        initializeBalls(1);
        initializePegs(1);
        //endregion
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        if (thread.getState().equals(Thread.State.TERMINATED)) {
            holder.addCallback(this);
            holder.setFormat(PixelFormat.TRANSPARENT);
            thread = new CustomThread(this, holder);
        }
        thread.onStart();
    }

    private void initializeBalls(int quantity) {
        //TODO: On going
        balls = new ArrayList<BallCollectible>();
        final float centerX = screenX / 2f;
        final BallCollectible ball = new BallCollectible(getResources(), screenRatioX, screenRatioY)
                .setSpawnX(centerX)
                .setSpawnY(0)
                .build();
        balls.add(ball);
    }

    private void initializePegs(int row) {
        //TODO: On going
        final float centerX = screenX / 2f;
        final float centerY = screenY / 2f;
        pegs = new ArrayList<PegParticle>();
        final PegParticle peg = new PegParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX(centerX)
                .setSpawnCenterY(centerY)
                .build();
        pegs.add(peg);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void update() {
        Log.d(TAG,"update");
        final List<BallCollectible> drops = new ArrayList<BallCollectible>();
        if(balls.isEmpty() == false) {
            for (BallCollectible ball : balls) {
                if (ball.positionY > screenY && ball.isCollected == false) {
                    ball.isCollected = true;
                    drops.add(ball);
                }
                if (ball.isSpawnDelayFinished()) {
                    ball.positionY += 5f * screenRatioY;
                } else {
                    ball.updateSpawnDelay(1);
                }
            }
            for (BallCollectible drop : drops)
                if (drop.isCollected)
                    balls.remove(drop);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw");
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.white));
        if(balls.isEmpty() == false) {
            for (BallCollectible ball : balls) {
                canvas.drawBitmap(ball.getBitmap(), ball.positionX, ball.positionY, paint);
            }
        }
        if (pegs.isEmpty() == false) {
            for (PegParticle peg : pegs) {
                canvas.drawBitmap(peg.getBitmap(), peg.positionX, peg.positionY, paint);
            }
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        thread.onPause();
    }
}