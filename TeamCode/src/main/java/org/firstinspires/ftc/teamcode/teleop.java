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

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        Robot.runtime.reset();

        while (opModeIsActive()) {
            vx = gamepad1.left_stick_x;
            vy = -gamepad1.left_stick_y;
            omega = gamepad1.right_stick_x;
            SwerveDrive.drive(vx, vy, omega);

//            if (gamepad1.b) {
//                Robot.intake();
//            }

            if (gamepad1.dpad_up) {
                Robot.shootHard();
            }

            if (gamepad1.dpad_down) {
                Robot.shootSoft();
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
