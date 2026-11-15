package org.firstinspires.ftc.teamcode.MiniBots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name="MiniTank", group="CLUB")
public class MiniTank extends OpMode {

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private TouchSensor killSwitch;

    @Override
    public void init() {
        // Initialize motors
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");  // Replace "left_motor" with the actual name in your robot configuration
        frontRightMotor = hardwareMap.dcMotor.get("frontRight");  // Replace "right_motor" with the actual name in your robot configuration
        backLeftMotor = hardwareMap.dcMotor.get("backLeft");  // Replace "left_motor" with the actual name in your robot configuration
        backRightMotor = hardwareMap.dcMotor.get("backRight");


        // Set motor directions
        //leftMotor.setDirection(DcMotor.Direction.REVERSE);
        //rightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        killSwitch = hardwareMap.touchSensor.get("kill");
    }

    @Override
    public void loop() {
//        double y = -gamepad1.left_stick_y * 0.65;
//        double turn = gamepad1.right_stick_x * 0.4;

  //      double y = -gamepad1.left_stick_y * 0.5; // small
 //       double turn = gamepad1.right_stick_x * 0.5; //small

        double y = - gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        //double y = -gamepad1.left_stick_y; // big
        //double turn = gamepad1.right_stick_x; //big

        if (gamepad1.b) {
            y = (gamepad1.right_trigger + -gamepad1.left_trigger) * 0.8;
        }
        // Tank drive control
        double leftPower = y + turn;  // Adjust if needed
        double rightPower = y - turn;  // Adjust if needed

        // Set motor powers
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
        backLeftMotor.setPower(leftPower);
        backRightMotor.setPower(rightPower);

        if (killSwitch.isPressed()) {
            requestOpModeStop();
        }

        telemetry.addData("Button is pressed:", killSwitch.isPressed());
    }
}

