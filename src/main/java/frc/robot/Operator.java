package frc.robot;

import frc.robot.variables.ControllerMap;
import frc.robot.variables.MagicNumbers;
import frc.robot.variables.Objects;

public class Operator {

    public Operator() {
        
    }

    public void operate() {
        revUp();
        farShoot();
        middleShoot();
        closeShoot();
        intake();
        index();
        extendPiston();
        retractPiston();
        manualOuttake();
        stopShoot();
    }
//used by A, rev up
    public void revUp() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.A_BUTTON)) {
            // changes rpm for Button A
            Objects.outtake.fire2(3917.78645324);
        }
    }

    public void stopShoot() {
        if (!Objects.operatorJoy.getRawButton(ControllerMap.A_BUTTON)
            && !Objects.operatorJoy.getRawButton(ControllerMap.B_BUTTON)
            && !Objects.operatorJoy.getRawButton(ControllerMap.X_BUTTON)
            && !Objects.operatorJoy.getRawButton(ControllerMap.Y_BUTTON)
            && !Objects.driverJoy.getRawButton(ControllerMap.X_BUTTON)) {
            Objects.outtake.stop();
        }
    }
//used to shoot currently, matches rpm of rev up
//nate's change
// 5200
    public void farShoot() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.B_BUTTON)) {
            Objects.outtake.farShoot();
        }
    }

    public void middleShoot() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.X_BUTTON)) {
            Objects.outtake.middleShoot();
        }
    }

    public void closeShoot() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.Y_BUTTON)) {
            Objects.outtake.closeShoot();
        }
    }

    public void intake() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.L_BUMPER)
            && !Objects.operatorJoy.getRawButton(ControllerMap.R_BUMPER)) {
            Objects.intake.intake(MagicNumbers.intake);
        } else if (Objects.operatorJoy.getRawButton(ControllerMap.L_BUMPER)
                   && Objects.operatorJoy.getRawButton(ControllerMap.R_BUMPER)) {
                   Objects.intake.intake(MagicNumbers.purgeIntake);
        } else {
            Objects.intake.intake(MagicNumbers.defaultIntake);
        }
    }

    public void index() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.R_BUMPER)
            && !Objects.operatorJoy.getRawButton(ControllerMap.L_BUMPER)) {
            //purgeIndex untested
            // Objects.index.purgeIndex();
        } else if (Objects.operatorJoy.getRawAxis(ControllerMap.L_STICKY) > 0.1 
                   || Objects.operatorJoy.getRawAxis(ControllerMap.L_STICKY) < -0.1) {
                   Objects.index.setTimer(MagicNumbers.ballSpacing + 1);
                   Objects.index.indexOverride(Objects.operatorJoy.getRawAxis(ControllerMap.L_STICKY));
        } else {
            Objects.index.backgroundIndex();
        }
    }

    public void extendPiston() {
        if (Objects.operatorJoy.getPOV() == ControllerMap.DEGREES0) {
            Objects.climb.extendPiston();
        }
    }

    public void retractPiston() {
        if (Objects.operatorJoy.getPOV() == ControllerMap.DEGREES180) {
            Objects.climb.retractPiston();            
        }
    }

    public void manualOuttake() {
        if (Objects.operatorJoy.getRawButton(ControllerMap.L_STICKD)) {
            Objects.index.manualOuttake();
        } else if (!Objects.operatorJoy.getRawButton(ControllerMap.L_STICKD)) {
            Objects.index.setFlush(false);
        }
    }
}