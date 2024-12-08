package BrickStrategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameObjects.Paddle;

public class AddPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final UserInputListener userInputListener;
    private GameObject additionalPaddle;
    private final WindowController windowController;

    public AddPaddleStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader,
                             UserInputListener inputListener, WindowController windowController) {
        super(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.userInputListener = inputListener;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        int halfScreen = 2;

        if (brickerGameManager.getPaddleCount() < Constants.MAXIMUM_PADDLE_COUNT) {
            Renderable paddleImage = this.imageReader.readImage(Constants.ASSETS_PADDLE_PNG,
                    true);
            additionalPaddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                    Constants.PADDLE_LENGTH), paddleImage, userInputListener, false,
                    this.brickerGameManager, windowController);
            additionalPaddle.setCenter(new Vector2(brickerGameManager.getWinDimentions().x() / halfScreen,
                    brickerGameManager.getWinDimentions().y() / halfScreen));
            brickerGameManager.addGameObject(additionalPaddle);
            this.brickerGameManager.increasePaddleCount();


        }
    }
}