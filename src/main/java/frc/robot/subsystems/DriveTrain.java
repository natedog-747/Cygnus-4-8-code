package frc.robot.subsystems;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import frc.robot.variables.MagicNumbers;

public class DriveTrain {

    public boolean isDriving;
    public boolean isTurning;
    public boolean isStart;
    public boolean isIndexing;

    public double startingTicks; 
    public double currentTicks;
    public double targetTicks;
    public double startingAngle;
    public double currentAngle;

    public DriveTrain() {
        isDriving = false;
        isTurning = false;
        isStart = true;
        startingTicks = -1;
        currentTicks = -1;
        targetTicks = -1;
        startingAngle = -1;
        currentAngle = -1;
    }

    public double quadraticDrive(double value) {
        double slow = 1.0 * (value * value * value * value);
        if (slow < 0) {
            slow = -slow;
        }
        return slow;
    }

    public void tankDrive(double left, double right) {
        double nvar = 1;
        Motors.leftFront.set(-nvar * left);
        Motors.leftBack.set(-nvar * left);
        Motors.rightFront.set(nvar * right);
        Motors.rightBack.set(nvar * right);
    }

    public void arcadeDrive(double throttle, double turnVal) {
        //+ dir = outtake forward, - dir = intake forward
        double nvar = 0.8;
        int dir = 1;
        Motors.leftFront.set(-nvar * dir * (throttle - dir * turnVal));
        Motors.leftBack.follow(Motors.leftFront, false);
        Motors.rightFront.set(nvar * dir * (throttle + dir * turnVal));
        Motors.rightBack.follow(Motors.rightFront, false);
    }

    public void powerDrive(double power) {
        Motors.leftFront.set(-power);
        Motors.leftBack.set(-power);
        Motors.rightFront.set(power);
        Motors.rightBack.set(power);
    }

    public void moveDistance(double targetDistance, double power, boolean direction) {
        if (isStart) {
            startingTicks = Motors.rightBack.getEncoder().getPosition();
            targetTicks = targetDistance * MagicNumbers.TPC;
            isStart = false;
        }
        currentTicks = Motors.rightBack.getEncoder().getPosition();
        if (direction && currentTicks < (targetTicks + startingTicks)) {
            powerDrive(power);
            isDriving = true;
        } else if (!direction && currentTicks >  (startingTicks - targetTicks)) {
            powerDrive(-power);
            isDriving = true;
        } else {
            isDriving = false;
        }
    }

    public void powerDriveIndex(double power) {
        Motors.indexLead.set(-power);
        Motors.indexFollower.follow(Motors.indexLead);
    }

    public void moveDistanceIndex(double targetDistance, double power, boolean direction) {
        if (isStart) {
            startingTicks = Motors.indexLead.getEncoder().getPosition();
            targetTicks = targetDistance * MagicNumbers.TPC;
            isStart = false;
        }
        currentTicks = Motors.indexLead.getEncoder().getPosition();
        if (direction && currentTicks < (targetTicks + startingTicks)) {
            powerDriveIndex(power);
            isIndexing = true;
        } else if (!direction && currentTicks >  (startingTicks - targetTicks)) {
            powerDriveIndex(-power);
            isIndexing = true;
        } else {
            isIndexing = false;
        }
    }
    
    public void turnTo(double targetAngle, double power, boolean direction) {
        if (isStart) {
            startingAngle = Objects.navx.getAngle();
            isStart = false;
        }
        currentAngle = Objects.navx.getAngle();
        if (direction && currentAngle < (startingAngle + targetAngle)) {
            tankDrive(-power, power);
            isTurning = true;
        } else if(!direction && currentAngle > (startingAngle - targetAngle)) {
            tankDrive(power, -power);
            isTurning = true;
        } else {
            isTurning = false;
        }
    }

    public boolean isDriving() {
        return isDriving;
    }

    public boolean isTurning() {
        return isTurning;
    }

    public void stop() {
        powerDrive(0);
        isStart = true;
    }
}