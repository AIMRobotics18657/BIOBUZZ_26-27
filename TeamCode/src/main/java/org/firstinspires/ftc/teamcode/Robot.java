package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.aimrobotics.aimlib.util.HardwareInterface;
import com.aimrobotics.aimlib.util.Mechanism;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Settings.InputHandler;

public class Robot extends Mechanism {

    private Pose startingPose;
    private boolean isAuto;
    private boolean isRed;

    private Drivebase drivebase = new Drivebase();
    private ScoringAssembly scorer = new ScoringAssembly();

    private InputHandler handler = new InputHandler();
    private boolean shooterEnabled = false;

    public Robot (Pose startingPose, boolean isAuto, boolean isRed) {
        this.startingPose = startingPose;
        this.isAuto = isAuto;
        this.isRed = isRed;
    }

    public void init(HardwareMap hwMap) {
        drivebase.init(hwMap, startingPose);
        scorer.init(hwMap);
    }

    public void loop(AIMPad aimPad1, AIMPad aimPad2) {
        handler.updateInputs(aimPad1, aimPad2);


        if (!isAuto) {
            drivebase.manualDrive(aimPad1);

            if (handler.SHOOT_OFF) {
                shooterEnabled = false;
            } else if (handler.SHOOT) {
                shooterEnabled = true;
            }



            if (handler.INCREASE_RATIO) {
                scorer.flywheels.increaseRatio();
            } else if (handler.DECREASE_RATIO) {
                scorer.flywheels.decreaseRatio();
            }

            if (handler.INCREASE_MAIN) {
                scorer.flywheels.increaseMain();
            } else if (handler.DECREASE_MAIN) {
                scorer.flywheels.decreaseMain();
            }

            if (handler.SWITCH_MAIN) {
                scorer.flywheels.switchMain();
            }

            if (shooterEnabled) {
                scorer.flywheels.setSpeeds();
            } else {
                scorer.flywheels.turnSpeedOff();
            }


            if (handler.TOGGLE_GATE) {
                if (scorer.gate.getServoState() == scorer.gate.OPEN) {
                    scorer.gate.close();
                } else {
                    scorer.gate.open();
                }
            }

            if (handler.INTAKE_IN) {
                scorer.intake.setMode(Intake.IntakeMode.IN);
            } else if (handler.INTAKE_OUT) {
                scorer.intake.setMode(Intake.IntakeMode.OUT);
            } else {
                scorer.intake.setMode(Intake.IntakeMode.OFF);
            }

            if (handler.TOGGLE_FLOWER) {
                if (scorer.flower.getServoState() == scorer.flower.UP) {
                    scorer.flower.setDown();
                } else {
                    scorer.flower.setUp();
                }
            }
        }



        scorer.loop(aimPad1);

    }

    public void telemetry(Telemetry telemetry) {
        drivebase.telemetry(telemetry);
        scorer.telemetry(telemetry);

    }

}
