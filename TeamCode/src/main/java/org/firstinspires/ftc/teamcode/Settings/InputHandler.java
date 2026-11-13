package org.firstinspires.ftc.teamcode.input;

import com.aimrobotics.aimlib.gamepad.AIMPad;
import com.pedropathing.drivetrain.DrivePowers;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.GamepadSettings;

public class InputHandler {
    public  boolean SHOOT = false;


    public void updateInputs(AIMPad aimPad1, AIMPad aimPad2) {
        SHOOT = aimPad1.isAPressed();
    }
}
