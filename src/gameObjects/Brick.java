package gameObjects;

import BrickStrategies.CollisionStrategy;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * Brick class which extends(inherits) the gameObject class from DanoGameLab.
 * this class uses the init class function to construct the Brick obj, on whch holds in addition
 * to the GameObject class a CollisionStrategy variable.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private boolean turboMode;
    private int collisionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.collisionCounter = collisionCounter = 0;
        this.turboMode = false;
        this.setTag(Constants.BRICK_TAG);
    }

    /**
     * this method overrides the onCollisionEnter method from the DanoGameLab library's
     * GameObject class.
     * this method, upon a collision, uses its father-method, and further it by using its
     * CollisionStrategy variable.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
        if(collisionCounter == Constants.MAX_TURBO_COLLISIONS){

        }

    }
}
