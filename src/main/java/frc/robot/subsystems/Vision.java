package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import frc.robot.variables.Objects;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {

    NetworkTableInstance table = NetworkTableInstance.getDefault();
    NetworkTable cameraTable = table.getTable("chameleon-vision").getSubTable("joemama");
    double targetRotation;
    double boundingHeight;
    double boundingWidth;
    double fittedHeight;
    double fittedWidth;
    double pitch;
    double[] position;
    double yaw;
    public boolean autoLineup = false;

    public Vision() {
        
    }

    public double getTargetRotation() {
        targetRotation = cameraTable.getEntry("targetRotation").getDouble(0.0);
        SmartDashboard.putNumber("Target Rotation: ", targetRotation);
        return cameraTable.getEntry("targetRotation").getDouble(0.0);
    } 

    public Boolean isValid() {
        return cameraTable.getEntry("isValid").getBoolean(false);
    } 

    public double getBoundingHeight() {
        boundingHeight = cameraTable.getEntry("targetBoundingHeight").getDouble(0.0);
        SmartDashboard.putNumber("Bounding Height", boundingHeight);
        return boundingHeight;
    } 

    public double getBoundingWidth() {
        boundingWidth = cameraTable.getEntry("targetBoundingWidth").getDouble(0.0);
        SmartDashboard.putNumber("Bounding Width", boundingWidth);
        return boundingWidth;
    } 

    public double getFittedHeight() {
        fittedHeight = cameraTable.getEntry("targetFittedHeight").getDouble(0.0);
        SmartDashboard.putNumber("Fitted Height: ", fittedHeight);
        return fittedHeight;
    } 

    public double getFittedWidth() {
        fittedWidth = cameraTable.getEntry("targetFittedWidth").getDouble(0.0);
        SmartDashboard.putNumber("Fitted Width: ", fittedWidth);
        return fittedWidth;
    } 

    public double getPitch() {
        pitch = cameraTable.getEntry("targetPitch").getDouble(0.0);
        SmartDashboard.putNumber("Pitch: ", pitch);
        return pitch;
    } 

    public double[]  getPosition() {
        double[] theDefault = {0.0,0.0,0.0};
        position = cameraTable.getEntry("targetPose").getDoubleArray(theDefault);
        SmartDashboard.putNumber("X: ", position[0]);
        SmartDashboard.putNumber("Y: ", position[1]);
        SmartDashboard.putNumber("Angle: ", position[2]);
        return position;
    } 

    public double getYaw() {
        yaw = cameraTable.getEntry("targetYaw").getDouble(0.0);
        SmartDashboard.putNumber("Yaw: ", yaw);
        return yaw;
    }

    public void setDriverMode(Boolean liveStream) {
        cameraTable.getEntry("driverMode").setBoolean(liveStream);
    }

    public double spinUp() {
        double result = (0.00111111111111111111 * (getYaw() * getYaw()));
        if(getYaw() < 0){
            result = -result;
        }
        return result;
    }

    public double autoShoot() {
        getPitch();
        getFittedHeight();
        getBoundingHeight();
        double result = 0;
        return result;
    }

	public void autonomousLineUp() {
        Objects.driveTrain.tankDrive(-Objects.vision.spinUp(), Objects.vision.spinUp());
    }
}
