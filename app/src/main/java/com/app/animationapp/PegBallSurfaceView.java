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
    // Define constants for gravity and bounce factors
    private static final float GRAVITY = 0.09f;
    private static final float PEG_BOUNCE_FACTOR = 0.09f; //Adjust for less bouncy collisions
    private static final float BALL_BOUNCE_FACTOR = 0.09f; //Adjust for less bouncy collisions
    private static final float FRICTION = 0.98f; //Apply friction to slow down horizontal motion
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
        final ArrayList<Integer> ballSpawns = new ArrayList<Integer>(Arrays.asList(0, 200, 400, 800));
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
        coordinates.add(new PointF(screenX * 0.25f, screenY * 0.20f));
        coordinates.add(new PointF(screenX * 0.50f, screenY * 0.20f));
        coordinates.add(new PointF(screenX * 0.75f, screenY * 0.20f));

        coordinates.add(new PointF(screenX * 0.10f, screenY * 0.30f));
        coordinates.add(new PointF(screenX * 0.30f, screenY * 0.30f));
        coordinates.add(new PointF(screenX * 0.50f, screenY * 0.30f));
        coordinates.add(new PointF(screenX * 0.70f, screenY * 0.30f));
        coordinates.add(new PointF(screenX * 0.90f, screenY * 0.30f));

        coordinates.add(new PointF(screenX * 0.00f, screenY * 0.40f));
        coordinates.add(new PointF(screenX * 0.20f, screenY * 0.40f));
        coordinates.add(new PointF(screenX * 0.40f, screenY * 0.40f));
        coordinates.add(new PointF(screenX * 0.60f, screenY * 0.40f));
        coordinates.add(new PointF(screenX * 0.80f, screenY * 0.40f));
        coordinates.add(new PointF(screenX * 1.00f, screenY * 0.40f));

        coordinates.add(new PointF(screenX * 0.10f, screenY * 0.50f));
        coordinates.add(new PointF(screenX * 0.30f, screenY * 0.50f));
        coordinates.add(new PointF(screenX * 0.50f, screenY * 0.50f));
        coordinates.add(new PointF(screenX * 0.70f, screenY * 0.50f));
        coordinates.add(new PointF(screenX * 0.90f, screenY * 0.50f));

        coordinates.add(new PointF(screenX * 0.00f, screenY * 0.60f));
        coordinates.add(new PointF(screenX * 0.20f, screenY * 0.60f));
        coordinates.add(new PointF(screenX * 0.40f, screenY * 0.60f));
        coordinates.add(new PointF(screenX * 0.60f, screenY * 0.60f));
        coordinates.add(new PointF(screenX * 0.80f, screenY * 0.60f));
        coordinates.add(new PointF(screenX * 1.00f, screenY * 0.60f));

        coordinates.add(new PointF(screenX * 0.10f, screenY * 0.70f));
        coordinates.add(new PointF(screenX * 0.30f, screenY * 0.70f));
        coordinates.add(new PointF(screenX * 0.50f, screenY * 0.70f));
        coordinates.add(new PointF(screenX * 0.70f, screenY * 0.70f));
        coordinates.add(new PointF(screenX * 0.90f, screenY * 0.70f));

        coordinates.add(new PointF(screenX * 0.00f, screenY * 0.80f));
        coordinates.add(new PointF(screenX * 0.20f, screenY * 0.80f));
        coordinates.add(new PointF(screenX * 0.40f, screenY * 0.80f));
        coordinates.add(new PointF(screenX * 0.60f, screenY * 0.80f));
        coordinates.add(new PointF(screenX * 0.80f, screenY * 0.80f));
        coordinates.add(new PointF(screenX * 1.00f, screenY * 0.80f));
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
        final List<BallCollectible> ballsToRemove = new ArrayList<>();
        for (BallCollectible ball : balls) {
            if (ball.isSpawnDelayFinished()) {
                // Apply gravity to the ball's velocity
                ball.velocityY += GRAVITY;
                // Move the ball based on its current velocity
                ball.positionX += ball.velocityX;
                ball.positionY += ball.velocityY;

                // Check for wall collisions (left and right boundaries of the screen)
                if (ball.positionX <= 0 || ball.positionX + ball.getRadius() * 2 >= screenX) {
                    ball.velocityX = -ball.velocityX * PEG_BOUNCE_FACTOR;
                    ball.positionX = Math.max(0, Math.min(screenX - ball.getRadius() * 2, ball.positionX)); // Keep within bounds
                }
                // Check for collisions with the pegs
                for (PegParticle peg : pegs) {
                    if (isColliding(ball, peg)) {
                        resolveCollision(ball, peg);
                    }
                }
                // Check for ball-to-ball collisions
                for (BallCollectible otherBall : balls) {
                    if (ball != otherBall && isColliding(ball, otherBall)) {
                        resolveCollision(ball, otherBall);
                    }
                }
                // If the ball falls off the screen, mark it for removal
                if (ball.positionY > screenY) {
                    ballsToRemove.add(ball);
                }
            } else {
                ball.updateSpawnDelay(1);
            }
        }
        // Remove balls that fall off the screen
        balls.removeAll(ballsToRemove);
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
        float dx = ball2.positionX + ball2.getRadius() - (ball1.positionX + ball1.getRadius());
        float dy = ball2.positionY + ball2.getRadius() - (ball1.positionY + ball1.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float overlap = (ball1.getRadius() + ball2.getRadius()) - distance;
        if (overlap > 0) {
            dx /= distance;
            dy /= distance;
            // Adjust positions of both balls to resolve overlap
            ball1.positionX -= dx * overlap / 2;
            ball1.positionY -= dy * overlap / 2;
            ball2.positionX += dx * overlap / 2;
            ball2.positionY += dy * overlap / 2;
            // Calculate relative velocity
            float relativeVelocityX = ball2.velocityX - ball1.velocityX;
            float relativeVelocityY = ball2.velocityY - ball1.velocityY;
            float collisionVelocity = relativeVelocityX * dx + relativeVelocityY * dy;
            if (collisionVelocity < 0) {
                // Apply bounce factor and resolve velocities
                float impulse = collisionVelocity;
                ball1.velocityX -= impulse * dx * BALL_BOUNCE_FACTOR;
                ball1.velocityY -= impulse * dy * BALL_BOUNCE_FACTOR;
                ball2.velocityX += impulse * dx * BALL_BOUNCE_FACTOR;
                ball2.velocityY += impulse * dy * BALL_BOUNCE_FACTOR;
                // Apply friction to reduce tangential velocity
                float frictionFactor = 0.98f;  // Adjust for desired friction
                float tangentialVelocityX = relativeVelocityX - collisionVelocity * dx;
                float tangentialVelocityY = relativeVelocityY - collisionVelocity * dy;
                ball1.velocityX += tangentialVelocityX * frictionFactor;
                ball1.velocityY += tangentialVelocityY * frictionFactor;
                ball2.velocityX -= tangentialVelocityX * frictionFactor;
                ball2.velocityY -= tangentialVelocityY * frictionFactor;
            }
        }
    }

    private void resolveCollision(BallCollectible ball, PegParticle peg) {
        float dx = ball.positionX + ball.getRadius() - (peg.positionX + peg.getRadius());
        float dy = ball.positionY + ball.getRadius() - (peg.positionY + peg.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float overlap = (ball.getRadius() + peg.getRadius()) - distance;
        if (overlap > 0) {
            dx /= distance;
            dy /= distance;
            // Adjust ball's position to resolve overlap
            ball.positionX += dx * overlap;
            ball.positionY += dy * overlap;
            // Bounce by reversing the Y velocity and adding a random component for variation
            ball.velocityY = -ball.velocityY * PEG_BOUNCE_FACTOR;
            // Apply a slight random variation to the X velocity
            ball.velocityX += (Math.random() - 0.5) * 2 * PEG_BOUNCE_FACTOR;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw");
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.white));
        if(balls.isEmpty() == false) {
            for (BallCollectible ball : balls) {
                if (ball.isSpawnDelayFinished())
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