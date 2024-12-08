package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball  extends GameObject {

    private final Sound collisionSound;
    private int collisionCounter;
    private boolean turboState;
    private int coulisionCountTOCancelTurbo;
    private ImageReader imageReader;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCounter = 0;
        turboState = false;
        this.setTag(Constants.BALL_TAG);
    }


    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionCounter++;
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        if(collisionCounter == coulisionCountTOCancelTurbo){
            exitTurboState(imageReader);
        }
    }

    public void exitTurboState(ImageReader imageReader){
        turboState = false;
        Renderable redBallImage = imageReader.readImage(Constants.ASSETS_BALL_PNG,
                true);
        this.renderer().setRenderable(redBallImage);
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;

        this.setVelocity(new Vector2(ballVelX, ballVelY));

    }

    public void enterTurboState(ImageReader imageReader){
        this.imageReader = imageReader;
        if (turboState){
            return;
        }
        turboState = true;
        coulisionCountTOCancelTurbo = collisionCounter + Constants.MAX_TURBO_COLLISIONS;
        Renderable redBallImage = imageReader.readImage(Constants.ASSETS_RED_BALL_PNG,
                true);
        this.renderer().setRenderable(redBallImage);
        float ballVelX = Constants.TURBO_SPEED_FACTOR * Constants.BALL_SPEED;
        float ballVelY = Constants.TURBO_SPEED_FACTOR * Constants.BALL_SPEED;
        this.setVelocity(new Vector2(ballVelX, ballVelY));

    }
    public int getCollisionCounter(){
        return collisionCounter;
    }

}
