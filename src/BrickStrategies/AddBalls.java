package BrickStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;

public class AddBalls implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    public AddBalls(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }
    public void onCollision(GameObject obj1, GameObject obj2) {
        brickerGameManager.removeObject(obj1);
        Renderable puckball;

    }

}
