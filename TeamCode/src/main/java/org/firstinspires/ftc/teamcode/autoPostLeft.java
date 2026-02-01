package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.swerve.SwerveModuleState;

@Autonomous(name="autoPostLeft", group="Linear OpMode")

public class autoPostLeft extends LinearOpMode {
    private final Robot Robot = new Robot();
    double timer;

    double vx, vy, omega;

    public void runOpMode() {
        Robot.init(hardwareMap);

        final SwerveModule fl = new SwerveModule(Robot.frontLeft, Robot.frontLeftServo, Robot.frontLeftLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule fr = new SwerveModule(Robot.frontRight, Robot.frontRightServo, Robot.frontRightLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule bl = new SwerveModule(Robot.backLeft, Robot.backLeftServo, Robot.backLeftLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveModule br = new SwerveModule(Robot.backRight, Robot.backRightServo, Robot.backRightLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveDrive SwerveDrive = new SwerveDrive(fl, fr, bl, br, 1, 1);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        Robot.shooter.setPower(0.6);
        sleep(1000);
        Robot.intake.setPower(1);
        sleep(3000);
        Robot.shooter.setPower(0);
        Robot.intake.setPower(0);
    }
}

