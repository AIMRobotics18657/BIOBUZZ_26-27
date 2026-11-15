package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.subsystems.sds.ServoState;
import com.aimrobotics.aimlib.subsystems.sds.StateDrivenServo;
import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;

public class Flower extends Mechanism {

    private StateDrivenServo rightFlower;
    private StateDrivenServo leftFlower;

    public ServoState UP = new ServoState(1.0);
    public ServoState DOWN = new ServoState(0.0);

    @Override
    public void init(HardwareMap hwMap) {
        rightFlower = new StateDrivenServo(new ServoState[]{UP, DOWN}, UP, ConfigInfo.rightFlower.getDeviceName());
        leftFlower = new StateDrivenServo(new ServoState[]{UP, DOWN}, UP, ConfigInfo.leftFlower.getDeviceName());

        leftFlower.init(hwMap);
        rightFlower.init(hwMap);

    }

    @Override
    public void loop(AIMPad aimPad1) {
        rightFlower.loop(aimPad1);
        leftFlower.loop(aimPad1);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        rightFlower.telemetry(telemetry);
        leftFlower.telemetry(telemetry);
    }

    public void setUp() {
        rightFlower.setActiveTargetState(UP);
        leftFlower.setActiveTargetState(UP);
    }
    public void setDown() {
        rightFlower.setActiveTargetState(DOWN);
        leftFlower.setActiveTargetState(DOWN);
    }

    public ServoState getServoState() {
        return leftFlower.getActiveTargetState();
    }
}

