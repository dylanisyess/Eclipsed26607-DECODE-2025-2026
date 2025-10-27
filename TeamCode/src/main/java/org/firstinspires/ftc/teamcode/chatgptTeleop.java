package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="chatgpt teleop", group="Linear OpMode")
// @Disabled
public class chatgptTeleop extends LinearOpMode {
    private final chatgptSwerve Robot = new chatgptSwerve();

    private static double dead(double v, double eps) {
        return Math.abs(v) < eps ? 0.0 : v;
    }

        @Override
    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        double prev_LX, prev_LY, prev_RX, LX, LY, RX;

        // Wait for the game to start (driver presses START).
        waitForStart();

        Robot.frontLeftServo.setPosition(0.5);
        Robot.frontRightServo.setPosition(0.5);
        Robot.backLeftServo.setPosition(0.5);
        Robot.backRightServo.setPosition(0.5);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            LX = dead(gamepad1.left_stick_x,0.05);
            LY = dead(-gamepad1.left_stick_y, 0.05);
            RX = dead(gamepad1.right_stick_x, 0.05);

            Robot.swerve_drive(LY, LX, RX);

            telemetry.addData("LY/LX/RX", "%.2f  %.2f  %.2f", LY, LX, RX);
            telemetry.update();
            idle();
        }
    }
}