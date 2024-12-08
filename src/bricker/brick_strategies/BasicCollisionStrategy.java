package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;


/**
 * calss impelemataion for the basic strategy implementation.
 * declares upon a collision, and evades the current object (which is the brick so far)
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }



    @Override
    /**
     * the method which evdaes the object, and causes it to disappear upon a collision.
     */
    public void onCollision(GameObject obj1, GameObject obj2) {
        System.out.println("collision with brick detected");
        brickerGameManager.removeObject(obj1);
    }
}
