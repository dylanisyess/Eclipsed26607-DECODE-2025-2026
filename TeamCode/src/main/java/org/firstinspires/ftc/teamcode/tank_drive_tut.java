package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="tankDriveTut", group="Linear Opmode")

public class tank_drive_tut extends LinearOpMode {
    public void runOpMode() {
        DcMotor frontLeft, frontRight, backLeft, backRight;
        double LY;
        double RX;

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        waitForStart();

        while (opModeIsActive()) {
            LY = gamepad1.left_stick_y;
            RX = gamepad1.right_stick_x;

            frontLeft.setPower(LY + RX);
            frontRight.setPower(LY - RX);
            backLeft.setPower(LY + RX);
            backRight.setPower(LY - RX);
        }
    }
}
