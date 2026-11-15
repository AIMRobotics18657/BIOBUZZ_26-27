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
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set(ConfigInfo.frontLeftDrive.getDeviceName());
        c.frontRightName.set(ConfigInfo.frontRightDrive.getDeviceName());
        c.backLeftName.set(ConfigInfo.backLeftDrive.getDeviceName());
        c.backRightName.set(ConfigInfo.backRightDrive.getDeviceName());
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(2.455678774615911);
        c.yPodOffset.set(0.6465059565746878);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1740644053318723);
                Controller secondaryTranslationalForward = Controller.proportional(0.06431214668355607);
                Controller primaryTranslationalLateral = Controller.proportional(0.21573017961817373);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07970653695235101);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017858011744375655));
                c.brake.set(Controller.proportionalFeedforward(0.015179309982719305));

                c.headingFeedback.set(Controller.proportional(2.6838952756180037));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03911650476495428, 0.008148463422742365));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06955846214348108, 0.03621409247875838));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001612076404404685, 0.002205662873072249));

                c.maxAchievableForwardVelocity.set(58.73652977383538);
                c.maxAchievableStrafeVelocity.set(49.69106604659499);
                c.naturalForwardDeceleration.set(39.04014695471649);
                c.naturalStrafeDeceleration.set(57.55113841918131);

                c.brakeAggression.set(1.0);
                c.maxBrakingPower.set(0.2);
                c.maxAccelerationConstraint.set(ForesightConfig.Constraint.NONE);
                c.maxVelocityConstraint.set(ForesightConfig.Constraint.NONE);
                c.maxDecelerationConstraint.set(ForesightConfig.Constraint.NONE);
                c.coastDownToVelocity.set(0.0);

                // Other Foresight constants...
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

}