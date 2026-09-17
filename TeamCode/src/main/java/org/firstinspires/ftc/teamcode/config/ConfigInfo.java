package org.firstinspires.ftc.teamcode.config;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Everything specific to this robot: Robot Controller config names, mounting geometry, and
 * tuner output. Any field set to {@link #PLACEHOLDER} is a stand-in, not a measurement.
 */
public final class ConfigInfo {
    private ConfigInfo() {}

    /** Stand-in for a value that is only knowable once the robot is built and tuned. */
    public static final double PLACEHOLDER = 1.0;

    /** Names must match the Robot Controller configuration. Directions come from MecanumTuner. */
    public static final class Drive {
        public static final String FRONT_LEFT = "frontLeft";
        public static final String FRONT_RIGHT = "frontRight";
        public static final String BACK_LEFT = "backLeft";
        public static final String BACK_RIGHT = "backRight";

        public static final DcMotorSimple.Direction FRONT_LEFT_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction FRONT_RIGHT_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction BACK_LEFT_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction BACK_RIGHT_DIRECTION = DcMotorSimple.Direction.FORWARD;

        private Drive() {}
    }

    /** Pod type, offsets, and pod directions all come from PinpointTuner. */
    public static final class Localization {
        public static final String PINPOINT = "pinpoint";

        public static final GoBildaPinpointDriver.GoBildaOdometryPods POD_TYPE =
                GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;

        /** Inches from the tracking center: +X forward, +Y left. */
        public static final double X_POD_OFFSET = 0.0;
        public static final double Y_POD_OFFSET = 0.0;

        public static final GoBildaPinpointDriver.EncoderDirection X_POD_DIRECTION =
                GoBildaPinpointDriver.EncoderDirection.FORWARD;
        public static final GoBildaPinpointDriver.EncoderDirection Y_POD_DIRECTION =
                GoBildaPinpointDriver.EncoderDirection.FORWARD;

        private Localization() {}
    }

    /** Every value here comes from ForesightTuner. Path following is not usable until they are real. */
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

    public static final class Field {
        public static final Pose ORIGIN = new Pose(0, 0, 0);

        private Field() {}
    }
}
