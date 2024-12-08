package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Bricks;
import bricker.main.Constants;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

import java.util.Random;

public class BricksStrategiesFactory {

    public BricksStrategiesFactory(){
        // Constructor intentionally empty
    }

    public CollisionStrategy buildCollisionStrategy(BrickerGameManager brickerGameManager, Bricks brick,
                                                    ImageReader imageReader, SoundReader soundReader,
                                                    Vector2 brickCoords, UserInputListener inputListener,
                                                    boolean isRandomDoubleBehaviour,
                                                    WindowController windowController){
        CollisionStrategy collisionStrategy = null;

        switch(brick){
            case ADD_PUCKS:
                collisionStrategy = new AddPucksStrategy(brickerGameManager,imageReader, soundReader,
                        brickCoords);
                break;
            case ADD_PADDLE:
                collisionStrategy = new AddPaddleStrategy(brickerGameManager, imageReader, inputListener,
                        windowController);
                break;
            case TURBO:
                collisionStrategy = new TurboStateStrategy(brickerGameManager, imageReader);
                break;
            case ADD_LIFE:
                collisionStrategy = new AddLifeStrategy(brickerGameManager, imageReader, brickCoords);
                break;
            case DOUBLE_BEHAVIOUR:
                collisionStrategy = new DoubleBehaviorStrategy(imageReader,brickerGameManager, soundReader,
                        brickCoords, inputListener, windowController, isRandomDoubleBehaviour);
                break;
            default:
                collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        }
        return collisionStrategy;
    }

}
