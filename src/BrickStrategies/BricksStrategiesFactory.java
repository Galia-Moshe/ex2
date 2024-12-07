package BrickStrategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameObjects.Ball;

import java.util.Random;

public class BricksStrategiesFactory {

    public BricksStrategiesFactory(){
        // Constructor intentionally empty
    }

    public CollisionStrategy buildCollisionStrategy(BrickerGameManager brickerGameManager,ImageReader
            imageReader, SoundReader soundReader, WindowController windowController, Vector2 brickCoords,
                                                    UserInputListener inputListener){
        CollisionStrategy collisionStrategy = null;
        Random random = new Random();
        int probability = random.nextInt(10);
        switch(probability){
            case Constants.ADD_BALLS_PROBABILITY:
                collisionStrategy = new AddPucksStrategy(brickerGameManager,imageReader, soundReader,
                        brickCoords);
                break;
            case Constants.ADD_PADDLE_PROBABILITY:
                collisionStrategy = new AddPaddleStrategy(brickerGameManager, imageReader, inputListener);
                break;
            case Constants.TURBO_STATE_PROBABILITY:
                collisionStrategy = new TurboStateStrategy(brickerGameManager, imageReader);
                break;
            case Constants.ADD_HEART_PROBABILITY:
                collisionStrategy = new AddLifeStrategy(brickerGameManager, imageReader, brickCoords);
                break;
//            case Constants.DOUBLE_BEHAVE_PROBABILITY:
//                collisionStrategy = new DoubleBehaviorStrategy(brickerGameManager);
//                break;
            default:
                collisionStrategy = new AddLifeStrategy(brickerGameManager, imageReader, brickCoords);
        }
        return collisionStrategy;
    }

}
