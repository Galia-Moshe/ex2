package BrickStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class DoubleBehaviorStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    public DoubleBehaviorStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {

    }
}
