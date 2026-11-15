package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;

public class Intake extends Mechanism {

    private DcMotorEx rightIntake;
    private DcMotorEx leftIntake;

    public enum IntakeMode {
        IN(1),
        OUT(-1),
        OFF(0);

        public final double power;

        IntakeMode (double power) {this.power = power;}
    }

    public IntakeMode activeIntakeMode = IntakeMode.OFF;

    @Override
    public void init(HardwareMap hwMap) {
        rightIntake = hwMap.get(DcMotorEx.class, ConfigInfo.rightIntake.getDeviceName());
        leftIntake = hwMap.get(DcMotorEx.class, ConfigInfo.leftIntake.getDeviceName());

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior) {
        rightIntake.setZeroPowerBehavior(behavior);
        leftIntake.setZeroPowerBehavior(behavior);
    }

    public void setRunMode(DcMotorEx.RunMode mode) {
        rightIntake.setMode(mode);
        leftIntake.setMode(mode);
    }

    @Override
    public void loop (AIMPad aimPad) {
        setPower(activeIntakeMode);
    }

    public void setMode(IntakeMode mode) {
        activeIntakeMode = mode;
    }
    private void setPower(IntakeMode mode) {
        rightIntake.setPower(activeIntakeMode.power);
        leftIntake.setPower(activeIntakeMode.power);
    }

    public void telemetry (Telemetry telemetry) {
        telemetry.addData("Intake Mode:", activeIntakeMode);
    }
}
