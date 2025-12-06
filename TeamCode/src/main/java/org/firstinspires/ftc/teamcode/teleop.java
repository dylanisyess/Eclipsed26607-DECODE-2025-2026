package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;

@TeleOp(name="teleop", group="Linear OpMode")

public class teleop extends LinearOpMode {
    private final Robot Robot = new Robot();
    private final SwerveModule fl = new SwerveModule(Robot.frontLeft, Robot.frontLeftServo, Robot.frontLeftLamprey, 0.01, 0, 0.0005, Robot.flOffsetDeg);
    private final SwerveModule fr = new SwerveModule(Robot.frontRight, Robot.frontRightServo, Robot.frontRightLamprey, 0.01, 0, 0.0005, Robot.frOffsetDeg);
    private final SwerveModule bl = new SwerveModule(Robot.backLeft, Robot.backLeftServo, Robot.backLeftLamprey, 0.01, 0, 0.0005, Robot.blOffsetDeg);
    private final SwerveModule br = new SwerveModule(Robot.backRight, Robot.backRightServo, Robot.backRightLamprey, 0.01, 0, 0.0005, Robot.brOffsetDeg);
    private final SwerveDrive SwerveDrive = new SwerveDrive(fl, fr, bl, br, 200, 200);
    double vx, vy, omega;

    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        Robot.runtime.reset();

        while (opModeIsActive()) {
            vx = gamepad1.left_stick_x;
            vy = gamepad1.left_stick_y;
            omega = gamepad1.right_stick_x;
            SwerveDrive.drive(vx, vy, omega);

            if (gamepad1.b) {
                Robot.intake();
            }

            if (gamepad1.dpad_up) {
                Robot.shootHard();
            }

            if (gamepad1.dpad_down) {
                Robot.shootSoft();
            }
        }
    }
}
