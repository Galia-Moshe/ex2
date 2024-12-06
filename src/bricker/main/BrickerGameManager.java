package bricker.main;
import BrickStrategies.BasicCollisionStrategy;
import BrickStrategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import gameobjects.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;


/**
 * the main class for the game runner, the game manager.
 * intis all the game components(sub classes), and runs the game using them.
 */
public class BrickerGameManager extends GameManager {
    //consants
    private static final float BALL_SPEED =50 ;
    private static final Color BORDER_COLOR = Color.BLUE ;
    private static final int GAMES = 3;
    private static final int HEARTS = 3;
    public static final String ASSETS_BRICK_PNG = "assets/brick.png";
    public static final String ASSETS_HEART_PNG = "assets/heart.png";
    public static final String ASSETS_PADDLE_PNG = "assets/paddle.png";
    public static final String ASSETS_DARK_BG_2_SMALL_JPEG = "assets/DARK_BG2_small.jpeg";
    public static final String ASSETS_BALL_PNG = "assets/ball.png";
    public static final String ASSETS_BLOP_WAV = "assets/blop.wav";
    public static final String ASSETS_PUCK_PNG = "assets/mockBall.png";
    public static final String YOU_WIN = "you win!";
    public static final String YOU_LOSE = "you lose!";
    public static final String PLAY_AGAIN = "Play again?";
    public static final String BOUNCING_BALL = "Bouncing Ball";
    private static final Vector2 WINDOW_SIZE = Vector2.of(700,500);
    private static final int BRIX_IDX =  0;
    private static final int ROWS_IDX = 1;
    private static final int DEFAULT_BRICKS_IN_ROW = 8;
    private static final int DEFAULT_BRICKS_ROW = 7;
    private static final int ARGS = 2;
    private static final int DISTANCE_BETWEEN_BRICKS = 4;
    private static final int BRICK_HEIGHT = 10;
    private static final String BRICK_TAG = "BRICK TAG";
    private static final int BIAS_FOR_BRICKS = 20;
    private static final int BIAS_FOR_SIDES = 5;
    private static final int BIAS_FOR_BRICKS_HEIGHT = 5;
    private static final int BIAS_FOR_BRICK_WIDTH = 10;
    private static final int BIAS_FOR_WALLS = 5;
    private static final float BALL_WIDTH = 20;
    private static final float BALL_LENGTH = 20;
    private static final float PUCKS_PRESENTS_SIZE = 0.75F;



    //field vars
    private int numOfBricks;
    private Vector2 WindowDimensions;
    private WindowController windowController;
    private Ball ball;
    private int gamesLeft = GAMES;
    private Random rand;
    private ArrayList<GameObject> heartObjects;
    private GameObject livesTextObject;
    private TextRenderable livesTextRenderable;
    private int BricksInRowNum;
    private int BricksRowNum;
    private Puck puckBall;


    /**
     * constructor for the game manager.
     * inherits the gamemanager class from the third side library and uses its function (super)
     * to init and run the game.
     * @param bouncingBall the name of the game\window
     * @param vector2 the dinmensions for the game window.
     */
    public BrickerGameManager(String bouncingBall, Vector2 vector2, int BricksInRowNum,
                              int BricksRowNum) {
        super(bouncingBall, vector2);
        this.BricksRowNum = BricksRowNum;
        this.BricksInRowNum = BricksInRowNum;
    }

