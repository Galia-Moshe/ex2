package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameManager;

import java.util.Random;

public class AddPucksStrategy extends BasicCollisionStrategy implements CollisionStrategy{

    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Vector2 brickCoords;

    public AddPucksStrategy(BrickerGameManager brickerGameManager, ImageReader
            imageReader, SoundReader soundReader, Vector2 brickCoords) {
        super(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.brickCoords = brickCoords;
    }

    public void onCollision(GameObject obj1, GameObject obj2) {
        brickerGameManager.removeObject(obj1);
        Puck puckBall1 = createPuckBall();
        Puck puckBall2 = createPuckBall();
        this.brickerGameManager.addGameObject(puckBall1);
        this.brickerGameManager.addGameObject(puckBall2);

    }

    private Puck createPuckBall(){
        Renderable puckBallImage =  imageReader.readImage(Constants.ASSETS_PUCK_PNG,
                true);
        Sound collisionSound = soundReader.readSound(Constants.ASSETS_BLOP_WAV);
        Puck packBall = new Puck(brickCoords, new Vector2(Constants.PUCKS_PRESENTS_SIZE *
                Constants.BALL_WIDTH,
                Constants.PUCKS_PRESENTS_SIZE* Constants.BALL_LENGTH), puckBallImage, collisionSound);

        Random puckRand  = new Random();
        double angle = puckRand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * Constants.BALL_SPEED;

        packBall.setVelocity(new Vector2(velocityX, velocityY));
        return packBall;

    }

}


