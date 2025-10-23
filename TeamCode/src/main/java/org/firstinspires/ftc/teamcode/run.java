package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Teleop_final", group="Linear OpMode")
// @Disabled
public class run extends LinearOpMode {
    private final Robot Robot = new Robot();

    //    @Override
    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        double prev_X, prev_Y, prev_X2, LX, LY, RX; //X2 is the right joystick, others are left

        // Wait for the game to start (driver presses START).
        waitForStart();
        Robot.runtime.reset();

        Robot.frontLeftServo.setPosition(0.5);
        Robot.frontRightServo.setPosition(0.5);
        Robot.backLeftServo.setPosition(0.5);
        Robot.backRightServo.setPosition(0.5);

        prev_X = 0;
        prev_Y = 0;
        prev_X2 = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            LX = gamepad1.left_stick_x;
            LY = gamepad1.left_stick_y;
            RX = gamepad1.right_stick_x;

            if (LX != 0 && LY != 0 && RX != 0 || LX > prev_X + 0.1 || LX < prev_X - 0.1 || LY > prev_Y + 0.1 || LY < prev_Y - 0.1 || RX > prev_X2 + 0.1 || RX < prev_X2 - 0.1 ) {
                Robot.swerve_drive(LY, LX, RX);
            } else if (LX == 0 && LY == 0 && RX == 0) {
                Robot.frontLeftServo.setPosition(0.5);
                Robot.frontRightServo.setPosition(0.5);
                Robot.backLeftServo.setPosition(0.5);
                Robot.backRightServo.setPosition(0.5);
                Robot.frontLeft.setPower(0);
                Robot.frontRight.setPower(0);
                Robot.backLeft.setPower(0);
                Robot.backRight.setPower(0);
            }

//            telemetry.addData("Front left Position", frontLeft.getPosition());
//            telemetry.addData("Right Servo Position", rightservo.getPosition());
//            telemetry.addData("moving?", moving );
//            telemetry.update();
            idle();
        }
    }
}