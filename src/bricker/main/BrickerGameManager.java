package bricker.main;
import BrickStrategies.AddPucksStrategy;
import BrickStrategies.BasicCollisionStrategy;
import BrickStrategies.BricksStrategiesFactory;
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
import gameObjects.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;


/**
 * the main class for the game runner, the game manager.
 * intis all the game components(sub classes), and runs the game using them.
 */
public class BrickerGameManager extends GameManager {
    //consants

    //field vars
    private int numOfBricks;
    private int numOfPaddles = 1;
    private Vector2 WindowDimensions;
    private WindowController windowController;
    private Ball ball;
    private int lifeLeft = Constants.NUM_OF_HEARTS;
    private Random rand;
    private ArrayList<Heart> heartObjects;
    private GameObject livesTextObject;
    private TextRenderable livesTextRenderable;
    private int BricksInRowNum;
    private int BricksRowNum;


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
        createBricks(imageReader, soundReader, windowController, inputListener);
        //creates win\lose objs s.t. hearts.
        createHearts(imageReader);
        createNumericLivesDisplay();

    }

    /**
     *
     * @return the paddle count
     */
    public int getPaddleCount() {
        return this.numOfPaddles;
    }

    /**
     * Increases the count of paddles in the game.
     */
    public void increasePaddleCount() {
        this.numOfPaddles++;
    }

    /**
     * Removes a paddle from the game.
     */
    public void removePaddle() {
        this.numOfPaddles--;
    }


    private void createBricks(ImageReader imageReader, SoundReader soundReader, WindowController
            windowController, UserInputListener inputListener) {
        BricksStrategiesFactory brickFactory = new BricksStrategiesFactory();
        Renderable brickImage = imageReader.readImage(Constants.ASSETS_BRICK_PNG, true);
        numOfBricks = BricksRowNum * BricksInRowNum;
        float brickWidth =
                ((Constants.WINDOW_SIZE.x() - Constants.BIAS_FOR_BRICKS)  - ((BricksInRowNum + 1) * Constants.BIAS_FOR_SIDES))
                        / BricksInRowNum;
        Vector2 brickDimensions = Vector2.of(brickWidth, Constants.BRICK_HEIGHT);
        int brickHeigthFactor = Constants.BIAS_FOR_BRICKS_HEIGHT;
        for (int row = 0; row < BricksRowNum; row++){
            float brickWidthFactor =Constants.BIAS_FOR_BRICK_WIDTH;
            for (int col = 0; col < BricksInRowNum; col++){
                Vector2 brickCoords = Vector2.of(brickWidthFactor, brickHeigthFactor);
                CollisionStrategy collisionStrategy = brickFactory.buildCollisionStrategy(
                        this, imageReader, soundReader, windowController, brickCoords, inputListener);
                GameObject brick = new Brick(brickCoords, brickDimensions, brickImage, collisionStrategy);
                gameObjects().addGameObject(brick);
                brick.setTag(Constants.BRICK_TAG);
                brickWidthFactor += brickWidth + Constants.BIAS_FOR_SIDES;
            }
            brickHeigthFactor += Constants.BRICK_HEIGHT + Constants.BIAS_FOR_BRICKS_HEIGHT;
        }
    }


    public void removeObject(GameObject object){
        boolean objectRemoved =
                gameObjects().removeGameObject(object) ||
                        gameObjects().removeGameObject(object, Layer.BACKGROUND) ||
        gameObjects().removeGameObject(object, Layer.STATIC_OBJECTS) ||
                        gameObjects().removeGameObject(object, Layer.UI);
        if (object.getTag().equals(Constants.BRICK_TAG) && objectRemoved == true)
        {
            numOfBricks -= 1;
        }
    }


    private void createNumericLivesDisplay() {
        livesTextRenderable = new TextRenderable(Integer.toString(lifeLeft));
        livesTextRenderable.setColor(Color.GREEN);
        Vector2 textPosition = new Vector2(10, 40);
        livesTextObject = new GameObject(textPosition, new Vector2(20,20), livesTextRenderable);
        gameObjects().addGameObject(livesTextObject, Layer.UI);
    }



    private void createHearts(ImageReader imageReader) {
        Renderable heartRender = imageReader.readImage(Constants.ASSETS_HEART_PNG, true);
        heartObjects = new ArrayList<>();
        int spacing = 15; // Space between hearts
        for (int i = 0; i < Constants.NUM_OF_HEARTS; i++) {
            Vector2 heartPosition = new Vector2(spacing * i + 10, 10);
            Vector2 heartSize = new Vector2(Constants.HEART_SIZE , Constants.HEART_SIZE);
            Heart heart = new Heart(heartPosition, heartSize, heartRender);
            heartObjects.add(heart);
            gameObjects().addGameObject(heart, Layer.UI);
        }
    }

    private void updateHearts(int livesLeft) {

        while (heartObjects.size() > livesLeft) {
            Heart heart = heartObjects.remove(heartObjects.size() - 1);
            gameObjects().removeGameObject(heart, Layer.UI);
        }
    }



    private void createPaddles(ImageReader imageReader, UserInputListener inputListener, Ball ball) {
        Renderable paddleImage = imageReader.readImage(Constants.ASSETS_PADDLE_PNG, true);

        //create user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH), paddleImage,
                inputListener, true, this);
        userPaddle.setCenter(new Vector2(WindowDimensions.x()/2, WindowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);


        //create AI paddle
//        GameObject AiPaddle = new AIPaddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, ball);
//        AiPaddle.setCenter(new Vector2(WindowDimensions.x()/2, 30));
//        gameObjects().addGameObject(AiPaddle);
    }

    private void createBackGround(ImageReader imageReader) {
        Renderable backgroundRender = imageReader.readImage(Constants.ASSETS_DARK_BG_2_SMALL_JPEG, true);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(WindowDimensions.x(),
                WindowDimensions.y()),  backgroundRender);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createWalls() {
        Renderable wallRender = new RectangleRenderable(Constants.BORDER_COLOR);
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.BIAS_FOR_WALLS,
                WindowDimensions.y()),
                wallRender);
        GameObject rightWall = new GameObject(new Vector2(WindowDimensions.x() - Constants.BIAS_FOR_WALLS, 0),
                new Vector2(Constants.BIAS_FOR_WALLS,
                WindowDimensions.y()), wallRender);
        GameObject upperWall = new GameObject(Vector2.ZERO, new Vector2(WindowDimensions.x(), Constants.BIAS_FOR_WALLS),
                wallRender);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(upperWall);
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader,
                         WindowController windowController) {
        Renderable ballImage =  imageReader.readImage(Constants.ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(Constants.ASSETS_BLOP_WAV);
        ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_WIDTH,Constants.BALL_LENGTH), ballImage, collisionSound);
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
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
     * add a game object to the game
     * @param obj the object to add
     */
    public void addGameObject(GameObject obj) {
        this.gameObjects().addGameObject(obj);
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
        livesTextRenderable.setString(Integer.toString(lifeLeft));
        float ballHeight = ball.getCenter().y();
        switch (lifeLeft)
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
            prompt = Constants.YOU_WIN;
            finishGame(prompt);
        }
        else if(lifeLeft == 0)
        {
            prompt = Constants.YOU_LOSE;
            finishGame(prompt);
        }
    }

    private void finishGame(String prompt) {
        prompt += Constants.PLAY_AGAIN;
        if (windowController.openYesNoDialog(prompt))
        {
            windowController.resetGame();
            lifeLeft = Constants.NUM_OF_HEARTS;
        }
        else
        {
            windowController.closeWindow();
        }
    }

    private void updateGameStatus() {
        lifeLeft--;
        updateHearts(lifeLeft);
        ball.setCenter(WindowDimensions.mult(0.5F));
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
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

    /**
     * Retrieves the number og life that lefts.
     * @return the number og life that lefts.
     */
    public int getLives(){
        return lifeLeft;
    }

    /**
     * Adds a life to the player.
     */
    public void addLife() {
        this.lifeLeft++;
    }

    /**
     * Retrieves the dimensions of the game window.
     * @return the win dimentions
     */
    public Vector2 getWinDimentions() {
        return this.WindowDimensions;
    }

    public static void main(String[] args) {
        ArgsFactory result = getArgsFactory(args);
        new BrickerGameManager(Constants.BOUNCING_BALL, new Vector2(700, 500), result.BricksInRowNum(), result.BricksRowNum()).run();
    }

    private static ArgsFactory getArgsFactory(String[] args) {
        int BricksRowNum;
        int BricksInRowNum;
        if (args.length == Constants.ARGS)
        {
            BricksInRowNum = Integer.parseInt(args[Constants.BRIX_IDX]);
            BricksRowNum = Integer.parseInt(args[Constants.ROWS_IDX]);
        }
        else
        {
            BricksInRowNum = Constants.DEFAULT_BRICKS_IN_ROW;
            BricksRowNum = Constants.DEFAULT_BRICKS_ROW;
        }
        ArgsFactory result = new ArgsFactory(BricksInRowNum, BricksRowNum);
        return result;
    }

    private record ArgsFactory(int BricksInRowNum, int BricksRowNum) {
    }
}
