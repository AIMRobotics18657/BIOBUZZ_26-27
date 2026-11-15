package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.subsystems.sds.ServoState;
import com.aimrobotics.aimlib.subsystems.sds.StateDrivenServo;
import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.ConfigInfo;

public class Gate extends Mechanism {

    private StateDrivenServo gate;

    public ServoState OPEN = new ServoState(1.0);
    public ServoState CLOSED = new ServoState(0.0);

    @Override
    public void init(HardwareMap hwMap) {
        gate = new StateDrivenServo(new ServoState[]{OPEN, CLOSED}, CLOSED, ConfigInfo.gate.getDeviceName());

        gate.init(hwMap);

    }

    @Override
    public void loop(AIMPad aimPad1) {
        gate.loop(aimPad1);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        gate.telemetry(telemetry);
    }

    public void open() {
        gate.setActiveTargetState(OPEN);
    }
    public void close() {
        gate.setActiveTargetState(CLOSED);
    }

    public ServoState getServoState() {
        return gate.getActiveTargetState();
    }
}

