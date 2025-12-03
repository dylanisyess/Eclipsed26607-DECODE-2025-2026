package org.firstinspires.ftc.teamcode.util;

public class PID {

    private double kP, kI, kD;
    private double integral;
    private double prevError;
    private double prevTime;

    public PID(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        this.integral = 0;
        this.prevError = 0;
        this.prevTime = System.nanoTime() / 1e9;
    }

    public double calculate(double error) {
        double currentTime = System.nanoTime() / 1e9;
        double dt = currentTime - prevTime;
        prevTime = currentTime;

        // Integral term (accumulator)
        integral += error * dt;

        // Derivative term
        double derivative = (dt > 0) ? (error - prevError) / dt : 0;

        prevError = error;

        // PID output
        return kP * error + kI * integral + kD * derivative;
    }

    public void reset() {
        integral = 0;
        prevError = 0;
        prevTime = System.nanoTime() / 1e9;
    }
}
