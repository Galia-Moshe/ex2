package bricker.main;

/**
 * This enum is used to differentiate between bricks.
 */
public enum Bricks {
    /** a brick with basic collision strategy */
    BASIC,
    /** a brick that drops a Life object when hit */

    ADD_LIFE,
    /** a brick that creates 2 Pucks objects when hit */

    ADD_PUCKS,
    /** a brick that cretes a Paddle when hit */

    ADD_PADDLE,
    /** a brick that change the color and velocity of the ball when hit */
    TURBO,

    /** a brick that generates 2 other strategies when hit */

    DOUBLE_BEHAVIOUR
}
