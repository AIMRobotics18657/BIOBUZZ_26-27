package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;
import org.firstinspires.ftc.teamcode.Tests.ratioShooting;

public class Flywheels extends Mechanism {
    private DcMotorEx bottomFlywheel;
    private DcMotorEx topFlywheel;

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

    public PIDFCoefficients pidfCoefficients;

    public double mainSpeed = 2.0;
    public double counterSpeed = 0;
    public double speedRatio = 1.0;
    public boolean isBottomMain = true;

    public double bottomMeasuredSpeed;
    public double topMeasuredSpeed;


    public void init(HardwareMap hwMap){
        bottomFlywheel = hwMap.get(DcMotorEx.class, ConfigInfo.bottomFlywheel.getDeviceName());
        topFlywheel = hwMap.get(DcMotorEx.class, ConfigInfo.topFlywheel.getDeviceName());

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pidfCoefficients = new PIDFCoefficients(Coefficients.kP.gain, Coefficients.kI.gain, Coefficients.kD.gain, Coefficients.kF.gain);

        setPIDFCoefficients(pidfCoefficients);
    }

    public void setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior) {
        bottomFlywheel.setZeroPowerBehavior(behavior);
        topFlywheel.setZeroPowerBehavior(behavior);
    }

    public void setRunMode(DcMotorEx.RunMode mode) {
        bottomFlywheel.setMode(mode);
        topFlywheel.setMode(mode);
    }

    public void setPIDFCoefficients(PIDFCoefficients coefficients) {
        bottomFlywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
        topFlywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
    }

    public void switchMain() {
        if (isBottomMain) {
            isBottomMain = false;
        } else {
            isBottomMain = true;
        }
    }

    public void increaseRatio() {
        speedRatio += 0.01;
    }

    public void decreaseRatio() {
        speedRatio = Math.max(0.0, speedRatio - 0.01);
    }
    public void increaseMain() {
        mainSpeed = Math.min(628.32, mainSpeed + 0.01);
    }
    public void decreaseMain() {
        mainSpeed = Math.max(0.0, mainSpeed - 0.01);
    }

    public void setSpeeds() {

        counterSpeed = mainSpeed * speedRatio;

        if (isBottomMain) {
            topFlywheel.setVelocity(counterSpeed, AngleUnit.RADIANS);
            bottomFlywheel.setVelocity(mainSpeed, AngleUnit.RADIANS);
        } else {
            topFlywheel.setVelocity(mainSpeed, AngleUnit.RADIANS);
            bottomFlywheel.setVelocity(counterSpeed, AngleUnit.RADIANS);
        }

    }

    public void turnSpeedOff() {
        topFlywheel.setVelocity(0);
        bottomFlywheel.setVelocity(0);

    }

    @Override
    public void loop(AIMPad aimPad) {

        counterSpeed = mainSpeed * speedRatio;


        //max radians per second is 628.32
        //max radians per second is 628.32

        bottomMeasuredSpeed = bottomFlywheel.getVelocity(AngleUnit.RADIANS);
        topMeasuredSpeed = topFlywheel.getVelocity(AngleUnit.RADIANS);


    }

    public void telemetry(Telemetry telemetry) {

        telemetry.addData("Main Velo (Rad/Sec)", mainSpeed);
        telemetry.addData("Counter Velo (Rad/Sec)", counterSpeed);

        telemetry.addData("Bottom Velo measured (Rad/sec)", bottomMeasuredSpeed);
        telemetry.addData("Top Velo measured (Rad/Sec)", topMeasuredSpeed);

        telemetry.addData("Speed Ratio", speedRatio);

        telemetry.addData("is Bottom Main?", isBottomMain);

    }

}
