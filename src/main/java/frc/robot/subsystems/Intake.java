package frc.robot.subsystems;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.variables.MagicNumbers;

public class Intake {

    int counter;
    double speed;

    public Intake() {
        counter = 0;
    }

    public void intake(int mode) {
        //original intake code - replaced with most recent intake code
        // if (mode == MagicNumbers.defaultIntake) {
        //     stop();
        // }
        // if (mode == MagicNumbers.intake) {
        //     Motors.intake.set(.4);
        //     Objects.intakeSolenoidExtend.set(Value.kForward);
        //     Objects.intakeSolenoidRetract.set(Value.kReverse);
        // }
        // if (mode == MagicNumbers.purgeIntake) {
        //     Motors.intake.set(-.4);
        //     Objects.intakeSolenoidExtend.set(Value.kForward);
        //     Objects.intakeSolenoidRetract.set(Value.kReverse);
        // }

        //intake code from version CygnusIntermediate_12-03-2020
        if(Objects.index.getInd0()) {
            if (counter > 10) {
                speed = 0.35;
            } else {
                speed = 0.8;
                counter++;
            }
        } else {
            counter = 0;
            speed = 0.8;
        }
        if (mode == -1) {
            stop();
        }
        if (mode == 0) {
            Motors.intake.set(speed);
            Objects.intakeSolenoidExtend.set(Value.kForward);
            Objects.intakeSolenoidRetract.set(Value.kReverse);
        }
        if (mode == 1) {
            Motors.intake.set(-speed);
            Objects.intakeSolenoidExtend.set(Value.kForward);
            Objects.intakeSolenoidRetract.set(Value.kReverse);
        }
    }

    public void stop() {
        Motors.intake.set(0);
        Objects.intakeSolenoidExtend.set(Value.kReverse);
        Objects.intakeSolenoidRetract.set(Value.kForward);
    }
}