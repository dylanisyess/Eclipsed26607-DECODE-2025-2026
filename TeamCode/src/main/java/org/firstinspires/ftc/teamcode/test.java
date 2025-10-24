package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name="Test", group="Linear OpMode")
// @Disabled
public class test extends LinearOpMode {
    private final Robot Robot = new Robot();

    //    @Override
    public void runOpMode() {
        Robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();
        Robot.runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double leftStickY = gamepad1.left_stick_y;

            if (leftStickY > 0.1) {
                Robot.frontRight.setPower(leftStickY);
            } else {
                Robot.frontRight.setPower(0);
            }

        }

    }

}