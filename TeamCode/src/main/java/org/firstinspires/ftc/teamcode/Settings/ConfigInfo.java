package org.firstinspires.ftc.teamcode.Settings;

import com.aimrobotics.aimlib.util.HardwareInterface;

public  class ConfigInfo {
    public static final HardwareInterface leftIntake = new HardwareInterface("leftIntake", false, 0);
    public static final HardwareInterface rightIntake = new HardwareInterface("rightIntake", false, 0);

    public static final HardwareInterface bottomFlywheel = new HardwareInterface("bottomFlywheel", false, 0);
    public static final HardwareInterface topFlywheel = new HardwareInterface("topFlywheel", false, 0);


    public static final HardwareInterface rightFlower = new HardwareInterface("rightFlower", false, 0);
    public static final HardwareInterface leftFlower = new HardwareInterface("leftFlower", false, 0);

    public static final HardwareInterface gate = new HardwareInterface("gate", false, 0);

    public static final HardwareInterface frontLeftDrive = new HardwareInterface("flDrive", false, 0);
    public static final HardwareInterface frontRightDrive = new HardwareInterface("frDrive", false, 1);
    public static final HardwareInterface backLeftDrive = new HardwareInterface("blDrive", false, 2);
    public static final HardwareInterface backRightDrive = new HardwareInterface("brDrive", false, 3);

}
