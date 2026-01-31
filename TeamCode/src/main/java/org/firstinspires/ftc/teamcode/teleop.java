package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;

@TeleOp(name="teleop", group="Linear OpMode")

public class teleop extends LinearOpMode {
    private final Robot Robot = new Robot();

    double vx, vy, omega;

    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("FL lamprey", Robot.frontLeftLamprey == null ? "NULL" : "OK");
        telemetry.addData("FR lamprey", Robot.frontRightLamprey == null ? "NULL" : "OK");
        telemetry.addData("BL lamprey", Robot.backLeftLamprey == null ? "NULL" : "OK");
        telemetry.addData("BR lamprey", Robot.backRightLamprey == null ? "NULL" : "OK");
        telemetry.update();
        sleep(3000);

        final SwerveModule fl = new SwerveModule(Robot.frontLeft, Robot.frontLeftServo, Robot.frontLeftLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule fr = new SwerveModule(Robot.frontRight, Robot.frontRightServo, Robot.frontRightLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule bl = new SwerveModule(Robot.backLeft, Robot.backLeftServo, Robot.backLeftLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule br = new SwerveModule(Robot.backRight, Robot.backRightServo, Robot.backRightLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveDrive SwerveDrive = new SwerveDrive(fl, fr, bl, br, 1, 1);

        Robot.shooting = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        Robot.runtime.reset();

        while (opModeIsActive()) {
            vx = gamepad1.left_stick_x;
            vy = -gamepad1.left_stick_y;
            omega = gamepad1.right_stick_x;
            SwerveDrive.drive(vx, vy, omega);

            if (gamepad1.a) {
                if (Robot.intaking == false) {
                    Robot.intake.setPower(1);
                    Robot.intaking = true;
                } else {
                    Robot.intake.setPower(0);
                    Robot.intaking = false;
                }
            }
                if (gamepad1.dpad_up) {
                    if (Robot.shooting == false) {
                        Robot.shooter.setPower(1);
                        Robot.shooting = true;
                    } else {
                        Robot.shooter.setPower(0);
                        Robot.shooting = false;
                    }
                } else if (gamepad1.dpad_right) {
                    if (Robot.shooting == false) {
                        Robot.shooter.setPower(0.8);
                        Robot.shooting = true;
                    } else {
                        Robot.shooter.setPower(0);
                        Robot.shooting = false;
                    }
                } else if (gamepad1.dpad_down) {
                    if (Robot.shooting == false) {
                        Robot.shooter.setPower(0.6);
                        Robot.shooting = true;
                    } else {
                        Robot.shooter.setPower(0);
                        Robot.shooting = false;
                    }
                } else if (gamepad1.dpad_left) {
                    if (Robot.shooting == false) {
                        Robot.shooter.setPower(0.3);
                        Robot.shooting = true;
                    } else {
                        Robot.shooter.setPower(0);
                        Robot.shooting = false;
                    }
                }

                telemetry.addData("FL angle", fl.getCurrentAngleDeg());
                telemetry.addData("FR angle", fr.getCurrentAngleDeg());
                telemetry.addData("BL angle", bl.getCurrentAngleDeg());
                telemetry.addData("BR angle", br.getCurrentAngleDeg());
                telemetry.addData("FR angle", fr.getCurrentAngleDeg());
                telemetry.addData("BL angle", bl.getCurrentAngleDeg());
                telemetry.addData("BR angle", br.getCurrentAngleDeg());
                telemetry.update();
        }
    }
}