    /**
     * inits the game create each component sub class) and starts the game.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        //create ball
        createBall(imageReader, soundReader, windowController);
        //create walls
        createWalls();
        //create beackground
        createBackGround(imageReader);
        //create paddles
        createPaddles(imageReader, inputListener, ball);
        // creates bricks
        createBricks(imageReader);
        //creates win\lose objs s.t. hearts.
        createHearts(imageReader);
        createNumericLivesDisplay();
//        createPucks(imageReader, soundReader, windowController);


    }

    private void createPucks(ImageReader imageReader,SoundReader soundReader,
                             WindowController windowController){
        Renderable puckBallImage =  imageReader.readImage(ASSETS_PUCK_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);
        puckBall = new Puck(Vector2.ZERO, new Vector2(PUCKS_PRESENTS_SIZE * BALL_WIDTH,
                PUCKS_PRESENTS_SIZE* BALL_LENGTH), puckBallImage, collisionSound);

        Random puckRand  = new Random();
        double angle = puckRand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;

        puckBall.setVelocity(new Vector2(velocityX, velocityY));
//        gameObjects().addGameObject(puckBall);

    }

    private void createBricks(ImageReader imageReader) {
        Renderable brickImage = imageReader.readImage(ASSETS_BRICK_PNG, true);
        numOfBricks = BricksRowNum * BricksInRowNum;
        float brickWidth =
                ((WINDOW_SIZE.x() - BIAS_FOR_BRICKS)  - ((BricksInRowNum + 1) * BIAS_FOR_SIDES))
                        / BricksInRowNum;
        Vector2 brickDimensions = Vector2.of(brickWidth, BRICK_HEIGHT);
        int brickHeigthFactor = BIAS_FOR_BRICKS_HEIGHT;
        for (int row = 0; row < BricksRowNum; row++){
            float brickWidthFactor =BIAS_FOR_BRICK_WIDTH;
            for (int col = 0; col < BricksInRowNum; col++){
                Vector2 brickCoords = Vector2.of(brickWidthFactor, brickHeigthFactor);
                CollisionStrategy collisionStrategy = new BasicCollisionStrategy(this);
                GameObject brick = new Brick(brickCoords, brickDimensions, brickImage, collisionStrategy);
                gameObjects().addGameObject(brick);
                brick.setTag(BRICK_TAG);
                brickWidthFactor += brickWidth + BIAS_FOR_SIDES;
            }
            brickHeigthFactor += BRICK_HEIGHT + BIAS_FOR_BRICKS_HEIGHT;
        }
    }


    public void removeObject(GameObject object){
        boolean objectRemoved =
                gameObjects().removeGameObject(object) ||
                        gameObjects().removeGameObject(object, Layer.BACKGROUND) ||
        gameObjects().removeGameObject(object, Layer.STATIC_OBJECTS) ||
                        gameObjects().removeGameObject(object, Layer.UI);
        if (object.getTag().equals(BRICK_TAG) && objectRemoved == true)
        {
            numOfBricks -= 1;
        }
    }


    private void createNumericLivesDisplay() {
        livesTextRenderable = new TextRenderable(Integer.toString(gamesLeft));
        livesTextRenderable.setColor(Color.GREEN);
        Vector2 textPosition = new Vector2(10, 40);
        livesTextObject = new GameObject(textPosition, new Vector2(20,20), livesTextRenderable);
        gameObjects().addGameObject(livesTextObject, Layer.UI);
    }



    private void createHearts(ImageReader imageReader) {
        Renderable heartRender = imageReader.readImage(ASSETS_HEART_PNG, true);
        heartObjects = new ArrayList<>();
        int spacing = 15; // Space between hearts
        for (int i = 0; i < HEARTS; i++) {
            Vector2 heartPosition = new Vector2(spacing * i + 10, 10);
            Vector2 heartSize = new Vector2(15 , 15);
            GameObject heart = new GameObject(heartPosition, heartSize, heartRender);
            heartObjects.add(heart);
            gameObjects().addGameObject(heart, Layer.UI);
        }
    }

    private void updateHearts(int livesLeft) {

        while (heartObjects.size() > livesLeft) {
            GameObject heart = heartObjects.remove(heartObjects.size() - 1);
            gameObjects().removeGameObject(heart, Layer.UI);
        }
    }






    private void createPaddles(ImageReader imageReader, UserInputListener inputListener, Ball ball) {
        Renderable paddleImage = imageReader.readImage(ASSETS_PADDLE_PNG, true);

        //create user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage,
                inputListener);
        userPaddle.setCenter(new Vector2(WindowDimensions.x()/2, WindowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);


        //create AI paddle
//        GameObject AiPaddle = new AIPaddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, ball);
//        AiPaddle.setCenter(new Vector2(WindowDimensions.x()/2, 30));
//        gameObjects().addGameObject(AiPaddle);
    }

    private void createBackGround(ImageReader imageReader) {
        Renderable backgroundRender = imageReader.readImage(ASSETS_DARK_BG_2_SMALL_JPEG, true);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(WindowDimensions.x(),
                WindowDimensions.y()),  backgroundRender);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createWalls() {
        Renderable wallRender = new RectangleRenderable(BORDER_COLOR);
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(BIAS_FOR_WALLS,
                WindowDimensions.y()),
                wallRender);
        GameObject rightWall = new GameObject(new Vector2(WindowDimensions.x() - BIAS_FOR_WALLS, 0),
                new Vector2(BIAS_FOR_WALLS,
                WindowDimensions.y()), wallRender);
        GameObject upperWall = new GameObject(Vector2.ZERO, new Vector2(WindowDimensions.x(), BIAS_FOR_WALLS),
                wallRender);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(upperWall);
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader,
                         WindowController windowController) {
        Renderable ballImage =  imageReader.readImage(ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_WIDTH,BALL_LENGTH), ballImage, collisionSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        rand  = new Random();
        if (rand.nextBoolean())
        {
            ballVelX *= -1;

        }
        if (rand.nextBoolean())
        {
            ballVelY *= -1;

        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        WindowDimensions = windowController.getWindowDimensions();
        ball.setCenter(WindowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);
    }

    /**
     * update the game each frame.Also, responsible for winning\losing checking and validation.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        CheckForGameEnd();
    }

    private void CheckForGameEnd() {
        livesTextRenderable.setString(Integer.toString(gamesLeft));
        float ballHeight = ball.getCenter().y();
        switch (gamesLeft)
        {
            case 2:
                livesTextRenderable.setColor(Color.YELLOW);
                break;
            case 1:
                livesTextRenderable.setColor(Color.RED);
                break;
            case 0:
                livesTextRenderable.setColor(Color.RED);
                break;
            default:
                livesTextRenderable.setColor(Color.GREEN);
                break;
        }
        String prompt = "";
        if (ballHeight > WindowDimensions.y())
        {
            updateGameStatus();
        }
        if (numOfBricks == 0)
        {
            prompt = YOU_WIN;
            finishGame(prompt);
        }
        else if(gamesLeft == 0)
        {
            prompt = YOU_LOSE;
            finishGame(prompt);
        }
    }

    private void finishGame(String prompt) {
        prompt += PLAY_AGAIN;
        if (windowController.openYesNoDialog(prompt))
        {
            windowController.resetGame();
            gamesLeft = GAMES;
        }
        else
        {
            windowController.closeWindow();
        }
    }

    private void updateGameStatus() {
        gamesLeft--;
        updateHearts(gamesLeft);
        ball.setCenter(WindowDimensions.mult(0.5F));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        if (rand.nextBoolean())
        {
            ballVelX *= -1;

        }
        if (rand.nextBoolean())
        {
            ballVelY *= -1;

        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    public static void main(String[] args) {
        ArgsFactory result = getArgsFactory(args);
        new BrickerGameManager(BOUNCING_BALL, new Vector2(700, 500), result.BricksInRowNum(), result.BricksRowNum()).run();
    }

    private static ArgsFactory getArgsFactory(String[] args) {
        int BricksRowNum;
        int BricksInRowNum;
        if (args.length == ARGS)
        {
            BricksInRowNum = Integer.parseInt(args[BRIX_IDX]);
            BricksRowNum = Integer.parseInt(args[ROWS_IDX]);
        }
        else
        {
            BricksInRowNum = DEFAULT_BRICKS_IN_ROW;
            BricksRowNum = DEFAULT_BRICKS_ROW;
        }
        ArgsFactory result = new ArgsFactory(BricksInRowNum, BricksRowNum);
        return result;
    }

    private record ArgsFactory(int BricksInRowNum, int BricksRowNum) {
    }
}
