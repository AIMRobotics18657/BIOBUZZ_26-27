package org.firstinspires.ftc.teamcode.input;

import com.pedropathing.drivetrain.DrivePowers;

/** Stateless shaping applied to raw gamepad values before they reach the drivetrain. */
public final class InputModification {
    private InputModification() {}

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Zeroes values inside the threshold and rescales the remainder back to full range. */
    public static double deadzone(double value, double threshold) {
        double magnitude = Math.abs(value);
        if (magnitude < threshold) return 0.0;
        return Math.signum(value) * (magnitude - threshold) / (1.0 - threshold);
    }

    /** Blends linear and cubic response; factor 0.0 is linear, 1.0 is cubic. */
    public static double expo(double value, double factor) {
        return factor * value * value * value + (1.0 - factor) * value;
    }

    public static DrivePowers scale(DrivePowers powers, double factor) {
        return new DrivePowers(powers.forward() * factor, powers.strafe() * factor, powers.turn() * factor);
    }

    /** Rate-limits a signal so commanded power cannot jump faster than {@code maxRate} per second. */
    public static final class SlewLimiter {
        private final double maxRate;
        private double value;

        public SlewLimiter(double maxRate) {
            this.maxRate = maxRate;
        }

        public double calculate(double target, double deltaSeconds) {
            if (deltaSeconds <= 0 || Double.isInfinite(maxRate)) {
                value = target;
                return value;
            }
            double maxDelta = maxRate * deltaSeconds;
            value += clamp(target - value, -maxDelta, maxDelta);
            return value;
        }

        public void reset() {
            value = 0.0;
        }
    }
}
