package frc.robot;

import frc.robot.variables.ControllerMap;
import frc.robot.variables.Objects;

public class Driver {

    public Driver() {
       
    }

    public void drive() {
        arcadeDrive();
        // autonomousShoot();
        //activeClimb();
        autonomousLineUp();
        turnLightsOff();
    }

    public void arcadeDrive() {
        if (Objects.driverJoy.getRawAxis(ControllerMap.L_STICKY) != 0 
            || Objects.driverJoy.getRawAxis(ControllerMap.R_STICKX) != 0) {
            Objects.driveTrain.arcadeDrive(Objects.driverJoy.getRawAxis(ControllerMap.L_STICKY),
            Objects.driverJoy.getRawAxis(ControllerMap.R_STICKX));
        } else if(!Objects.vision.autoLineup) {
            Objects.driveTrain.stop();
        }
    }

    public void autonomousShoot() {
        if (Objects.driverJoy.getRawButton(ControllerMap.X_BUTTON)) {
            Objects.visionSystems.turnLightOn();
            Objects.outtake.autoShoot();
            Objects.vision.autoLineup = true;
        }
        Objects.vision.autoLineup = false;
    }

    public void turnLightsOff() {
        if (!Objects.driverJoy.getRawButton(ControllerMap.X_BUTTON) 
            && !Objects.driverJoy.getRawButton(ControllerMap.L_STICKD)) {
            Objects.visionSystems.turnLightOff();
        }
    }

    public void activeClimb() {
        if (Objects.driverJoy.getRawButton(ControllerMap.L_BUMPER)) {
            Objects.climb.leftSlowActiveClimb();
        } else if (Objects.driverJoy.getRawButton(ControllerMap.R_BUMPER)) {
            Objects.climb.rightSlowActiveClimb();
        } else if (Objects.driverJoy.getRawAxis(ControllerMap.R_TRIGGER) > 0) {
            Objects.climb.rightActiveClimb(Objects.driverJoy.getRawAxis(ControllerMap.R_TRIGGER));
        } else if (Objects.driverJoy.getRawAxis(ControllerMap.L_TRIGGER) > 0) {
            Objects.climb.rightActiveClimb(-Objects.driverJoy.getRawAxis(ControllerMap.L_TRIGGER));
        } else {
            Objects.climb.stop();
        }
    }

    public void autonomousLineUp() {
        if (Objects.driverJoy.getRawButton(ControllerMap.L_STICKD)) {
            Objects.vision.autonomousLineUp();
            Objects.vision.autoLineup = true;
            Objects.visionSystems.turnLightOn();
        } else {
            Objects.vision.autoLineup = false;
        }
    }
}