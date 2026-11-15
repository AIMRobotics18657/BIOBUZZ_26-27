package org.firstinspires.ftc.teamcode.Tests;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp (name="Shooter Testing")
public class ShooterTesting extends OpMode {

    private DcMotorEx main;
    private DcMotorEx counter;

    public double mainSpeed = 0;
    public double counterSpeed = 0;

    public enum Coefficients{
        kP(15.0),
        kI(0),
        kD(0),
        kF(11.0);

        public double gain;

        private Coefficients (double gain){
            this.gain = gain;
        }

    }

    public Coefficients activeCoefficient = Coefficients.kP;

    public double[] stepSize = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};
    public int stepIndex = 1;

    public PIDFCoefficients pidfCoefficients;

    private AIMPad aimPad1;


    ElapsedTime dt = new ElapsedTime();
    ElapsedTime checkPeriod = new ElapsedTime();
    boolean ready = false;
    boolean recovering = false;
    double recoveryTime;
    double threshold = 0.01;

    public void init(){
        main = hardwareMap.get(DcMotorEx.class, "main");
        counter = hardwareMap.get(DcMotorEx.class, "counter");
        main.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        counter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        counter.setDirection(DcMotorSimple.Direction.FORWARD);
        aimPad1 = new AIMPad(gamepad1);

        pidfCoefficients = new PIDFCoefficients(Coefficients.kP.gain, Coefficients.kI.gain, Coefficients.kD.gain, Coefficients.kF.gain);

        main.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        counter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

    }

    public void loop() {
        aimPad1.update(gamepad1);

        if (aimPad1.isAPressed() && !aimPad1.isAReleased()) mainSpeed += 0.1;
        if (aimPad1.isBPressed() && !aimPad1.isBReleased()) counterSpeed += 0.1;
        if (aimPad1.isXPressed() && !aimPad1.isXReleased()) mainSpeed -= 0.1;
        if (aimPad1.isYPressed() && !aimPad1.isYReleased()) counterSpeed -= 0.1;

        if (aimPad1.isRightBumperPressed() && !aimPad1.isRightBumperReleased()) stepIndex = (stepIndex + 1) % stepSize.length;

        if (aimPad1.isLeftBumperPressed() && !aimPad1.isLeftBumperReleased()) {
            switch (activeCoefficient) {
                case kP:
                    activeCoefficient = Coefficients.kI;
                    break;
                case kI:
                    activeCoefficient = Coefficients.kD;
                    break;
                case kD:
                    activeCoefficient = Coefficients.kF;
                    break;
                case kF:
                    activeCoefficient = Coefficients.kP;
                    break;
            }

        }

        if (aimPad1.isDPadUpPressed() && !aimPad1.isDPadUpReleased()) activeCoefficient.gain += stepSize[stepIndex];
        if (aimPad1.isDPadDownPressed() && !aimPad1.isDPadDownReleased()) activeCoefficient.gain -= stepSize[stepIndex];
        if (aimPad1.isDPadRightPressed() && !aimPad1.isDPadRightReleased()) threshold += 0.01;
        if (aimPad1.isDPadLeftPressed() && !aimPad1.isDPadLeftReleased()) threshold -= 0.01;



        pidfCoefficients = new PIDFCoefficients(Coefficients.kP.gain, Coefficients.kI.gain, Coefficients.kD.gain, Coefficients.kF.gain);


        main.setVelocityPIDFCoefficients(pidfCoefficients.p, pidfCoefficients.i, pidfCoefficients.d, pidfCoefficients.f);
        counter.setVelocityPIDFCoefficients(pidfCoefficients.p, pidfCoefficients.i, pidfCoefficients.d, pidfCoefficients.f);

        main.setVelocity(mainSpeed, AngleUnit.RADIANS); //max radians per second is 628.32
        counter.setVelocity(counterSpeed, AngleUnit.RADIANS); //max radians per second is 628.32

        double speedRatio = counterSpeed/mainSpeed;

        double mainMeasuredSpeed = main.getVelocity(AngleUnit.RADIANS);
        double counterMeasuredSpeed = counter.getVelocity(AngleUnit.RADIANS);

        recoveryTimer(mainSpeed, mainMeasuredSpeed);

        telemetry.addData("Main recovery Time (ms): ", recoveryTime);
        telemetry.addData("Threshold (+ dpad right/ - dpad left): ", threshold);

        telemetry.addData("Ticks/rev", main.getMotorType().getTicksPerRev());
        telemetry.addData("type", main.getMotorType());

        telemetry.addData("main Velo (Rad/sec) (+A, -X)", mainSpeed);
        telemetry.addData("counter Velo (Rad/Sec) (+B, -Y)", counterSpeed);
        telemetry.addData("main Velo measured (Rad/sec)", mainMeasuredSpeed);
        telemetry.addData("counter Velo measured (Rad/Sec)", counterMeasuredSpeed);
        telemetry.addData("main velocity error (Rad/sec)", mainSpeed - mainMeasuredSpeed);
        telemetry.addData("counter velocity error (Rad/sec)", counterSpeed - counterMeasuredSpeed);
        telemetry.addData("counter/main", speedRatio);

        telemetry.addData("Current Tuning Coefficient: (D Pad Up and Down)", activeCoefficient);
        telemetry.addData("PIDF Coefficients: (Left Bumper)", pidfCoefficients.toString());
        telemetry.addData("Step Size: (Right Bumper)", stepSize[stepIndex]);
        telemetry.update();

    }



    public void recoveryTimer(double targetVelo, double currentVelo) {
        double tolerance = Math.abs(targetVelo) * threshold;
        boolean withinTolerence = Math.abs(targetVelo - currentVelo) <= tolerance;

        if (targetVelo == 0) {
            ready = false;
            recovering = false;
            checkPeriod.reset();
            return;
        }

        if (!withinTolerence) {

            if (ready) {
                dt.reset();
                recovering = true;
                ready = false;
            }

            checkPeriod.reset();

        } else if (checkPeriod.milliseconds() >= 100) {
            if (recovering) {
                recoveryTime = dt.milliseconds();
                recovering = false;
            }
            ready = true;
        }



    }
}
