package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;


@TeleOp(name="LampreyTest", group="Linear OpMode")
// @Disabled
public class lampreyTest extends LinearOpMode {

    public AnalogInput frontLeftLamprey, frontRightLamprey, backLeftLamprey, backRightLamprey;
    double x;
    double flcurrentAngle, frcurrentAngle, blcurrentAngle, brcurrentAngle;
    double maxVoltage;
    public void runOpMode() {
        frontLeftLamprey = hardwareMap.get(AnalogInput.class, "frontLeftLamprey");
        frontRightLamprey = hardwareMap.get(AnalogInput.class, "frontRightLamprey");
        backLeftLamprey = hardwareMap.get(AnalogInput.class, "backLeftLamprey");
        backRightLamprey = hardwareMap.get(AnalogInput.class, "backRightLamprey");
        maxVoltage = frontLeftLamprey.getMaxVoltage();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            flcurrentAngle = flgetCurrentAngleDeg();
            frcurrentAngle = frgetCurrentAngleDeg();
            blcurrentAngle = blgetCurrentAngleDeg();
            brcurrentAngle = brgetCurrentAngleDeg();
            telemetry.addData("front left volts", frontLeftLamprey.getVoltage());
            telemetry.addData("front right volts", frontRightLamprey.getVoltage());
            telemetry.addData("back left volts", backLeftLamprey.getVoltage());
            telemetry.addData("back right volts", backRightLamprey.getVoltage());
            telemetry.addData("front left angle", flcurrentAngle);
            telemetry.addData("front right angle", frcurrentAngle);
            telemetry.addData("back left angle", blcurrentAngle);
            telemetry.addData("back right angle", brcurrentAngle);
            telemetry.update();
        }
    }

    private double flgetCurrentAngleDeg() {
        double volts = frontLeftLamprey.getVoltage();
        double angle = (volts / 2.164) * 360.0;  // 0–360 from Lamprey

        angle = (angle % 360 + 360) % 360;

        return angle;
    }

    private double frgetCurrentAngleDeg() {
        double volts = frontRightLamprey.getVoltage();
        double angle = (volts / 2.164) * 360.0;  // 0–360 from Lamprey

        angle = (angle % 360 + 360) % 360;

        return angle;
    }

    private double blgetCurrentAngleDeg() {
        double volts = backLeftLamprey.getVoltage();
        double angle = (volts / 2.164) * 360.0;  // 0–360 from Lamprey

        angle = (angle % 360 + 360) % 360;

        return angle;
    }

    private double brgetCurrentAngleDeg() {
        double volts = backRightLamprey.getVoltage();
        double angle = (volts / 2.164) * 360.0;  // 0–360 from Lamprey

        angle = (angle % 360 + 360) % 360;

        return angle;
    }

}