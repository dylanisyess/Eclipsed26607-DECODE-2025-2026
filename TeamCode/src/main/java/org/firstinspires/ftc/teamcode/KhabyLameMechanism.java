package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name="Test", group="Linear OpMode")
// @Disabled
public class KhabyLameMechanism extends LinearOpMode {

    public DcMotor KhabyLameHands;
    double power;

    //    @Override
    public void runOpMode() {
        KhabyLameHands = hardwareMap.get(DcMotor.class, "frontLeft");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START).
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
           power = gamepad1.left_stick_y;
           KhabyLameHands.setPower(power);


        }

    }

}