package BrickStrategies;

import danogl.GameObject;

public interface CollisionStrategy {
    public void onCollision(GameObject obj1, GameObject obj2);
}
