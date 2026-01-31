package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.swerve.SwerveModuleState;

@Autonomous(name="auto", group="Linear OpMode")

public class auto extends LinearOpMode {
    private final Robot Robot = new Robot();
    double timer;

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

        SwerveModuleState state = new SwerveModuleState(0, 30);

        fl.setDesiredState(state);
        fr.setDesiredState(state);
        bl.setDesiredState(state);
        br.setDesiredState(state);

        sleep(500);

        telemetry.addData("FL angle", fl.getCurrentAngleDeg());
        telemetry.addData("FR angle", fr.getCurrentAngleDeg());
        telemetry.addData("BL angle", bl.getCurrentAngleDeg());
        telemetry.addData("BR angle", br.getCurrentAngleDeg());
        telemetry.addData("FR angle", fr.getCurrentAngleDeg());
        telemetry.addData("BL angle", bl.getCurrentAngleDeg());
        telemetry.addData("BR angle", br.getCurrentAngleDeg());
        telemetry.update();

        SwerveModuleState state2 = new SwerveModuleState(-0.5, 30);
        SwerveModuleState state2L = new SwerveModuleState(-0.5, 30);

        resetRuntime();
        while (timer < 7) {
            fl.setDesiredState(state2L);
            fr.setDesiredState(state2);
            bl.setDesiredState(state2L);
            br.setDesiredState(state2);
            timer = getRuntime();
        }

        SwerveModuleState state3 = new SwerveModuleState(0, 30);

        fl.setDesiredState(state3);
        fr.setDesiredState(state3);
        bl.setDesiredState(state3);
        br.setDesiredState(state3);

    }
}

