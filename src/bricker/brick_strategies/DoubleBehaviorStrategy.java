package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Bricks;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import java.util.Random;


public class DoubleBehaviorStrategy implements CollisionStrategy{
    private final WindowController windowController;
    private final CollisionStrategy collisionStrategy1;
    private final CollisionStrategy collisionStrategy2;
    private Bricks[] bricksTypes = {Bricks.ADD_PADDLE,Bricks.ADD_LIFE,
            Bricks.ADD_PUCKS,Bricks.TURBO,Bricks.DOUBLE_BEHAVIOUR};

    public DoubleBehaviorStrategy(ImageReader imageReader, BrickerGameManager brickerGameManager,
                                  SoundReader soundReader, Vector2 brickCoords,
                                  UserInputListener userInputListener,WindowController windowController,
                                  boolean isRandomDoubleBehaviour) {
        this.windowController = windowController;
        BricksStrategiesFactory brickFactory = new BricksStrategiesFactory();
        int idx;
        int idx1;
        Random random = new Random();
        if (isRandomDoubleBehaviour) {
            idx = random.nextInt(bricksTypes.length);
            if (bricksTypes[idx] == Bricks.DOUBLE_BEHAVIOUR) {
                idx1 = random.nextInt(bricksTypes.length-1);
            } else {
                idx1 = random.nextInt(bricksTypes.length);
            }
        }

        else {
            idx = random.nextInt(bricksTypes.length-1);
            idx1 = random.nextInt(bricksTypes.length-1);
        }

        this.collisionStrategy1 = brickFactory.buildCollisionStrategy(brickerGameManager,bricksTypes[idx],
                imageReader, soundReader, brickCoords, userInputListener,false,
                windowController);
        this.collisionStrategy2 = brickFactory.buildCollisionStrategy(brickerGameManager,bricksTypes[idx1],
                imageReader, soundReader, brickCoords, userInputListener,false,
                windowController);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        this.collisionStrategy1.onCollision(obj1, obj2);
        this.collisionStrategy2.onCollision(obj1, obj2);
    }
}
