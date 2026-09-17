package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Driver preferences and control bindings. Unlike {@link ConfigInfo} nothing here depends on the
 * robot, so the defaults are deliberately neutral: no expo, no rate limiting, no turn scaling.
 */
public final class GamepadSettings {
    private GamepadSettings() {}

    @FunctionalInterface
    public interface Axis {
        double read(Gamepad gamepad);
    }

    @FunctionalInterface
    public interface Button {
        boolean read(Gamepad gamepad);
    }

    public static final class Shaping {
        public static final double STICK_DEADZONE = 0.05;

        /** 0.0 is fully linear, 1.0 is fully cubic. */
        public static final double DRIVE_EXPO = 0.0;
        public static final double TURN_EXPO = 0.0;

        /** Max change in commanded power per second; infinity disables rate limiting. */
        public static final double SLEW_RATE = Double.POSITIVE_INFINITY;

        private Shaping() {}
    }

    public static final class Speed {
        public static final double BASE = 1.0;
        public static final double SLOW = 0.5;
        public static final double TURN = 1.0;

        private Speed() {}
    }

    public static final class Defaults {
        public static final boolean FIELD_CENTRIC = true;

        private Defaults() {}
    }

    /** Positive is forward, left, and counterclockwise, matching Pedro's coordinate frame. */
    public static final Axis FORWARD = gamepad -> -gamepad.left_stick_y;
    public static final Axis STRAFE = gamepad -> -gamepad.left_stick_x;
    public static final Axis TURN = gamepad -> -gamepad.right_stick_x;

    public static final Button SLOW_MODE = gamepad -> gamepad.left_bumper;
    public static final Button TOGGLE_FIELD_CENTRIC = Gamepad::optionsWasPressed;
    public static final Button RESET_HEADING = Gamepad::shareWasPressed;
}
