package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="ShooterTest", group="Linear OpMode")
// @Disabled
public class shooterTest extends LinearOpMode {
    public DcMotor shooter;
    boolean shooting;


    double shootPower;


    public void runOpMode() {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        shooting = false;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (shooting == false) {
                    shooter.setPower(1);
                    shooting = true;
                } else {
                    shooter.setPower(0);
                    shooting = false;
                }
            } else if (gamepad1.dpad_right) {
                if (shooting == false) {
                    shooter.setPower(0.8);
                    shooting = true;
                } else {
                    shooter.setPower(0);
                    shooting = false;
                }
            } else if (gamepad1.dpad_down) {
                if (shooting == false) {
                    shooter.setPower(0.6);
                    shooting = true;
                } else {
                    shooter.setPower(0);
                    shooting = false;
                }
            } else if (gamepad1.dpad_left) {
                if (shooting == false) {
                    shooter.setPower(0.3);
                    shooting = true;
                } else {
                    shooter.setPower(0);
                    shooting = false;
                }
            }


        }
    }
}