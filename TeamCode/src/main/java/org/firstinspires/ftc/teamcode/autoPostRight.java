package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.swerve.SwerveModuleState;

@Autonomous(name="autoPostRight", group="Linear OpMode")

public class autoPostRight extends LinearOpMode {
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
        sleep(2000);
        Robot.intake.setPower(0.7);
        sleep(3000);
        Robot.shooter.setPower(0);
        Robot.intake.setPower(0);

        SwerveModuleState move0align = new SwerveModuleState(0, 215);
        timer = 0;
        resetRuntime();
        while (timer < 2) {
            fl.setDesiredState(move0align);
            fr.setDesiredState(move0align);
            bl.setDesiredState(move0align);
            br.setDesiredState(move0align);
            timer = getRuntime();
        }
        SwerveModuleState move0 = new SwerveModuleState(0.8, 215);
        timer = 0;
        resetRuntime();
        while (timer < 0.7) {
            fl.setDesiredState(move0);
            fr.setDesiredState(move0);
            bl.setDesiredState(move0);
            br.setDesiredState(move0);
            timer = getRuntime();
        }
        SwerveModuleState move0stop = new SwerveModuleState(0, 215);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move0stop);
            fr.setDesiredState(move0stop);
            bl.setDesiredState(move0stop);
            br.setDesiredState(move0stop);
            timer = getRuntime();
        }
    }
}

