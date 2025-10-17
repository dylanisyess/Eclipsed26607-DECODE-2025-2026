package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name="Teleop_final", group="Linear OpMode")
// @Disabled
public class Teleop_final extends LinearOpMode {
    private final Robot Robot = new Robot();

    //    @Override
    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();
        Robot.runtime.reset();

        // Set the initial servo positions to neutral (mid-point).
        Robot.frontLeftServo.setPosition(0.5);
        Robot.frontLeftServo.setPosition(0.5);
        Robot.frontLeftServo.setPosition(0.5);
        Robot.frontLeftServo.setPosition(0.5);
        Robot.moving = false;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup variables for motor power
            boolean leftMotorForward = true;
            boolean rightMotorForward = true;
            boolean leftjoystickactive = false;
            double leftPower;
            double rightPower;
            double radius;
            radius = 1;
            rightpodposition = 1;
            leftpodposition = 1;


            // Apply deadzone for joystick X-axis
            double leftStickX = gamepad1.left_stick_x;
            double rightStickX = gamepad1.right_stick_x;
            double leftStickY = gamepad1.left_stick_y;
            double rightStickY = gamepad1.right_stick_y;
            double deadzone = 0;


            Robot.left_theta = -Math.atan2(leftStickY, leftStickX);
            Robot.right_theta = -Math.atan2(rightStickY, rightStickX);


            Robot.left_magnitude = Math.sqrt(Math.pow(leftStickY, 2.0) + Math.pow(leftStickX, 2.0));
            Robot.right_magnitude = Math.sqrt(Math.pow(rightStickY, 2.0) + Math.pow(rightStickX, 2.0));

            // avoid singular point for serve position.
            if (Robot.left_magnitude > 0.2 && ((Robot.prev_left_theta < (Robot.left_theta + 0.2)) || (Robot.prev_left_theta > (Robot.left_theta - 0.2)))) {
                Robot.left_theta = Robot.prev_left_theta;
                leftjoystickactive = true;
            } else {
                Robot.prev_left_theta = Robot.left_theta;
            }

            Robot.left_forward = 1.0;
            Robot.right_forward = 1.0;
            if (leftjoystickactive) {
                if (Robot.left_theta < 0) {
                    Robot.left_forward = -1.0;
                    Robot.left_theta = Robot.left_theta * -1;
                } else {
                    // left_theta = (left_theta - Math.PI/2) * -1 + Math.PI/2 ;
                    Robot.left_theta = Math.PI - Robot.left_theta;
                }
                if (Robot.right_theta < 0) {
                    Robot.right_forward = -1.0;
                    Robot.right_theta = Robot.right_theta * -1;
                }
                Robot.leftservo.setPosition(Robot.left_theta / Math.PI);
                // synchronzied with left joystick
                Robot.rightservo.setPosition(Robot.left_theta / Math.PI);
                // independent
                // rightservo.setPosition(right_theta / Math.PI);

                leftwheel.setPower(Robot.left_forward * Robot.left_magnitude * Robot.magnitude_gain);
                // synchronzied with left joystick
                rightwheel.setPower(Robot.left_forward * Robot.left_magnitude * Robot.magnitude_gain);
            } else if (Robot.right_magnitude > 0.2 && !leftjoystickactive) {
                Robot.magnitude_gain = 0.5;
                Robot.frontLeftServo.setPosition(0.5);
                Robot.frontRightServo.setPosition(0.5);
                Robot.backLeftServo.setPosition(0.5);
                Robot.backRightServo.setPosition(0.5);
                leftwheel.setPower(rightStickX * magnitude_gain);
                rightwheel.setPower(rightStickX * -1 * magnitude_gain);
            } else {
                leftwheel.setPower(0);
                rightwheel.setPower(0);



            // independent
            // rightwheel.setPower(right_forward*right_magnitude * magnitude_gain);


            telemetry.addData("Left Servo Position", leftservo.getPosition());
            telemetry.addData("Right Servo Position", rightservo.getPosition());
            telemetry.addData("moving?", moving );
            telemetry.update();
            idle();

//
        }

        // Telemetry to display key data


    }

}