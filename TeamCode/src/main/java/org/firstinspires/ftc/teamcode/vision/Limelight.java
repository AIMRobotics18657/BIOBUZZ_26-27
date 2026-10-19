package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class Limelight extends OpMode {

    private Limelight3A limelight3A;

    private final static double CAMERA_HEIGHT = 1;
    private final static double GOAL_HEIGHT = 58.64;

    private final static double CAMERA_ANGLE = 1;

    public double distance = 0;


    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(0);
    }

    public void start(){
        limelight3A.start();
    }

    public void loop() {

        LLResult llResult = limelight3A.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            distance = getDistance(llResult.getTy());

            telemetry();

        }

    }

    private double getDistance(double ty) {

        double angleToTarget = CAMERA_ANGLE + ty;
        double heightDifference = GOAL_HEIGHT - CAMERA_HEIGHT;

        return heightDifference/Math.tan(Math.toRadians(angleToTarget));


    }

    public void telemetry () {
        telemetry.addData("Distance", distance);
        telemetry.update();
    }
}


