package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.GamepadSettings;
import org.firstinspires.ftc.teamcode.Settings.InputModification;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class
Drivebase {

    private Follower follower;

    private Pose robotPose;

    public void init(HardwareMap hwMap, Pose startingPose) {
        follower = Constants.create(hwMap);
        follower.setPose(startingPose);
    }

    private double deadzonedStickInput(double input) {
        if (Math.abs(input) > GamepadSettings.GP1_STICK_DEADZONE) {
            return input;
        } else {
            return 0;
        }
    }
    public void manualDrive(AIMPad aimPad) {

        double y = InputModification.poweredInput(deadzonedStickInput(-aimPad.getLeftStickY()), GamepadSettings.EXPONENT_MODIFIER);
        double x = InputModification.poweredInput(deadzonedStickInput(-aimPad.getLeftStickX()), GamepadSettings.EXPONENT_MODIFIER);
        double rx = InputModification.poweredInput(deadzonedStickInput(-aimPad.getRightStickX()), GamepadSettings.EXPONENT_MODIFIER);

        follower.manual(y, x, rx);
        follower.update();

        robotPose = follower.pose();
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Current Pose", robotPose);
    }
}
