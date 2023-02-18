package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class SwerveModule {
    private final SwerveMotor driveMotor;
    private final SwerveMotor steerMotor;
    private final CANCoder absEncoder;

    public SwerveModule(int steerID, int driveID, int encoderID, boolean driveInverted){

         CANCoderConfiguration encoderConfig = new CANCoderConfiguration();   // Configuration for the Magnetic Can Encoder

         driveMotor = new SwerveMotor(driveID, 80, driveInverted);    //Creates Drive Motor 
         steerMotor = new SwerveMotor(steerID, 20, 0.2, 0.0, 0.1); //Creates Steer Motor
         absEncoder = new CANCoder(encoderID);  // Creates Magnetic Can Encoder
         absEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10, 250); 
         encoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;  // Configuration has that CanEncoder is [0,360)
         absEncoder.configAllSettings(encoderConfig); // Applies configuration to the Can Encoder
         driveMotor.setMotorEncoder(0);   // Sets integrated encoder of drive wheel to 0 -> important for odometry/SwerveModulePosition
 

    }

    public double getAbsEncoderAngle(){
        
        return absEncoder.getAbsolutePosition(); // Returns absolute position from CanEncoder
    }

    public double getEncoderAngle(){
        return steerMotor.getMotorEncoder() / 2048.0 * 360;   // Returns Angle of steer integrated encoder in terms of degrees 
    }

    public double getEncoderValue(){
        return steerMotor.getMotorEncoder();    // Returns straight up encoder value from steer integrated encoder
    }

    public double getDriveDistance(){       //Gets estimated distance traveled by drive wheel using integrated encoder -> this is why initializing drive integrated encoder to 0 is important
        return driveMotor.getMotorEncoder() / 2048.0 * Math.PI * Constants.WHEEL_DIAMETER / Constants.DRIVE_GEAR_REDUC;
    }

    public Rotation2d getIntegratedAbsAngle(){    //Gets angle from integrated encoder but limits it to [0,360) -> important for SwerveModulePosition/Odometry
        return Rotation2d.fromDegrees((steerMotor.getMotorEncoder() / 2048.0 * 360 / Constants.STEER_GEAR_REDUC ));  //Might Need % 360 unsure
    }

    public SwerveModulePosition getPosition(){   //Returns SwerveModulePosition of for Odometry in SwerveDrivetrain
        return new SwerveModulePosition(getDriveDistance(), getIntegratedAbsAngle());
    }

    public void resetDriveEncoders(){
        driveMotor.setMotorEncoder(0);
    }


    

    

    public void set(SwerveModuleState state){
        
        double goalAngleRadians = state.angle.getRadians();
        double goalVelocity = state.speedMetersPerSecond;

        
        


         goalAngleRadians %= (2.0 * Math.PI);

        if(goalAngleRadians < 0.0){
            goalAngleRadians += (2.0 * Math.PI);
        }

        double currentAngleRadians = steerMotor.getMotorEncoder() / 2048.0 * 2.0 * Math.PI / Constants.STEER_GEAR_REDUC; 

        currentAngleRadians %= (2.0 * Math.PI);

        if(currentAngleRadians < 0.0){
            currentAngleRadians += (2.0 * Math.PI);
        }

        double error = goalAngleRadians - currentAngleRadians;

        //if(error >= Math.PI){
        //    goalAngleRadians -= (2.0 * Math.PI);
       // } else{
        //    goalAngleRadians += (2.0 * Math.PI);
       // }

        error = goalAngleRadians - currentAngleRadians;

        if(error > Math.PI/2.0 || error < -Math.PI/2.0){
            goalAngleRadians += Math.PI;
            goalVelocity *= -1.0;
        }

        driveMotor.setDriveMotor(goalVelocity); 
        steerMotor.setSteerMotor(goalAngleRadians); 
    }
}
