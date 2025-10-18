package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.lynx.LynxModule;

public class swerve {

    // ---------- robot geometry (use your half-dimensions) ----------
    private static final double LX_HALF = 100;
    private static final double LY_HALF = 100;

    // ---------- steering travel (fixed for this design) ----------
    // Total = 135°, so +/- 67.5° around "center"
    private static final double LIMIT_DEG = 67.5;

    // ---------- per-module servo calibration (MUST TUNE) ----------
    // Define how angle maps to servo position. Pick two physical safe endpoints.
    private static class ServoMap {
        final double POS_MIN, POS_MAX;     // servo positions at -67.5° and +67.5° (or your safe ends)
        final double ZERO_POS;             // servo position that corresponds to 0.0°

        ServoMap(double posMin, double zeroPos, double posMax) {
            this.POS_MIN = posMin;
            this.ZERO_POS = zeroPos;
            this.POS_MAX = posMax;
        }

        double angleToPos(double deg) { // deg in [-67.5, +67.5]
            double clamped = Math.max(-LIMIT_DEG, Math.min(LIMIT_DEG, deg));
            if (clamped >= 0) {
                return ZERO_POS + (clamped / LIMIT_DEG) * (POS_MAX - ZERO_POS);
            } else {
                return ZERO_POS + (clamped / LIMIT_DEG) * (ZERO_POS - POS_MIN);
            }
        }
    }

    // Example guesses — replace with your measured values (per wheel if needed)
    private final ServoMap mapFR = new ServoMap(0, 0.50, 1);
    private final ServoMap mapFL = new ServoMap(0, 0.50, 1);
    private final ServoMap mapBR = new ServoMap(0, 0.50, 1);
    private final ServoMap mapBL = new ServoMap(0, 0.50, 1);

    // ---------- hardware ----------
    public DcMotorEx frontRight, frontLeft, backRight, backLeft;
    public Servo frontRightServo;
    public Servo frontLeftServo;
    public Servo backRightServo;
    public Servo backLeftServo;  // regular positional servos
    private IMU imu;

    // ---------- state ----------
    private final ElapsedTime loopTimer = new ElapsedTime();
    private boolean fieldCentric = true;
    private double targetHeadingDeg = 0.0;

    // We can’t read real servo angle; track the last commanded (deg) as our “current”
    private double curFRA = 0, curFLA = 0, curBRA = 0, curBLA = 0;

