package bricker.main;

import danogl.util.Vector2;

import java.awt.*;


public class Constants {
    public static final float BALL_SPEED = 150;
    public static final Color BORDER_COLOR = Color.BLUE;
    public static final int NUM_OF_HEARTS = 3;
    public static final int MAX_NUM_OF_HEARTS = 4;
    public static final int HEART_SIZE = 15;
    public static final String BALL_TAG = "Ball";
    public static final String HEART_TAG = "Heart";
    public static final String ASSETS_BRICK_PNG = "assets/brick.png";
    public static final String ASSETS_HEART_PNG = "assets/heart.png";
    public static final String ASSETS_PADDLE_PNG = "assets/paddle.png";
    public static final String ASSETS_DARK_BG_2_SMALL_JPEG = "assets/DARK_BG2_small.jpeg";
    public static final String ASSETS_BALL_PNG = "assets/ball.png";
    public static final String ASSETS_BLOP_WAV = "assets/blop.wav";
    public static final String ASSETS_PUCK_PNG = "assets/mockBall.png";
    public static final String ASSETS_RED_BALL_PNG = "assets/redball.png";
    public static final String YOU_WIN = "you win!";
    public static final String YOU_LOSE = "you lose!";
    public static final String PLAY_AGAIN = "Play again?";
    public static final String BOUNCING_BALL = "Bouncing Ball";
    public static final Vector2 WINDOW_SIZE = Vector2.of(700, 500);
    public static final int BRIX_IDX = 0;
    public static final int ROWS_IDX = 1;
    public static final int DEFAULT_BRICKS_IN_ROW = 8;
    public static final int DEFAULT_BRICKS_ROW = 7;
    public static final int ARGS = 2;
    public static final int DISTANCE_BETWEEN_BRICKS = 4;
    public static final int BRICK_HEIGHT = 10;
    public static final String BRICK_TAG = "BRICK TAG";
    public static final String PADDLE_TAG = "Paddle";
    public static final int BIAS_FOR_BRICKS = 20;
    public static final int BIAS_FOR_SIDES = 5;
    public static final int BIAS_FOR_BRICKS_HEIGHT = 5;
    public static final int BIAS_FOR_BRICK_WIDTH = 10;
    public static final int BIAS_FOR_WALLS = 5;
    public static final float BALL_WIDTH = 20;
    public static final float BALL_LENGTH = 20;
    public static final float PADDLE_WIDTH = 200;
    public static final float PADDLE_LENGTH = 20;
    public static final float PUCKS_PRESENTS_SIZE = 0.75F;
    public static final float TURBO_SPEED_FACTOR = 1.4F;
    public static final String PUCK_TAG = "Puck";
    public static final int MAX_TURBO_COLLISIONS = 6;

    public static final int ADD_BALLS_PROBABILITY = 0;
    public static final int ADD_PADDLE_PROBABILITY = 1;
    public static final int TURBO_STATE_PROBABILITY = 2;
    public static final int ADD_HEART_PROBABILITY = 3;
    public static final int DOUBLE_BEHAVE_PROBABILITY = 4;

    public static final int MAX_PADDLE_COLLISION_COUNT = 4;
    public static final int MAXIMUM_PADDLE_COUNT = 2;

    public static final float HEART_SPEED = 100;
}
