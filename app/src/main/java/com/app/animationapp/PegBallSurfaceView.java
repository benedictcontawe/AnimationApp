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
import java.util.Arrays;
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
        final ArrayList<Integer> ballSpawns = new ArrayList<Integer>(Arrays.asList(0, 5, 13, 20));
        for (Integer ballSpawn: ballSpawns) {
            final BallCollectible ball = new BallCollectible(getResources(), screenRatioX, screenRatioY)
                    .setSpawnX(centerX)
                    .setSpawnY(0)
                    .setSpawnDelay(ballSpawn)
                    .build();
            balls.add(ball);
        }
    }

    private void initializePegs(int row) {
        //TODO: On going
        final List<PointF> coordinates = new ArrayList<PointF>();
        coordinates.add(new PointF(screenX / 2f, screenY / 2f));
        coordinates.add(new PointF(screenX * 0.25f, screenY / 2f));
        coordinates.add(new PointF(screenX * 0.75f, screenY / 2f));
        coordinates.add(new PointF(screenX * 0.10f, screenY * 0.75f));
        coordinates.add(new PointF(screenX * 0.30f, screenY * 0.75f));
        coordinates.add(new PointF(screenX * 0.50f, screenY * 0.75f));
        coordinates.add(new PointF(screenX * 0.70f, screenY * 0.75f));
        coordinates.add(new PointF(screenX * 0.90f, screenY * 0.75f));
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
            for (int i = 0; i < balls.size(); i++) {
                BallCollectible ball = balls.get(i);
                if (ball.positionY > screenY && !ball.isCollected) {
                    ball.isCollected = true;
                    drops.add(ball);
                }
                if (ball.isSpawnDelayFinished()) {
                    // Apply gravity to the ball
                    ball.updatePosition(1.3f * screenRatioY); // Gravity factor
                    // Check for collisions with pegs
                    for (PegParticle peg : pegs) {
                        if (isColliding(ball, peg)) {
                            resolveCollision(ball, peg);
                        }
                    }
                    // Check for collisions with other balls
                    for (int j = i + 1; j < balls.size(); j++) {
                        BallCollectible otherBall = balls.get(j);
                        if (isColliding(ball, otherBall)) {
                            resolveCollision(ball, otherBall);
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

    private boolean isColliding(BallCollectible ball1, BallCollectible ball2) {
        float dx = ball1.positionX + ball1.getRadius() - (ball2.positionX + ball2.getRadius());
        float dy = ball1.positionY + ball1.getRadius() - (ball2.positionY + ball2.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (ball1.getRadius() + ball2.getRadius());
    }

    private boolean isColliding(BallCollectible ball, PegParticle peg) {
        float dx = ball.positionX + ball.getRadius() - (peg.positionX + peg.getRadius());
        float dy = ball.positionY + ball.getRadius() - (peg.positionY + peg.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (ball.getRadius() + peg.getRadius());
    }

    private void resolveCollision(BallCollectible ball1, BallCollectible ball2) {
        // Get the difference in positions
        float dx = ball1.positionX + ball1.getRadius() - (ball2.positionX + ball2.getRadius());
        float dy = ball1.positionY + ball1.getRadius() - (ball2.positionY + ball2.getRadius());

        // Calculate the distance between the centers
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Calculate overlap
        float overlap = (ball1.getRadius() + ball2.getRadius()) - distance;

        if (overlap > 0) {
            // Normalize the direction vector (dx, dy)
            dx /= distance;
            dy /= distance;

            // Move the balls out of each other by half of the overlap distance
            ball1.positionX += dx * overlap / 2;
            ball1.positionY += dy * overlap / 2;
            ball2.positionX -= dx * overlap / 2;
            ball2.positionY -= dy * overlap / 2;

            // Exchange velocities for a basic bounce effect
            float tempVelocityX = ball1.velocityX;
            float tempVelocityY = ball1.velocityY;

            // Damping factor to reduce bounce height
            float dampingFactor = 0.1f; // Adjust this value to make the bounce lower

            // Reduce the bounce intensity by applying the damping factor
            ball1.velocityX = ball2.velocityX * dampingFactor;
            ball1.velocityY = ball2.velocityY * dampingFactor;

            ball2.velocityX = tempVelocityX * dampingFactor;
            ball2.velocityY = tempVelocityY * dampingFactor;
        }
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
            // Damping factor to reduce bounce height
            float dampingFactor = 0.1f;  // Adjust this value for less bounce
            // Reduce the bounce intensity by applying the damping factor
            ball.velocityY = -ball.velocityY * dampingFactor;
            // Optional: Modify X velocity for more realistic deflection
            ball.velocityX += dx * 0.1f * dampingFactor;
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