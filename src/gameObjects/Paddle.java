package gameObjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
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
    private final UserInputListener inputListener;
    private int collisionCount;
    private final boolean isMainPaddle;
    private final BrickerGameManager brickerGameManager;
    private final WindowController windowController;


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
            inputListener, boolean isMainPaddle, BrickerGameManager brickerGameManager, WindowController
            windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.isMainPaddle = isMainPaddle;
        this.brickerGameManager = brickerGameManager;
        this.windowController = windowController;
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
        float winLeftEdge = 0;

        Vector2 moveDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && getTopLeftCorner().x() > winLeftEdge) {
            moveDir = moveDir.add(Vector2.LEFT.mult(Constants.PADDLE_MOVEMENT_SPEED));
        } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && ( getTopLeftCorner().x() +
                Constants.PADDLE_WIDTH) < windowController.getWindowDimensions().x()) {
            moveDir = moveDir.add(Vector2.RIGHT.mult(Constants.PADDLE_MOVEMENT_SPEED));
        }
        setVelocity(moveDir);

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
                brickerGameManager.addLife((Heart) other);
            }
            this.brickerGameManager.removeObject(other);
        }

    }

}
