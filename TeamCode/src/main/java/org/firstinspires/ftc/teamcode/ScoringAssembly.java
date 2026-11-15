package org.firstinspires.ftc.teamcode;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.InputHandler;
import org.intellij.lang.annotations.Flow;

public class ScoringAssembly extends Mechanism {

    public Flywheels flywheels = new Flywheels();
    public Flower flower = new Flower();
    public Gate gate = new Gate();
    public Intake intake = new Intake();


    public void init(HardwareMap hwMap) {
        flywheels.init(hwMap);
        flower.init(hwMap);
        gate.init(hwMap);
        intake.init(hwMap);
    }

    public void loop(AIMPad aimPad) {
        flywheels.loop(aimPad);
        flower.loop(aimPad);
        gate.loop(aimPad);
        intake.loop(aimPad);
    }

    public void telemetry(Telemetry telemetry) {
        flywheels.telemetry(telemetry);
        flower.telemetry(telemetry);
        gate.telemetry(telemetry);
        intake.telemetry(telemetry);
    }
}
