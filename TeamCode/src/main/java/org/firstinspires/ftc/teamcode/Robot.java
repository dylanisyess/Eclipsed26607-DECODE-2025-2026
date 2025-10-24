package org.firstinspires.ftc.teamcode;;

import static com.sun.tools.doclint.Entity.pi;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import static java.lang.Math.PI;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.Arrays;
import java.util.List;

public class Robot {
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public Servo frontLeftServo, frontRightServo, backLeftServo, backRightServo;
    public DigitalChannel limitSwitch;
    private List<DcMotorEx> motors;
    private Context _appContext;
    public ElapsedTime runtime = new ElapsedTime();
    double L = 200;
    double W = 200;
    double R = 282.84;
    double FWD, STR, RCW, A, B, C, D, FRA, FLA, BLA, BRA, FRS, FLS, BLS, BRS, max;
    boolean neg;


    IMU imu;
    private static final double MAX_VELOCITY = 2800d;
    private static final double COUNTS_PER_MOTOR_REV = 146.44d;    // eg: HD Hex Motor 20:1 560, core hex 288, 40:1 1120
    private static final double DRIVE_GEAR_REDUCTION = 1.33d;     // This is < 1.0 if geared UP, eg. 26d/10d
    private static final double WHEEL_DIAMETER_INCHES = 2.3622d;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265359d);
    double gripPosition, handPosition;
    int handMode;

    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        motors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);

        frontLeftServo = hardwareMap.get(Servo.class, "frontLeftServo");
        frontRightServo = hardwareMap.get(Servo.class, "frontRightServo");
        backLeftServo = hardwareMap.get(Servo.class, "backLeftServo");
        backRightServo = hardwareMap.get(Servo.class, "backRightServo");

        frontLeft.setDirection(Direction.REVERSE);
        frontRight.setDirection(Direction.FORWARD);
        backLeft.setDirection(Direction.REVERSE);
        backRight.setDirection(Direction.FORWARD);

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(LogoFacingDirection.RIGHT, UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        _appContext = hardwareMap.appContext;

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);
            motor.setMode(RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public void swerve_drive(double LY, double LX, double RX) {
        FWD = LY;
        STR = LX;
        RCW = RX;
        neg = false;

        A = STR - RCW*(L/R);
        B = STR + RCW*(L/R);
        C = FWD - RCW*(W/R);
        D = FWD + RCW*(W/R);

        FRS = sqrt((B*B)+(C*C));
        FLS = sqrt((B*B)+(D*D));
        BLS = sqrt((A*A)+(D*D));
        BRS = sqrt((A*A)+(C*C));

        FRA = atan2(B,C)*180/Math.PI;
        FLA = atan2(B,D)*180/Math.PI;
        BLA = atan2(A,D)*180/Math.PI;
        BRA = atan2(A,C)*180/Math.PI;

        if (FRA < 0) {
//            FRA =- 180;
            FRS =  FRS * -1;
            neg = true;
        }
        if (FLA < 0) {
//            FLA =- 180;
            FLS =  FLS * -1;
        }
        if (BLA < 0) {
//            BLA =- 180;
            BLS =  BLS * -1;
        }
        if (BRA < 0) {
//            BRA =- 180;
            BRS =  BRS * -1;
        }

        FRA = (FRA + 180)/360.0;
        FLA = (FLA + 180)/360.0;
        BLA = (BLA + 180)/360.0;
        BRA = (BRA + 180)/360.0;

//        max = FRS;
//        if (FLS > max) {max = FLS;}
//        if (BLS > max) {max = BLS;}
//        if (BRS > max) {max = BRS;}
//
//        FRS /= max;
//        FLS /= max;
//        BLS /= max;
//        BRS /= max;

        frontRightServo.setPosition(FRA);
        frontLeftServo.setPosition(FLA);
        backLeftServo.setPosition(BLA);
        backRightServo.setPosition(BRA);

        frontRight.setPower(FRS);
        frontLeft.setPower(FLS);
        backLeft.setPower(BLS);
        backRight.setPower(BRS);
    }

    double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }
}

//