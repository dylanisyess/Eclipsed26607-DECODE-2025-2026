package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.swerve.SwerveModuleState;

@Autonomous(name="autoBackLeft", group="Linear OpMode")

public class autoBackLeft extends LinearOpMode {
    private final Robot Robot = new Robot();
    double timer;

    double vx, vy, omega;

    public void runOpMode() {
        Robot.init(hardwareMap);
        Robot.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        final SwerveModule fl = new SwerveModule(Robot.frontLeft, Robot.frontLeftServo, Robot.frontLeftLamprey, 0.0025, 0, 0.0004, -2);
        final SwerveModule fr = new SwerveModule(Robot.frontRight, Robot.frontRightServo, Robot.frontRightLamprey, 0.0025, 0, 0.0004, -2);
        final SwerveModule bl = new SwerveModule(Robot.backLeft, Robot.backLeftServo, Robot.backLeftLamprey, 0.0025, 0, 0.0004, 2);
        final SwerveModule br = new SwerveModule(Robot.backRight, Robot.backRightServo, Robot.backRightLamprey, 0.0025, 0, 0.0004, 0);
        final SwerveDrive SwerveDrive = new SwerveDrive(fl, fr, bl, br, 1, 1);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        timer = 0;
        SwerveModuleState move0align = new SwerveModuleState(0, 0);
        resetRuntime();
        while (timer < 2) {
            fl.setDesiredState(move0align);
            fr.setDesiredState(move0align);
            bl.setDesiredState(move0align);
            br.setDesiredState(move0align);
            timer = getRuntime();
        }
        SwerveModuleState move0 = new SwerveModuleState(0.8, 0);
        timer = 0;
        resetRuntime();
        while (timer < 2.8) {
            fl.setDesiredState(move0);
            fr.setDesiredState(move0);
            bl.setDesiredState(move0);
            br.setDesiredState(move0);
            timer = getRuntime();
        }
        SwerveModuleState move0stop = new SwerveModuleState(0, 0);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move0stop);
            fr.setDesiredState(move0stop);
            bl.setDesiredState(move0stop);
            br.setDesiredState(move0stop);
            timer = getRuntime();
        }

        timer = 0;
        SwerveModuleState move1align = new SwerveModuleState(0, 0);
        resetRuntime();
        while (timer < 1) {
            fl.setDesiredState(move1align);
            fr.setDesiredState(move1align);
            bl.setDesiredState(move1align);
            br.setDesiredState(move1align);
            timer = getRuntime();
        }
        SwerveModuleState move1R = new SwerveModuleState(0.8, 0);
        SwerveModuleState move1L = new SwerveModuleState(-0.8, 0);
        timer = 0;
        resetRuntime();
        while (timer < 0.45) {
            fl.setDesiredState(move1L);
            fr.setDesiredState(move1R);
            bl.setDesiredState(move1L);
            br.setDesiredState(move1R);
            timer = getRuntime();
        }
        SwerveModuleState move1stop = new SwerveModuleState(0, 0);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move1stop);
            fr.setDesiredState(move1stop);
            bl.setDesiredState(move1stop);
            br.setDesiredState(move1stop);
            timer = getRuntime();
        }

//        shoot
        Robot.shooter.setPower(0.75);
        timer = 0;
        resetRuntime();
        while (timer < 2) {
            fl.setDesiredState(move1stop);
            fr.setDesiredState(move1stop);
            bl.setDesiredState(move1stop);
            br.setDesiredState(move1stop);
            timer = getRuntime();
        }
        Robot.intake.setPower(1);
        timer = 0;
        resetRuntime();
        while (timer < 3) {
            fl.setDesiredState(move1stop);
            fr.setDesiredState(move1stop);
            bl.setDesiredState(move1stop);
            br.setDesiredState(move1stop);
            timer = getRuntime();
        }
        Robot.shooter.setPower(0);
        Robot.intake.setPower(0);

        SwerveModuleState move2align = new SwerveModuleState(0, 45);
        timer = 0;
        resetRuntime();
        while (timer < 3) {
            fl.setDesiredState(move2align);
            fr.setDesiredState(move2align);
            bl.setDesiredState(move2align);
            br.setDesiredState(move2align);
            timer = getRuntime();
        }
        SwerveModuleState move2 = new SwerveModuleState(-0.8, 45);
        timer = 0;
        resetRuntime();
        while (timer < 2) {
            fl.setDesiredState(move2);
            fr.setDesiredState(move2);
            bl.setDesiredState(move2);
            br.setDesiredState(move2);
            timer = getRuntime();
        }
        SwerveModuleState move2stop = new SwerveModuleState(0, 45);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move2stop);
            fr.setDesiredState(move2stop);
            bl.setDesiredState(move2stop);
            br.setDesiredState(move2stop);
            timer = getRuntime();
        }

        SwerveModuleState move3align = new SwerveModuleState(0, 0);
        timer = 0;
        resetRuntime();
        while (timer < 1) {
            fl.setDesiredState(move3align);
            fr.setDesiredState(move3align);
            bl.setDesiredState(move3align);
            br.setDesiredState(move3align);
            timer = getRuntime();
        }
        SwerveModuleState move3L = new SwerveModuleState(-0.8, 0);
        SwerveModuleState move3R = new SwerveModuleState(0.8, 0);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move3L);
            fr.setDesiredState(move3R);
            bl.setDesiredState(move3L);
            br.setDesiredState(move3R);
            timer = getRuntime();
        }
        SwerveModuleState move3stop = new SwerveModuleState(0, 0);
        timer = 0;
        resetRuntime();
        while (timer < 0.5) {
            fl.setDesiredState(move3stop);
            fr.setDesiredState(move3stop);
            bl.setDesiredState(move3stop);
            br.setDesiredState(move3stop);
            timer = getRuntime();
        }
    }
}

