package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp(name="ShooterTest", group="Linear OpMode")
// @Disabled
public class shooterTest extends LinearOpMode {
    public DcMotorEx shooter;

    public void runOpMode() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                shootHard();
            }

            if (gamepad1.dpad_down) {
                shootSoft();
            }
            telemetry.addData("angle", "currentAngle");
        }
    }

    public void shootHard() {
        shooter.setTargetPosition(96);
        shooter.setPower(1);
        while (shooter.isBusy()) {

        }
        shooter.setPower(0);
        shooter.setTargetPosition(0);
        shooter.setPower(0.5);
        while (shooter.isBusy()) {

        }
        shooter.setPower(0);
    }

    public void shootSoft() {
        shooter.setTargetPosition(96);
        shooter.setPower(0.5);
        while (shooter.isBusy()) {

        }
        shooter.setPower(0);
        shooter.setTargetPosition(0);
        shooter.setPower(0.5);
        while (shooter.isBusy()) {

        }
        shooter.setPower(0);
    }
}

