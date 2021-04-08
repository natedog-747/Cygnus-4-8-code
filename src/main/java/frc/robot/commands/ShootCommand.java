package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.Command;
import frc.robot.variables.Objects;

public class ShootCommand implements Command {

    int velocity;
    Timer timer = new Timer();
    boolean lineup;

    public ShootCommand(int v, boolean l) {
        velocity = v;
        lineup = l;
        if (!lineup) {
            timer.start();
        }
        Objects.outtake.isShooting = false;
    }

    public ShootCommand(boolean l) {
        velocity = -1;
        lineup = l;
        if (!lineup) {
            timer.start();
        }
        Objects.outtake.isShooting = false;
    }

    public void execute() {
        Objects.visionSystems.turnLightOn();
        System.out.println(Objects.vision.spinUp());
        if (lineup && (Objects.vision.getYaw() > 1 || Objects.vision.getYaw() < -1)) {
            Objects.driveTrain.tankDrive(0.5 * -Objects.vision.spinUp(), 0.5 * Objects.vision.spinUp());
        } else {
            if (velocity == -1) {
                Objects.outtake.fire4(Objects.vision.autoShoot());
            } else {
                Objects.outtake.fire4(velocity);
            }
        }
    }

    public void stop() {
        Objects.outtake.firePercent(0);
    }

    public boolean isComplete() {
        boolean result = false;
        if (!Objects.outtake.isShooting) {
            timer.start();
        }
        if (timer.get() > 6) {
            result = true;
            timer.stop();
            Objects.outtake.stop();
            Objects.visionSystems.turnLightOff();
        }
        return result;
    }
}
