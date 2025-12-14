package org.firstinspires.ftc.teamcode.swerve;

import static java.lang.Math.sqrt;

public class SwerveKinematics {

    // Robot geometry (same units for both: m, in, whatever)
    // L = front–back distance between module centers
    // W = left–right distance between module centers
    private final double L;
    private final double W;

    public SwerveKinematics(double trackLength, double trackWidth) {
        this.L = trackLength;
        this.W = trackWidth;
    }

    /**
     * Robot-centric kinematics.
     *
     * vx  = strafe speed  (+ right,  - left)
     * vy  = forward speed (+ forward, - backward)
     * omega = rotation rate (+ CCW,  - CW)
     *
     * Returns [FL, FR, BL, BR] module states.
     */
    public SwerveModuleState[] toModuleStates(double vx, double vy, double omega) {

        // Combine translation and rotation for each corner
        double R = sqrt(L*L + W*W);
        double A = vx - omega * (L / R);
        double B = vx + omega * (L / R);
        double C = vy - omega * (W / R);
        double D = vy + omega * (W / R);

        // Velocity vectors for each module
        // (x = strafe component, y = forward component)
        SwerveModuleState fl = new SwerveModuleState(
                Math.hypot(B, D),
                Math.toDegrees(Math.atan2(B, D))
        );
        SwerveModuleState fr = new SwerveModuleState(
                Math.hypot(B, C),
                Math.toDegrees(Math.atan2(B, C))
        );
        SwerveModuleState bl = new SwerveModuleState(
                Math.hypot(A, D),
                Math.toDegrees(Math.atan2(A, D))
        );
        SwerveModuleState br = new SwerveModuleState(
                Math.hypot(A, C),
                Math.toDegrees(Math.atan2(A, C))
        );

        // Normalize speeds so the fastest wheel is 1.0
        double max = Math.max(
                Math.max(fl.speed, fr.speed),
                Math.max(bl.speed, br.speed)
        );

        if (max > 1.0) {
            fl.speed /= max;
            fr.speed /= max;
            bl.speed /= max;
            br.speed /= max;
        }

        fr.speed = -fr.speed;
        br.speed = -br.speed;
        bl.speed = -bl.speed;

        return new SwerveModuleState[] { fl, fr, bl, br };
    }
}
