package gameObjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * the User's Paddle object class, which extends(inherits) the GameObject class from DanoameLab.
 * this class exends the GameObj class, and adds to it a movement speed constannt, and an
 * inoutlistner, so that game object could interact with the user.
 */
public class Paddle extends GameObject {
//    constants
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private int collisionCount;
    private final boolean isMainPaddle;
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, boolean isMainPaddle, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.isMainPaddle = isMainPaddle;
        this.brickerGameManager = brickerGameManager;
        collisionCount = 0;
        this.setTag(Constants.PADDLE_TAG);
    }


    /**
     * this methods overrides the update method from the father class.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add((Vector2.LEFT));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add((Vector2.RIGHT));
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(Constants.BALL_TAG)){
            if (++this.collisionCount == Constants.MAX_PADDLE_COLLISION_COUNT && !isMainPaddle) {
                this.brickerGameManager.removeObject(this);
                this.brickerGameManager.removePaddle();
            }
        }
        if (other.getTag().equals(Constants.HEART_TAG) && isMainPaddle) {
            if (brickerGameManager.getLives() < Constants.MAX_NUM_OF_HEARTS) {
                brickerGameManager.addLife();
            }
            this.brickerGameManager.removeObject(other);
        }

    }

}
