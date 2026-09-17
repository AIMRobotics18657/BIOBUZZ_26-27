package org.firstinspires.ftc.teamcode.input;

import com.pedropathing.drivetrain.DrivePowers;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.GamepadSettings;
import org.firstinspires.ftc.teamcode.config.GamepadSettings.Shaping;
import org.firstinspires.ftc.teamcode.config.GamepadSettings.Speed;

/**
 * Turns raw gamepad state into shaped drive powers and latched driver actions.
 * Call {@link #update()} exactly once per OpMode loop before reading anything.
 */
public class InputHandler {
    private final Gamepad driver;
    private final Gamepad operator;

    private final InputModification.SlewLimiter forwardLimiter =
            new InputModification.SlewLimiter(Shaping.SLEW_RATE);
    private final InputModification.SlewLimiter strafeLimiter =
            new InputModification.SlewLimiter(Shaping.SLEW_RATE);
    private final InputModification.SlewLimiter turnLimiter =
            new InputModification.SlewLimiter(Shaping.SLEW_RATE);

    private final ElapsedTime loopTimer = new ElapsedTime();

    private DrivePowers drivePowers = DrivePowers.zero();
    private boolean fieldCentric = GamepadSettings.Defaults.FIELD_CENTRIC;
    private boolean headingResetRequested;
    private double deltaSeconds;

    public InputHandler(Gamepad driver, Gamepad operator) {
        this.driver = driver;
        this.operator = operator;
    }

    public void update() {
        deltaSeconds = loopTimer.seconds();
        loopTimer.reset();

        if (GamepadSettings.TOGGLE_FIELD_CENTRIC.read(driver)) fieldCentric = !fieldCentric;
        headingResetRequested = GamepadSettings.RESET_HEADING.read(driver);

        double multiplier = GamepadSettings.SLOW_MODE.read(driver) ? Speed.SLOW : Speed.BASE;

        double forward = shape(GamepadSettings.FORWARD.read(driver), Shaping.DRIVE_EXPO);
        double strafe = shape(GamepadSettings.STRAFE.read(driver), Shaping.DRIVE_EXPO);
        double turn = shape(GamepadSettings.TURN.read(driver), Shaping.TURN_EXPO) * Speed.TURN;

        drivePowers = new DrivePowers(
                forwardLimiter.calculate(forward * multiplier, deltaSeconds),
                strafeLimiter.calculate(strafe * multiplier, deltaSeconds),
                turnLimiter.calculate(turn * multiplier, deltaSeconds));
    }

    private static double shape(double raw, double expo) {
        return InputModification.expo(
                InputModification.deadzone(raw, Shaping.STICK_DEADZONE), expo);
    }

    public DrivePowers drivePowers() {
        return drivePowers;
    }

    public boolean fieldCentric() {
        return fieldCentric;
    }

    public boolean headingResetRequested() {
        return headingResetRequested;
    }

    public double deltaSeconds() {
        return deltaSeconds;
    }

    public Gamepad driver() {
        return driver;
    }

    public Gamepad operator() {
        return operator;
    }

    public void reset() {
        forwardLimiter.reset();
        strafeLimiter.reset();
        turnLimiter.reset();
        drivePowers = DrivePowers.zero();
        loopTimer.reset();
    }
}
