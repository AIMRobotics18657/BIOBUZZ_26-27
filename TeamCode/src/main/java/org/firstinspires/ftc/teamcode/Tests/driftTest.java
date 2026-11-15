package org.firstinspires.ftc.teamcode.Tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="driftTest")
public class driftTest extends OpMode {
    private GoBildaPinpointDriver pinpoint;

    public Follower follower;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private int TEST_LENGTH_TIME = 20;
    private int TEST_LENGTH_AMMOUNT = 5;

    private ElapsedTime timer = new ElapsedTime();

    private File file;
    private FileWriter writer;


    public double speed = 0.5;


    RevBlinkinLedDriver blinkin;



    public double laps = 20;


    public void init() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

//        follower = Constants.create(hardwareMap);
//        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    public void start() {
        file = new File(hardwareMap.appContext.getExternalFilesDir(null), "pinpoint_data.csv");
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            telemetry.addData("File Error", e.getMessage());
            telemetry.update();
        }

//        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    public void loop() {

//        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE);

        double x = pinpoint.getPosX(DistanceUnit.INCH);
        double y = pinpoint.getPosY(DistanceUnit.INCH);
        double heading = pinpoint.getHeading(AngleUnit.DEGREES);

        if (gamepad1.yWasPressed()) {
            timer.reset();
            try {
                writer.write("Time,X,Y,Heading\n");
            } catch (IOException e) {
                telemetry.addData("File Error", e.getMessage());
                telemetry.update();
            }

            pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
            pinpoint.update();

            for (int k = 0; k < TEST_LENGTH_TIME; k++) {
                timer.reset();
                pinpoint.update();

                while (timer.seconds() < 1) {
                    pinpoint.update();
                    frontLeft.setPower(-speed);
                    frontRight.setPower(speed);
                    backLeft.setPower(-speed);
                    backRight.setPower(speed);

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

                while (timer.seconds() < 2) {
                    pinpoint.update();
                    frontLeft.setPower(speed);
                    frontRight.setPower(-speed);
                    backLeft.setPower(speed);
                    backRight.setPower(-speed);

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
                }
                timer.reset();

                pinpoint.update();
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);

                pinpoint.update();


                try {
                    writer.close();
                } catch (IOException e) {
                    telemetry.addData("File Error", e.getMessage());
                    telemetry.update();
                }
            } if (gamepad1.xWasPressed()) {
                TEST_LENGTH_TIME -= 1;
            } else if (gamepad1.bWasPressed()) {
                TEST_LENGTH_TIME += 1;
            } else if (gamepad1.dpadLeftWasPressed()) {
                TEST_LENGTH_AMMOUNT -= 1;
            } else if (gamepad1.dpadRightWasPressed()) {
                TEST_LENGTH_AMMOUNT += 1;
            }
            telemetry.addData("Test Max Time:", TEST_LENGTH_TIME);
//            telemetry.addData("Test ammount per time:", TEST_LENGTH_AMMOUNT);
            telemetry.update();
        }
    }
