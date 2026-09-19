package org.firstinspires.ftc.teamcode.opmode;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.ConfigInfo;
import org.firstinspires.ftc.teamcode.input.InputHandler;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Teleop", group = "Competition")
public class Teleop extends OpMode {
    private Follower follower;
    private InputHandler input;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(ConfigInfo.Field.ORIGIN);
        input = new InputHandler(gamepad1, gamepad2);

        telemetry.addLine("Initialized. Press start.");
        telemetry.update();
    }

    @Override
    public void start() {
        // Refresh localization before the first field-centric command.
        follower.update();
        input.reset();
    }

    @Override
    public void loop() {
        input.update();

        if (input.headingResetRequested()) follower.setHeading(0);

        DrivePowers powers = input.drivePowers();
        if (input.fieldCentric()) {
            powers = ManualDrive.fieldCentric(powers, follower.pose().heading());
        }

        follower.manual(powers);
        follower.update();

        addTelemetry();
    }

    @Override
    public void stop() {
        if (follower != null) {
            follower.stop();
            // Pedro 3 applies the idle drivetrain stop during update().
            follower.update();
        }
    }

    private void addTelemetry() {
        Pose pose = follower.pose();
        telemetry.addData("mode", input.fieldCentric() ? "field centric" : "robot centric");
        telemetry.addData("x", "%.2f", pose.x());
        telemetry.addData("y", "%.2f", pose.y());
        telemetry.addData("heading", "%.2f deg", Math.toDegrees(pose.heading()));
        telemetry.addData("loop", "%.1f hz", input.deltaSeconds() > 0 ? 1.0 / input.deltaSeconds() : 0.0);
        telemetry.update();
    }
}
