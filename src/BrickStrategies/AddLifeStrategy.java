package BrickStrategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameObjects.Heart;

public class AddLifeStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final Vector2 brickCoords;

    public AddLifeStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader,
                           Vector2 brickCoords){
        super(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.brickCoords = brickCoords;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1,obj2);
        Renderable heartImage = this.imageReader.readImage(Constants.ASSETS_HEART_PNG,
                true);
        Heart newHeart = new Heart(brickCoords, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE),
                heartImage, brickerGameManager);
        newHeart.setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));
        this.brickerGameManager.addGameObject(newHeart);

    }
}