    public void init(HardwareMap hw) {
        frontRight = hw.get(DcMotorEx.class, "frDrive");
        frontLeft = hw.get(DcMotorEx.class, "flDrive");
        backRight = hw.get(DcMotorEx.class, "brDrive");
        backLeft = hw.get(DcMotorEx.class, "blDrive");

        frontRightServo = hw.get(Servo.class, "frSteer");
        frontLeftServo = hw.get(Servo.class, "flSteer");
        backRightServo = hw.get(Servo.class, "brSteer");
        backLeftServo = hw.get(Servo.class, "blSteer");

        imu = hw.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));

        targetHeadingDeg = getHeadingDeg();

        for (LynxModule hub : hw.getAll(LynxModule.class)) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        loopTimer.reset();

        // Initialize servos to center (0°)
        setServoAngle(frontRightServo, mapFR, 0);
        curFRA = 0;
        setServoAngle(frontLeftServo, mapFL, 0);
        curFLA = 0;
        setServoAngle(backRightServo, mapBR, 0);
        curBRA = 0;
        setServoAngle(backLeftServo, mapBL, 0);
        curBLA = 0;

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setFieldCentric(boolean enabled) {
        fieldCentric = enabled;
    }

    /**
     * Call each loop; LY forward(+), LX left(+), RX CCW(+). No drive-speed flipping; limited steering.
     */
    public void swerve_drive(double LY, double LX, double RX) {
        double dt = Math.max(1e-3, loopTimer.seconds());
        loopTimer.reset();

        double headingDeg = getHeadingDeg();

        // Heading hold: if driver turns, follow; otherwise hold heading with a tiny P (no D/I needed here)
        boolean driverTurning = Math.abs(RX) > 0.05;
        if (driverTurning) targetHeadingDeg = headingDeg;
        double omegaHold = driverTurning ? 0.0 : 0.08 * shortestDeg(targetHeadingDeg - headingDeg);
        double omega = RX + omegaHold;

        // Field-centric (optional)
        double vx = LX, vy = LY; // robot frame: vy=fwd, vx=left
        if (fieldCentric) {
            double ch = Math.cos(Math.toRadians(headingDeg));
            double sh = Math.sin(Math.toRadians(headingDeg));
            double fwd = LY * ch + LX * sh;
            double left = -LY * sh + LX * ch;
            vy = fwd;
            vx = left;
        }

        // Kinematics
        double xFR = LX_HALF, yFR = LY_HALF;
        double xFL = -LX_HALF, yFL = LY_HALF;
        double xBR = LX_HALF, yBR = -LY_HALF;
        double xBL = -LX_HALF, yBL = -LY_HALF;

        double vxFR = vx - omega * yFR, vyFR = vy + omega * xFR;
        double vxFL = vx - omega * yFL, vyFL = vy + omega * xFL;
        double vxBR = vx - omega * yBR, vyBR = vy + omega * xBR;
        double vxBL = vx - omega * yBL, vyBL = vy + omega * xBL;

        double FRS = Math.hypot(vxFR, vyFR);
        double FLS = Math.hypot(vxFL, vyFL);
        double BRS = Math.hypot(vxBR, vyBR);
        double BLS = Math.hypot(vxBL, vyBL);

        double FRA_des = Math.toDegrees(Math.atan2(vyFR, vxFR));
        double FLA_des = Math.toDegrees(Math.atan2(vyFL, vxFL));
        double BRA_des = Math.toDegrees(Math.atan2(vyBR, vxBR));
        double BLA_des = Math.toDegrees(Math.atan2(vyBL, vxBL));

        // normalize drive speeds
        double sMax = Math.max(Math.max(FRS, FLS), Math.max(BRS, BLS));
        if (sMax > 1.0) {
            FRS /= sMax;
            FLS /= sMax;
            BRS /= sMax;
            BLS /= sMax;
        }

        // Optimize without flip + enforce ±67.5° around current
        SteerOut FRo = optimizeLimited(curFRA, FRA_des, FRS, LIMIT_DEG);
        SteerOut FLo = optimizeLimited(curFLA, FLA_des, FLS, LIMIT_DEG);
        SteerOut BRo = optimizeLimited(curBRA, BRA_des, BRS, LIMIT_DEG);
        SteerOut BLo = optimizeLimited(curBLA, BLA_des, BLS, LIMIT_DEG);

        // Set servo positions (map angle to 0..1)
        setServoAngle(frontRightServo, mapFR, FRo.angleDeg);
        curFRA = ServoAngle(frontRightServo, mapFR, FRo.angleDeg);
        setServoAngle(frontLeftServo, mapFL, FLo.angleDeg);
        curFLA = ServoAngle(frontLeftServo, mapFL, FLo.angleDeg);
        setServoAngle(backRightServo, mapBR, BRo.angleDeg);
        curBRA = ServoAngle(backRightServo, mapBR, BRo.angleDeg);
        setServoAngle(backLeftServo, mapBL, BLo.angleDeg);
        curBLA = ServoAngle(backLeftServo, mapBL, BLo.angleDeg);

        // Drive (no flip)
        frontRight.setPower(FRo.wheelSpeed);
        frontLeft.setPower(FLo.wheelSpeed);
        backRight.setPower(BRo.wheelSpeed);
        backLeft.setPower(BLo.wheelSpeed);
    }

    // ---------- helpers (Java 8 compatible) ----------
    private static class SteerOut {
        final double angleDeg;
        final double wheelSpeed;

        SteerOut(double angleDeg, double wheelSpeed) {
            this.angleDeg = angleDeg;
            this.wheelSpeed = wheelSpeed;
        }
    }

    // limit-aware optimizer: NO 180° flip; clamp to ±limitDeg and scale speed if clamped
    private static SteerOut optimizeLimited(double currentDeg, double desiredDeg,
                                            double speed, double limitDeg) {
        double delta = shortestDeg(desiredDeg - currentDeg);   // (-180, 180]
        // no flip
        double clamped = Math.max(-limitDeg, Math.min(limitDeg, delta));
        double excess = Math.abs(delta) - limitDeg;
        if (excess > 0) {
            double scale = Math.max(0, Math.cos(Math.toRadians(excess)));
            speed *= scale;
        }
        double outAngle = normDeg(currentDeg + clamped);
        return new SteerOut(outAngle, speed);
    }

    private static double shortestDeg(double a) {
        double x = ((a + 180) % 360 + 360) % 360 - 180;
        return (x == -180) ? 180 : x;
    }

    private static double normDeg(double a) {
        double x = ((a + 180) % 360 + 360) % 360 - 180;
        return (x == -180) ? 180 : x;
    }

    private void setServoAngle(Servo servo, ServoMap map, double angleDeg) {
        double pos = map.angleToPos(Math.max(-LIMIT_DEG, Math.min(LIMIT_DEG, angleDeg)));
        servo.setPosition(Math.max(0.0, Math.min(1.0, pos)));
    }

    private double ServoAngle(Servo servo, ServoMap map, double angleDeg) {
        double clamped = Math.max(-LIMIT_DEG, Math.min(LIMIT_DEG, angleDeg));
        double pos = map.angleToPos(clamped);
        servo.setPosition(Math.max(0.0, Math.min(1.0, pos)));
        return clamped;
    }

    private double getHeadingDeg() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
