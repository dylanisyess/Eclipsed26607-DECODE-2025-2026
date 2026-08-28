package org.firstinspires.ftc.teamcode;

import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="mecanum_tut", group="Linear Opmode")

public class mecanum_tut extends LinearOpMode {
    public void runOpMode() {
        DcMotor frontLeft, frontRight, backLeft, backRight;
        double LY;
        double RX;
        double LX;
        double d;

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        waitForStart();

        while (opModeIsActive()) {
            LY = gamepad1.left_stick_y;
            LX = gamepad1.left_stick_x;
            RX = gamepad1.right_stick_x;

            d = Math.max(Math.abs(LY) + Math.abs(LX) + Math.abs(RX), 1);
            frontLeft.setPower((LY + LX + RX) / d);
            frontRight.setPower((LY - LX - RX) / d);
            backLeft.setPower((LY - LX + RX) / d);
            backRight.setPower((LY + LX - RX / d));
        }
    }
}
