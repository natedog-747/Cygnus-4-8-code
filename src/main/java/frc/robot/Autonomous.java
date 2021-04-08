package frc.robot;

import java.util.ArrayList;
import frc.robot.commands.*;
import frc.robot.variables.Objects;

public class Autonomous {

    private ArrayList<Command> commandsList;
    boolean start = false;

    public Autonomous() {
        commandsList = new ArrayList<Command>();
    }

    /**
     * starts facing target directly. Shoots, goes to trench, picks up balls, shoots from trench
     */
    public void redPath() {
        if (!start) {
            commandsList.add(new ShootCommand(5000, true));  
            commandsList.add(new TurnCommand(52.03187, 0.05, false));    
            commandsList.add(new MoveDistanceCommand(109.45, 0.1, false));
            commandsList.add(new TurnCommand(52.03187, 0.05, true));    
            commandsList.add(new MoveDistanceCommand(108, 0.12, false));
            commandsList.add(new MoveDistanceCommand(108, 0.12, true));
            commandsList.add(new TurnCommand(52.03187, 0.05, false)); 
            commandsList.add(new ShootCommand(5000, true));
            start = true;
        }
        startAutononous();
    }

    /**
     * starts with back directed towards the trench. Picks up balls in trench, shoots from trench
     */
    public void orangePath() {
        if (!start) {
            // commandsList.add(new ShootCommand(5000, true));  
            commandsList.add(new TurnCommand(29.14, 0.05, true));    
            commandsList.add(new MoveDistanceCommand(86.63, 0.12, false));
            commandsList.add(new MoveDistanceCommand(108, 0.12, false));
            commandsList.add(new MoveDistanceCommand(108, 0.12, true));
            commandsList.add(new TurnCommand(29.14, 0.05, false)); 
            commandsList.add(new ShootCommand(5000, true));
            start = true;
        }
        startAutononous();

    }

    /**
     * starts facing target directly. Picks up 2 balls from rondezvous, shoots
     */
    public void yellowPath() {
        if (!start) {
            // commandsList.add(new ShootCommand(5000, true));  
            commandsList.add(new MoveDistanceCommand(122.63, 0.12, false));
            commandsList.add(new TurnCommand(33.1956, 0.05, true)); 
            commandsList.add(new MoveDistanceCommand(21.63, 0.12, false));
            commandsList.add(new MoveDistanceCommand(21.63, 0.12, true));
            commandsList.add(new TurnCommand(33.1956, 0.05, false)); 
            commandsList.add(new ShootCommand(5000, true));
            start = true;
        }
        startAutononous();
    }

    public void greenPath() {
        startAutononous();
    }

    public void bluePath() {
        startAutononous();
    }

    public void indigoPath() {
        startAutononous();
    }

    public void violetPath() {
        startAutononous();
    }

    public void defaultAutonomous() {
        if (!start) {
            commandsList.add(new MoveDistanceCommand(48, 0.1, false));
            start = true;
        }
        startAutononous();
    }

    public void startAutononous() {
        Objects.scheduler.addCommands(commandsList);
        Objects.scheduler.run();
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}

