package org.firstinspires.ftc.teamcode.Settings;

import com.aimrobotics.aimlib.gamepad.AIMPad;

public class InputHandler {
    public boolean SHOOT = false;
    public boolean SHOOT_OFF = false;
    public boolean INCREASE_RATIO = false;
    public boolean DECREASE_RATIO = false;
    public boolean INCREASE_MAIN = false;
    public boolean DECREASE_MAIN = false;

    public boolean SWITCH_MAIN = false;

    public boolean TOGGLE_GATE = false;

    public boolean INTAKE_IN = false;
    public boolean INTAKE_OUT = false;

    public boolean TOGGLE_FLOWER = false;


    public void updateInputs(AIMPad aimPad1, AIMPad aimPad2) {
        SHOOT = aimPad1.isRightBumperPressed();
        SHOOT_OFF = aimPad1.isLeftBumperPressed();
        INCREASE_RATIO = aimPad1.isDPadUpPressed();
        DECREASE_RATIO = aimPad1.isDPadDownPressed();//need to do not pressed in last loop?
        INCREASE_MAIN = aimPad1.isDPadRightPressed();
        DECREASE_MAIN = aimPad1.isDPadLeftPressed();
        SWITCH_MAIN = aimPad1.isXPressed();

        TOGGLE_GATE = aimPad1.isAPressed();

        INTAKE_IN = aimPad1.getRightTrigger() > 0.01;
        INTAKE_OUT = aimPad1.getLeftTrigger() > 0.01;

        TOGGLE_FLOWER = aimPad1.isBPressed();
    }
}
