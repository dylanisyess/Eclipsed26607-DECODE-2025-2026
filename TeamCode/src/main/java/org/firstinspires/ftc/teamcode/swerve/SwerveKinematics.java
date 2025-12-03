package org.firstinspires.ftc.teamcode.swerve;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public class SwerveKinematics {
    double trackLength;
    double trackWidth;
    double L = 200;
    double W = 200;
    double R = 282.84;
    double FWD, STR, RCW, A, B, C, D, FRA, FLA, BLA, BRA, FRS, FLS, BLS, BRS, max;
    boolean neg;

    public SwerveKinematics (double trackLength, double trackWidth){
        this.trackLength = trackLength;
        this.trackWidth = trackWidth;
    }

    public SwerveModuleState[] toModuleStates (double vx, double vy, double omega) {

        FWD = vy;
        STR = vx;
        RCW = omega;
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
            FRA = FRA * -1;
            FRS = FRS * -1;
            neg = true;
        }
        if (FLA < 0) {
            FLA = FLA * -1;
            FLS = FLS * -1;
        }
        if (BLA < 0) {
            BLA = BLA * -1;
            BLS = BLS * -1;
        }
        if (BRA < 0) {
            BRA = BRA * -1;
            BRS = BRS * -1;
        }

        FRA = (FRA)/180.0;
        FLA = (FLA)/180.0;
        BLA = (BLA)/180.0;
        BRA = (BRA)/180.0;


        SwerveModuleState fl = new SwerveModuleState(FLS, FLA);
        SwerveModuleState fr = new SwerveModuleState(FRS, FRA);
        SwerveModuleState bl = new SwerveModuleState(BLS, BLA);
        SwerveModuleState br = new SwerveModuleState(BRS, BRA);

        return new SwerveModuleState[] {fl, fr, bl, br};
    }

}
