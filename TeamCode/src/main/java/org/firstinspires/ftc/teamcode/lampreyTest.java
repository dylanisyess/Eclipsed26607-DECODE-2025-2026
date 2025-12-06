package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;


@TeleOp(name="LampreyTest", group="Linear OpMode")
// @Disabled
public class lampreyTest extends LinearOpMode {

    public AnalogInput frontLeftLamprey;
    double x;
    double maxVoltage;
    public void runOpMode() {
        frontLeftLamprey = hardwareMap.get(AnalogInput.class, "frontLeftLamprey");
        maxVoltage = frontLeftLamprey.getMaxVoltage();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double currentAngle = getCurrentAngleDeg();
            telemetry.addData("angle", "currentAngle");
        }
    }

    private double getCurrentAngleDeg() {
        double volts = frontLeftLamprey.getVoltage();
        double angle = (volts / maxVoltage) * 360.0;  // 0–360 from Lamprey

//        angle -= angleOffsetDeg;                      // apply per-module offset
        angle = (angle % 360 + 360) % 360;            // wrap to 0–360

        return angle;
    }

}