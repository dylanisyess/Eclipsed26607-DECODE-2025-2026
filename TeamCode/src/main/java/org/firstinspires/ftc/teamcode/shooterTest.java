package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp(name="ShooterTest", group="Linear OpMode")
// @Disabled
public class shooterTest extends LinearOpMode {
    public DcMotor shooter;
    boolean shooting;

    public void runOpMode() {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooting = false;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                shootHard();
            }

            else if (gamepad1.dpad_right) {
                shootMiddleHard();
            }

            else if (gamepad1.dpad_down) {
                shootMiddleSoft();
            }

            else if (gamepad1.dpad_left) {
                shootSoft();
            }

            else {
                shooter.setPower(0);
            }
        }
    }

    public void shootHard() {
        if (shooting == false) {
            shooter.setPower(1);
            shooting = true;
        }
        else {
            shooter.setPower(0);
            shooting = false;
        }
    }

    public void shootSoft() {
        if (shooting == false) {
            shooter.setPower(0.3);
            shooting = true;
        }
        else {
            shooter.setPower(0);
            shooting = false;
        }
    }

    public void shootMiddleHard() {
        if (shooting == false) {
            shooter.setPower(0.8);
            shooting = true;
        }
        else {
            shooter.setPower(0);
            shooting = false;
        }
    }

    public void shootMiddleSoft() {
        if (shooting == false) {
            shooter.setPower(0.6);
            shooting = true;
        }
        else {
            shooter.setPower(0);
            shooting = false;
        }
    }
}

