package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.ConfigInfo;

/** Wires the values in {@link ConfigInfo} into the Pedro follower. */
public class Constants {
    private static final double TRANSLATIONAL_PIECEWISE_THRESHOLD = 2.5;

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set(ConfigInfo.Drive.FRONT_LEFT);
        c.frontRightName.set(ConfigInfo.Drive.FRONT_RIGHT);
        c.backLeftName.set(ConfigInfo.Drive.BACK_LEFT);
        c.backRightName.set(ConfigInfo.Drive.BACK_RIGHT);
        c.frontLeftDirection.set(ConfigInfo.Drive.FRONT_LEFT_DIRECTION);
        c.frontRightDirection.set(ConfigInfo.Drive.FRONT_RIGHT_DIRECTION);
        c.backLeftDirection.set(ConfigInfo.Drive.BACK_LEFT_DIRECTION);
        c.backRightDirection.set(ConfigInfo.Drive.BACK_RIGHT_DIRECTION);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set(ConfigInfo.Localization.PINPOINT);
        c.podType.set(ConfigInfo.Localization.POD_TYPE);
        c.xPodOffset.set(ConfigInfo.Localization.X_POD_OFFSET);
        c.yPodOffset.set(ConfigInfo.Localization.Y_POD_OFFSET);
        c.xPodDirection.set(ConfigInfo.Localization.X_POD_DIRECTION);
        c.yPodDirection.set(ConfigInfo.Localization.Y_POD_DIRECTION);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(c -> {
        c.forwardTranslational.set(Controller
                .piecewise(Controller.proportional(ConfigInfo.Foresight.FORWARD_TRANSLATIONAL_SECONDARY))
                .put(TRANSLATIONAL_PIECEWISE_THRESHOLD,
                        Controller.proportional(ConfigInfo.Foresight.FORWARD_TRANSLATIONAL_PRIMARY)));
        c.strafeTranslational.set(Controller
                .piecewise(Controller.proportional(ConfigInfo.Foresight.STRAFE_TRANSLATIONAL_SECONDARY))
                .put(TRANSLATIONAL_PIECEWISE_THRESHOLD,
                        Controller.proportional(ConfigInfo.Foresight.STRAFE_TRANSLATIONAL_PRIMARY)));

        c.coast.set(Controller.proportionalFeedforward(ConfigInfo.Foresight.COAST));
        c.brake.set(Controller.proportionalFeedforward(ConfigInfo.Foresight.BRAKE));

        c.headingFeedback.set(Controller.proportional(ConfigInfo.Foresight.HEADING));
        c.headingBrakeCoefficients.set(Vector2D.cartesian(
                ConfigInfo.Foresight.HEADING_LINEAR_BRAKE,
                ConfigInfo.Foresight.HEADING_QUADRATIC_BRAKE));

        c.linearBrakeCoefficients.set(Matrix.diag(
                ConfigInfo.Foresight.FORWARD_LINEAR_BRAKE,
                ConfigInfo.Foresight.STRAFE_LINEAR_BRAKE));
        c.quadraticBrakeCoefficients.set(Matrix.diag(
                ConfigInfo.Foresight.FORWARD_QUADRATIC_BRAKE,
                ConfigInfo.Foresight.STRAFE_QUADRATIC_BRAKE));

        c.maxAchievableForwardVelocity.set(ConfigInfo.Foresight.MAX_FORWARD_VELOCITY);
        c.maxAchievableStrafeVelocity.set(ConfigInfo.Foresight.MAX_STRAFE_VELOCITY);
        c.naturalForwardDeceleration.set(ConfigInfo.Foresight.NATURAL_FORWARD_DECELERATION);
        c.naturalStrafeDeceleration.set(ConfigInfo.Foresight.NATURAL_STRAFE_DECELERATION);
    });

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig));
    }
}
