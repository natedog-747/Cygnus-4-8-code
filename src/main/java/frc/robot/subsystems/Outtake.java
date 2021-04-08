package frc.robot.subsystems;

import frc.robot.variables.ControllerMap;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;

public class Outtake {

    public CANPIDController pidController;
    private final CANEncoder encoder;
    public double kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public boolean isShooting;
    public static boolean isRev;
    public double kP = 0.00032; 
    public double kI = 0.00000028;
    public double kD = 0.00;

    public Outtake() {
        isRev = false;
        pidController = Motors.outLeader.getPIDController();
        encoder = Motors.outLeader.getEncoder();
        kP = 0.00032; 
        kI = 0.0000003;
        kD = 0.00;
        kIz = 0; 
        kFF = 0.0000; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        //maxRPM = 5700;
        maxRPM = 6500;
        maxVel = 2000;
        maxAcc = 1500;

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
    }
    
    public void revUp() { 
        pidController.setReference(-4000, ControlType.kVelocity);
        Motors.outFollower.follow(Motors.outLeader, true);
        SmartDashboard.putNumber("Velocity", encoder.getVelocity());
    }

    public void fire(final double percentPower) {
        System.out.println(Motors.outLeader.getEncoder().getVelocity());
        Motors.outLeader.setVoltage(percentPower);
        Motors.outFollower.follow(Motors.outLeader, true);
    }

    public void moveUp(final double percent) {
        Motors.moveUp.set(-percent);
        Motors.indexLead.set(-percent);
        Motors.indexFollower.follow(Motors.indexLead);
    }

    public void fire2(final double setPoint) {
        pidController.setReference(-setPoint, ControlType.kVelocity);
        Motors.outFollower.follow(Motors.outLeader, true);
        if ((encoder.getVelocity() > (-setPoint) - 70) && (encoder.getVelocity() < (-setPoint) + 70)) {
            isShooting = true;
        }
    }

    public void fire3(final double setPoint) {
        //SmartDashboard.putData("Velocity", encoder.getVelocity());
        pidController.setReference(-setPoint, ControlType.kVelocity);
        Motors.outFollower.follow(Motors.outLeader, true);
        if (encoder.getVelocity() > (Math.abs(setPoint)) - 500) {
            pidController.setFF(1/setPoint);
        } else {
            pidController.setFF(0);
        }
        //System.out.print(encoder.getVelocity());
        //System.out.println("hello");
        if ((encoder.getVelocity() > (Math.abs(setPoint)) - 70) 
             && (encoder.getVelocity() < (Math.abs(setPoint)) + 70)) {
            moveUp(1);
            isShooting = true;
        } else {
            moveUp(0);
        }
    }
    
    public void fire4(final double setPoint) {
        pidController.setReference(-setPoint, ControlType.kVelocity);
        Motors.outFollower.follow(Motors.outLeader, true);
        if ((encoder.getVelocity() > (-setPoint) - 70) && (encoder.getVelocity() < (-setPoint) + 70)) {
           moveUp(1);
            isShooting = true;
        } else {
            moveUp(0);
        }
    }

    public void farShoot() {
      //button b 
      // 4325
        fire3(5200);
    }

    public void middleShoot() {
      //button x
        fire3(3900);
    }

    public void closeShoot() {
        //button y
        fire2(2800);
    }

    public void autoShoot() {
        if (Objects.vision.getYaw() > 1 || Objects.vision.getYaw() < -1) {
            revUp();
        } else {
            fire2(Objects.vision.autoShoot());
        }
    }
    
    public void firePercent(final double percent) {
        Motors.outLeader.set(percent);
        Motors.outFollower.follow(Motors.outLeader, true);
    }

    public void reset() {
        Motors.outLeader.restoreFactoryDefaults();
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
    }

    public void stop() {
        Motors.outLeader.stopMotor();
        Motors.outFollower.stopMotor();
        reset();
    }

    public boolean isShooting() {
        return isShooting;
    }
// not used
    public void fireSequenceTest(final int setPoint) {
        if (Objects.driverJoy.getRawButton(ControllerMap.X_BUTTON)) {
            fire3(setPoint);
        }
    }
}

