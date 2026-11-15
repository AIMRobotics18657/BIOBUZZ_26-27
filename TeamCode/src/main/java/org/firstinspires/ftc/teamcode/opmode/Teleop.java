package org.firstinspires.ftc.teamcode.opmode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivebase;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.TeleOpConstants;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;
import org.firstinspires.ftc.teamcode.Settings.InputHandler;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Teleop", group = "Competition")
public class Teleop extends OpMode {
    private Follower follower;
    private InputHandler input;

    private AIMPad aimPad1;
    private AIMPad aimPad2;

    private Robot robot;

    private boolean isAuto = false;
    private boolean isRed;

    @Override
    public void init() {

        aimPad1 = new AIMPad(gamepad1);
        aimPad2 = new AIMPad(gamepad2);

        robot = new Robot(TeleOpConstants.ORIGIN, isAuto, isRed);

        robot.init(hardwareMap);
    }
    @Override
    public void loop() {
        aimPad1.update(gamepad1);
        aimPad2.update(gamepad2);

        robot.loop(aimPad1, aimPad2);
        robot.telemetry(telemetry);

        telemetry.update();
    }


}
