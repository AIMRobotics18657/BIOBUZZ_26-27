package org.firstinspires.ftc.teamcode.config;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Everything specific to this robot: Robot Controller config names, mounting geometry, and tuner
 * output.
 *
 * Most fields hold a real, conventional value that a tuner may later correct. Only measurements
 * that cannot exist until the robot is physically built use {@link #PLACEHOLDER}.
 */
public final class ConfigInfo {
    private ConfigInfo() {}

    /** A measurement that can only be taken off the built robot. Never a guess dressed as a value. */
    public static final double PLACEHOLDER = 1.0;

    /** Names follow team convention. Directions are the standard mecanum layout; MecanumTuner confirms. */
    public static final class Drive {
        public static final String FRONT_LEFT = "frontLeft";
        public static final String FRONT_RIGHT = "frontRight";
        public static final String BACK_LEFT = "backLeft";
        public static final String BACK_RIGHT = "backRight";

        public static final DcMotorSimple.Direction FRONT_LEFT_DIRECTION = REVERSE;
        public static final DcMotorSimple.Direction BACK_LEFT_DIRECTION = REVERSE;
        public static final DcMotorSimple.Direction FRONT_RIGHT_DIRECTION = FORWARD;
        public static final DcMotorSimple.Direction BACK_RIGHT_DIRECTION = FORWARD;

        private Drive() {}
    }

    public static final class Localization {
        public static final String PINPOINT = "pinpoint";

        public static final GoBildaPinpointDriver.GoBildaOdometryPods POD_TYPE =
                GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;

        /** PinpointTuner flips these if a pod counts backwards. */
        public static final GoBildaPinpointDriver.EncoderDirection X_POD_DIRECTION =
                GoBildaPinpointDriver.EncoderDirection.FORWARD;
        public static final GoBildaPinpointDriver.EncoderDirection Y_POD_DIRECTION =
                GoBildaPinpointDriver.EncoderDirection.FORWARD;

        /** Measured: inches from the tracking center to each pod. +X forward, +Y left. */
        public static final double X_POD_OFFSET = PLACEHOLDER;
        public static final double Y_POD_OFFSET = PLACEHOLDER;

        private Localization() {}
    }

    /**
     * Measured by ForesightTuner. Pedro ships no defaults for any of these — every one is
     * {@code ConfigVar.required()} with no fallback — so there is nothing reasonable to set until
     * the robot drives. Path following is not usable until they are real.
     */
    public static final class Foresight {
        public static final double FORWARD_TRANSLATIONAL_PRIMARY = PLACEHOLDER;
        public static final double FORWARD_TRANSLATIONAL_SECONDARY = PLACEHOLDER;
        public static final double STRAFE_TRANSLATIONAL_PRIMARY = PLACEHOLDER;
        public static final double STRAFE_TRANSLATIONAL_SECONDARY = PLACEHOLDER;

        public static final double COAST = PLACEHOLDER;
        public static final double BRAKE = PLACEHOLDER;

        public static final double HEADING = PLACEHOLDER;
        public static final double HEADING_LINEAR_BRAKE = PLACEHOLDER;
        public static final double HEADING_QUADRATIC_BRAKE = PLACEHOLDER;

        public static final double FORWARD_LINEAR_BRAKE = PLACEHOLDER;
        public static final double STRAFE_LINEAR_BRAKE = PLACEHOLDER;
        public static final double FORWARD_QUADRATIC_BRAKE = PLACEHOLDER;
        public static final double STRAFE_QUADRATIC_BRAKE = PLACEHOLDER;

        public static final double MAX_FORWARD_VELOCITY = PLACEHOLDER;
        public static final double MAX_STRAFE_VELOCITY = PLACEHOLDER;
        public static final double NATURAL_FORWARD_DECELERATION = PLACEHOLDER;
        public static final double NATURAL_STRAFE_DECELERATION = PLACEHOLDER;

        private Foresight() {}
    }

    /**
     * The camera's pose on the robot is configured in the Limelight web UI ("Camera Pose in Robot
     * Space"), not here, so no mounting offsets belong in this block.
     */
    public static final class Vision {
        public static final String LIMELIGHT = "limelight";

        public static final int DEFAULT_PIPELINE = 0;
        public static final int POLL_RATE_HZ = 100;

        /** Results older than this are treated as no target. */
        public static final long MAX_STALENESS_MS = 200;

        /**
         * Limelight reports field coordinates from the field centre in metres; Pedro works in
         * inches from a corner. Half of a 144 in field. Verify against your uploaded field map and
         * whichever origin the season's paths use before trusting {@code fieldPose()}.
         */
        public static final double FIELD_ORIGIN_OFFSET_X = 72.0;
        public static final double FIELD_ORIGIN_OFFSET_Y = 72.0;

        private Vision() {}
    }

    public static final class Field {
        public static final Pose ORIGIN = new Pose(0, 0, 0);

        private Field() {}
    }
}
