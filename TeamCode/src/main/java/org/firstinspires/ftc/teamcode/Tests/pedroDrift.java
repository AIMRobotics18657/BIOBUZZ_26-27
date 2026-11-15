package org.firstinspires.ftc.teamcode.Tests;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Autonomous(name = "Pedro Drift", group = "Tests")
public class pedroDrift extends OpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startPose = poseFactory.of(0, 0, 0);
    private final Pose farPose = poseFactory.of(0, 48, 0);
    private Follower follower;
    private Path outbound;
    private boolean complete;

    private File file;
    private FileWriter writer;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.holdEnd.set(false);
        follower.setPose(startPose);
        outbound = line(startPose, farPose).constant(startPose);
        complete = false;
        telemetry.addLine("Ready: (0, 0) -> (0, 48) inches, heading 0 degrees");
        telemetry.addLine("At heading 0, +Y is left (a strafe).");
        telemetry.update();
    }

    @Override
    public void start() {
        follower.setPose(startPose);
        follower.follow(outbound);

        file = new File(hardwareMap.appContext.getExternalFilesDir(null), "pinpoint_data.csv");
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            telemetry.addData("File Error", e.getMessage());
            telemetry.update();
        }

    }

    @Override
    public void loop() {

        Pose current = follower.pose();
        timer.reset();
        follower.update();
        if (!complete && !follower.following()) {
            complete = true;
            follower.stop();
            follower.update();
        }
        telemetry.addData("Status", complete ? "Complete" : "Following");
        telemetry.addData("Pose", follower.pose());
        telemetry.addData("Distance to endpoint (in)", follower.pose().distance(farPose));
        telemetry.update();

        double x = current.x();
        double y = current.y();
        double heading = current.heading();


        if (timer.seconds() % 0.5 == 0) {

            try {
                writer.write(x + "," + y + "," + heading + "\n");
                telemetry.addData("posx", x);
                telemetry.addData("posy", y);
                telemetry.update();
                writer.flush();
            } catch (IOException e) {
                telemetry.addData("File Error", e.getMessage());
                telemetry.update();
            }

        }

    }

    @Override
    public void stop() {
        if (follower != null) {
            follower.stop();
            follower.update();
        }
    }
}
