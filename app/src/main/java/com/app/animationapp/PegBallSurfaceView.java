package com.app.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
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
        final List<PointF> coordinates = new ArrayList<PointF>();
        coordinates.add(new PointF(screenX / 2f, screenY / 2f));
        coordinates.add(new PointF(screenX * 0.25f, screenY / 2f));
        coordinates.add(new PointF(screenX * 0.75f, screenY / 2f));
        pegs = new ArrayList<PegParticle>();
        for (PointF coordinate : coordinates) {
            final PegParticle peg = new PegParticle(getResources(), screenRatioX, screenRatioY)
                    .setSpawnCenterX(coordinate.x)
                    .setSpawnCenterY(coordinate.y)
                    .build();
            pegs.add(peg);
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void update() {
        Log.d(TAG,"update");
        final List<BallCollectible> drops = new ArrayList<>();
        if (!balls.isEmpty()) {
            for (BallCollectible ball : balls) {
                if (ball.positionY > screenY && !ball.isCollected) {
                    ball.isCollected = true;
                    drops.add(ball);
                }
                if (ball.isSpawnDelayFinished()) {
                    // Apply gravity to the ball
                    ball.updatePosition(1f * screenRatioY); // Gravity factor
                    // Check for collisions with pegs
                    for (PegParticle peg : pegs) {
                        if (isColliding(ball, peg)) {
                            // Resolve collision to prevent overlap
                            resolveCollision(ball, peg);
                        }
                    }
                } else {
                    ball.updateSpawnDelay(1);
                }
            }
            // Remove balls that are collected
            for (BallCollectible drop : drops) {
                if (drop.isCollected) {
                    balls.remove(drop);
                }
            }
        }
    }

    private boolean isColliding(BallCollectible ball, PegParticle peg) {
        float dx = ball.positionX + ball.getRadius() - (peg.positionX + peg.getRadius());
        float dy = ball.positionY + ball.getRadius() - (peg.positionY + peg.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (ball.getRadius() + peg.getRadius());
    }

    private void resolveCollision(BallCollectible ball, PegParticle peg) {
        // Get the difference in positions
        float dx = ball.positionX + ball.getRadius() - (peg.positionX + peg.getRadius());
        float dy = ball.positionY + ball.getRadius() - (peg.positionY + peg.getRadius());
        // Calculate the distance between the centers
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        // Calculate overlap
        float overlap = (ball.getRadius() + peg.getRadius()) - distance;
        if (overlap > 0) {
            // Normalize the direction vector (dx, dy)
            dx /= distance;
            dy /= distance;
            // Move the ball out of the peg by the overlap distance
            ball.positionX += dx * overlap;
            ball.positionY += dy * overlap;
            // Bounce by reversing Y velocity (or you can adjust both X and Y velocities)
            ball.velocityY = -ball.velocityY;
            // Optional: Modify X velocity for more realistic deflection
            ball.velocityX += dx * 0.1f;
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