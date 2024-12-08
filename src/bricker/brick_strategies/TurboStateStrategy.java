package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

import java.util.Random;

public class TurboStateStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private Ball mainBall;

    public TurboStateStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader) {
        super(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
    }


    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if(obj2.getTag().equals(Constants.BALL_TAG)){
            mainBall = (Ball) obj2;
            mainBall.enterTurboState(imageReader);

        }
    }
}


