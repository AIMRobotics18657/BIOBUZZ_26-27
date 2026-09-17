package org.firstinspires.ftc.teamcode.vision;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.config.ConfigInfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Limelight 3A wrapper. Pure data: it reads the camera and exposes results, and never touches the
 * follower. Call {@link #start()} before the loop and one of the update methods once per loop.
 */
public class LimelightCamera {
    private final Limelight3A limelight;
    private LLResult result;
    private int pipeline = ConfigInfo.Vision.DEFAULT_PIPELINE;

    public LimelightCamera(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, ConfigInfo.Vision.LIMELIGHT);
        limelight.setPollRateHz(ConfigInfo.Vision.POLL_RATE_HZ);
        limelight.pipelineSwitch(pipeline);
    }

    public void start() {
        limelight.start();
    }

    public void stop() {
        limelight.stop();
    }

    /** Refreshes the cached result. Crosshair and detector data only; MegaTag2 pose needs a heading. */
    public void update() {
        LLResult latest = limelight.getLatestResult();
        result = usable(latest) ? latest : null;
    }

    /** Also feeds the robot heading so MegaTag2 can resolve tag ambiguity. Prefer this on a live robot. */
    public void update(double headingRadians) {
        limelight.updateRobotOrientation(Math.toDegrees(headingRadians));
        update();
    }

    private static boolean usable(LLResult candidate) {
        return candidate != null
                && candidate.isValid()
                && candidate.getStaleness() <= ConfigInfo.Vision.MAX_STALENESS_MS;
    }

    public boolean hasTarget() {
        return result != null;
    }

    public boolean isConnected() {
        return limelight.isConnected();
    }

    public LLStatus status() {
        return limelight.getStatus();
    }

    public int pipeline() {
        return pipeline;
    }

    public void pipeline(int index) {
        if (index == pipeline) return;
        pipeline = index;
        limelight.pipelineSwitch(index);
    }

    /** Horizontal offset to the target in degrees, 0 when there is no target. */
    public double tx() {
        return result == null ? 0.0 : result.getTx();
    }

    /** Vertical offset to the target in degrees, 0 when there is no target. */
    public double ty() {
        return result == null ? 0.0 : result.getTy();
    }

    /** Target area as a percentage of the image, 0 when there is no target. */
    public double ta() {
        return result == null ? 0.0 : result.getTa();
    }

    public long staleness() {
        return result == null ? -1 : result.getStaleness();
    }

    public List<LLResultTypes.FiducialResult> fiducials() {
        return result == null ? Collections.emptyList() : result.getFiducialResults();
    }

    public List<LLResultTypes.DetectorResult> detections() {
        return result == null ? Collections.emptyList() : result.getDetectorResults();
    }

    public List<LLResultTypes.ColorResult> colors() {
        return result == null ? Collections.emptyList() : result.getColorResults();
    }

    /**
     * MegaTag2 field pose converted to Pedro's frame: inches, origin shifted off the field centre by
     * {@link ConfigInfo.Vision#FIELD_ORIGIN_OFFSET_X}. Only meaningful if a heading was supplied via
     * {@link #update(double)} and the field map matches the season.
     */
    public Optional<Pose> fieldPose() {
        if (result == null) return Optional.empty();

        Pose3D botpose = result.getBotpose_MT2();
        if (botpose == null) return Optional.empty();

        Position position = botpose.getPosition().toUnit(DistanceUnit.INCH);
        double heading = botpose.getOrientation().getYaw(AngleUnit.RADIANS);

        return Optional.of(new Pose(
                position.x + ConfigInfo.Vision.FIELD_ORIGIN_OFFSET_X,
                position.y + ConfigInfo.Vision.FIELD_ORIGIN_OFFSET_Y,
                heading));
    }

    /** How many tags contributed to the current pose estimate. More tags means a better estimate. */
    public int tagCount() {
        return result == null ? 0 : result.getBotposeTagCount();
    }
}
