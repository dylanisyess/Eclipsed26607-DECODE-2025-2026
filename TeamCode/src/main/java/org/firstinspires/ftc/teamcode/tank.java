package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.swerve.SwerveModule;

import java.util.Arrays;

@TeleOp(name="tank", group="Linear OpMode")

public class tank extends LinearOpMode {
    public void runOpMode() {
        DcMotor frontLeft, frontRight, backLeft, backRight;
//        Servo frontLeftServo, frontRightServo, backLeftServo, backRightServo;
        CRServo frontLeftServo;

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeftServo = hardwareMap.get(CRServo.class, "frontLeftServo");
//        frontRightServo = hardwareMap.get(Servo.class, "frontRightServo");
//        backLeftServo = hardwareMap.get(Servo.class, "backLeftServo");
//        backRightServo = hardwareMap.get(Servo.class, "backRightServo");

        double LY = 0, RY = 0;

//        frontLeftServo.setPosition(0.5);
//        frontRightServo.setPosition(0.5);
//        backLeftServo.setPosition(0.5);
//        backRightServo.setPosition(0.5);

        waitForStart();

        while (opModeIsActive()) {

            LY = gamepad1.left_stick_y;
            RY = gamepad1.right_stick_y;

            frontLeft.setPower(LY);
            backLeft.setPower(LY);
            frontRight.setPower(RY);
            backRight.setPower(-RY);
        }
    }
}