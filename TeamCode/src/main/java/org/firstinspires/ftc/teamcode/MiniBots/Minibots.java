package org.firstinspires.ftc.teamcode.MiniBots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name="Minibots", group="CLUB")
public class Minibots extends OpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private TouchSensor killSwitch;

    @Override
    public void init() {
        // Initialize motors
        leftMotor = hardwareMap.dcMotor.get("left");  // Replace "left_motor" with the actual name in your robot configuration
        rightMotor = hardwareMap.dcMotor.get("right");  // Replace "right_motor" with the actual name in your robot configuration

        killSwitch = hardwareMap.get(TouchSensor.class, "kill");
        // Set motor directions
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
//        double y = -gamepad1.left_stick_y * 0.65;
//        double turn = gamepad1.right_stick_x * 0.4;

        double y = -gamepad1.left_stick_y * 0.8; // small
        double turn = gamepad1.right_stick_x * 0.8; //small

        //double y = -gamepad1.left_stick_y; // big
        //double turn = gamepad1.right_stick_x; //big

        if (gamepad1.b) {
            y = (gamepad1.right_trigger + -gamepad1.left_trigger) * 0.8;
        }
        // Tank drive control
        double leftPower = y + turn;  // Adjust if needed
        double rightPower = y - turn;  // Adjust if needed

        // Set motor powers
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        }

    }

