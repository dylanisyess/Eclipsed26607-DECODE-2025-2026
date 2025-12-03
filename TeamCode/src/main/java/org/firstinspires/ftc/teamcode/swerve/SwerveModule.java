package org.firstinspires.ftc.teamcode.swerve;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.util.PID;

public class SwerveModule {
    private DcMotorEx drive;
    private CRServo steer;
    private AnalogInput lamprey;
    private PID PID;
    private double maxVoltage;
    private double angleOffsetDeg;

    public SwerveModule(DcMotorEx drive,
                        CRServo steer,
                        AnalogInput lamprey,
                        double kP, double kI, double kD,
                        double angleOffsetDeg) {

        this.drive = drive;
        this.steer = steer;
        this.lamprey = lamprey;
        this.PID = new PID(kP, kI, kD);
        this.maxVoltage = lamprey.getMaxVoltage();
        this.angleOffsetDeg = angleOffsetDeg;
    }

    public void setDesiredState(SwerveModuleState state) {
        double targetSpeed = state.speed;      // from kinematics
        double targetAngle = state.angle;   // from kinematics

        double currentAngle = getCurrentAngleDeg();
        double error = angleErrorDeg(targetAngle, currentAngle);

        if (Math.abs(error) > 90.0) {
            targetAngle = (targetAngle + 180.0) % 360.0;
            targetSpeed *= -1.0;
            error = angleErrorDeg(targetAngle, currentAngle);
        }

        double steerOutput = PID.calculate(error);

        steerOutput = com.qualcomm.robotcore.util.Range.clip(steerOutput, -1.0, 1.0);

        steer.setPower(steerOutput);
        drive.setPower(targetSpeed);
    }


    private double getCurrentAngleDeg() {
        double volts = lamprey.getVoltage();
        double angle = (volts / maxVoltage) * 360.0;  // 0–360 from Lamprey

        angle -= angleOffsetDeg;                      // apply per-module offset
        angle = (angle % 360 + 360) % 360;            // wrap to 0–360

        return angle;
    }

    private double angleErrorDeg(double target, double current) {
        double error = target - current;
        error = (error + 540) % 360 - 180;  // now in (-180, 180]
        return error;
    }

}
