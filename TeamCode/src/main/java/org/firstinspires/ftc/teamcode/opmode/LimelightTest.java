package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.LimelightCamera;

/**
 * Camera-only check: confirms the Limelight is configured, connected and seeing targets, without
 * requiring the drivetrain. Botpose is not shown because MegaTag2 needs a live robot heading.
 */
@TeleOp(name = "Limelight Test", group = "Test")
public class LimelightTest extends LinearOpMode {
    private LimelightCamera camera;

    @Override
    public void runOpMode() {
        camera = new LimelightCamera(hardwareMap);

        telemetry.addLine("Limelight initialized. Press start.");
        telemetry.update();

        waitForStart();
        camera.start();

        while (opModeIsActive()) {
            camera.update();

            LLStatus status = camera.status();
            telemetry.addData("connected", camera.isConnected());
            telemetry.addData("pipeline", "%d (%s)", status.getPipelineIndex(), status.getPipelineType());
            telemetry.addData("health", "%.0fC  cpu %.0f%%  %d fps",
                    status.getTemp(), status.getCpu(), (int) status.getFps());

            if (camera.hasTarget()) {
                telemetry.addData("target", "tx %.2f  ty %.2f  ta %.2f",
                        camera.tx(), camera.ty(), camera.ta());
                telemetry.addData("staleness", "%d ms", camera.staleness());
                for (LLResultTypes.FiducialResult fiducial : camera.fiducials()) {
                    telemetry.addData("tag " + fiducial.getFiducialId(), "%s  x %.2f  y %.2f",
                            fiducial.getFamily(),
                            fiducial.getTargetXDegrees(),
                            fiducial.getTargetYDegrees());
                }
            } else {
                telemetry.addLine("no target");
            }

            telemetry.update();
        }

        camera.stop();
    }
}
