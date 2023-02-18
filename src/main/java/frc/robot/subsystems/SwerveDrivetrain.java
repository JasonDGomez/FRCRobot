package frc.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ModuleType;

public class SwerveDrivetrain extends SubsystemBase {

    private final SwerveModule frontLeftSwerveModule;
    private final SwerveModule frontRightSwerveModule;
    private final SwerveModule backLeftSwerveModule;
    private final SwerveModule backRightSwerveModule;

    private final SwerveDriveOdometry odometry;
    
    private Pigeon2 gyro;
    

    public SwerveDrivetrain(){
        frontLeftSwerveModule = new SwerveModule(Constants.FRONT_LEFT_STEER_ID, Constants.FRONT_LEFT_DRIVE_ID, Constants.FRONT_LEFT_CANCODER_ID, false);
        frontRightSwerveModule = new SwerveModule(Constants.FRONT_RIGHT_STEER_ID, Constants.FRONT_RIGHT_DRIVE_ID, Constants.FRONT_RIGHT_CANCODER_ID, false); // True
        backLeftSwerveModule = new SwerveModule(Constants.BACK_LEFT_STEER_ID, Constants.BACK_LEFT_DRIVE_ID, Constants.BACK_LEFT_CANCODER_ID, false);
        backRightSwerveModule = new SwerveModule(Constants.BACK_RIGHT_STEER_ID, Constants.BACK_RIGHT_DRIVE_ID, Constants.BACK_RIGHT_CANCODER_ID, false);
        gyro = new Pigeon2(Constants.GYRO_ID);
        zeroGyro();
        odometry = new SwerveDriveOdometry(
          Constants.kinematics, 
          getGyroRot2d(),
          new SwerveModulePosition[] {
             frontLeftSwerveModule.getPosition(),
             frontRightSwerveModule.getPosition(),
             backLeftSwerveModule.getPosition(),
             backRightSwerveModule.getPosition()
          });

          
         

       

    }

    public void zeroGyro(){
      //  gyro.setYawToCompass()
        gyro.setYaw(0);
    }

    public double getGyroAngle(){
    
        return gyro.getYaw() % 360;
    }

    public Rotation2d getGyroRot2d(){
        return Rotation2d.fromDegrees(gyro.getYaw()); // Might need to use getGyroAngle, unsure
    }

    public SwerveModule getModule(Constants.ModuleType module){
        if(module == ModuleType.FRONT_LEFT){
            return frontLeftSwerveModule;
        } else if(module == ModuleType.FRONT_RIGHT){
            return frontRightSwerveModule;
        } else if(module == ModuleType.BACK_LEFT){
            return backLeftSwerveModule;
        } else{
            return backRightSwerveModule;
        }
    }


    public void resetOdometry(Pose2d pose){
        odometry.resetPosition(getGyroRot2d(),  new SwerveModulePosition[] {
            frontLeftSwerveModule.getPosition(),
            frontRightSwerveModule.getPosition(),
            backLeftSwerveModule.getPosition(),
            backRightSwerveModule.getPosition()
         }, pose);
    }

    public Pose2d getPose(){
       return odometry.getPoseMeters();
    }

    public void resetDriveEncoders(){
        frontLeftSwerveModule.resetDriveEncoders();
        frontRightSwerveModule.resetDriveEncoders();
        backLeftSwerveModule.resetDriveEncoders();
        backRightSwerveModule.resetDriveEncoders();
    }

    public void setModuleStates(SwerveModuleState[] moduleStates){
        SwerveModuleState frontLeft =   moduleStates[0]; 
        SwerveModuleState frontRight =   moduleStates[1];
        SwerveModuleState backLeft =     moduleStates[2];
        SwerveModuleState backRight =   moduleStates[3]; 

        frontLeftSwerveModule.set(frontLeft);
        frontRightSwerveModule.set(frontRight);
        backLeftSwerveModule.set(backLeft);
        backRightSwerveModule.set(backRight);


    }


    public void drive(ChassisSpeeds speeds){
        SwerveModuleState[] moduleStates = Constants.kinematics.toSwerveModuleStates(speeds);

        SwerveModuleState frontLeft =   moduleStates[0];//SwerveModuleState.optimize(moduleStates[0], Rotation2d.fromDegrees(frontLeftSwerveModule.getEncoderAngle() % 360 / 12.8));     //(frontLeftSwerveModule.getAbsEncoderAngle()));
        SwerveModuleState frontRight =   moduleStates[1];//SwerveModuleState.optimize(moduleStates[1], Rotation2d.fromDegrees(frontRightSwerveModule.getEncoderAngle() % 360 / 12.8));
        SwerveModuleState backLeft =     moduleStates[2];//SwerveModuleState.optimize(moduleStates[2], Rotation2d.fromDegrees(backLeftSwerveModule.getEncoderAngle() % 360 / 12.8));
        SwerveModuleState backRight =   moduleStates[3];//SwerveModuleState.optimize(moduleStates[3],Rotation2d.fromDegrees(backRightSwerveModule.getEncoderAngle() % 360 / 12.8));

        SmartDashboard.putNumber("Front Left Module Goal Angle : ", frontLeft.angle.getDegrees());
        SmartDashboard.putNumber("Front Right Module Goal Angle : ", frontRight.angle.getDegrees());
        SmartDashboard.putNumber("Back Left Module Goal Angle : ", backLeft.angle.getDegrees());
        SmartDashboard.putNumber("Back Right Module Goal Angle : ", backRight.angle.getDegrees());

        //SmartDashboard.putNumber("Front Left Module Goal Speed: ", frontLeft.speedMetersPerSecond);
        //SmartDashboard.putNumber("Front Right Module Goal Speed : ", frontRight.speedMetersPerSecond);
       // SmartDashboard.putNumber("Back Left Module Goal Speed : ", backLeft.speedMetersPerSecond);
       // SmartDashboard.putNumber("Back Right Module Goal Speed : ", backRight.speedMetersPerSecond);

        SmartDashboard.putNumber("Front Left Integrated Encoder Angle : ", frontLeftSwerveModule.getEncoderAngle() % 360);
        SmartDashboard.putNumber("Front Right Integrated Encoder Angle : ", frontRightSwerveModule.getEncoderAngle() % 360);
        SmartDashboard.putNumber("Back Left Integrated Encoder Angle : ", backLeftSwerveModule.getEncoderAngle() % 360);
        SmartDashboard.putNumber("Back Right Integrated Encoder Angle : ", backRightSwerveModule.getEncoderAngle() % 360);

        
        
       
        
        
       // SwerveModuleState.optimize(desiredState, currentAngle)


        frontLeftSwerveModule.set(frontLeft);
        frontRightSwerveModule.set(frontRight);
        backLeftSwerveModule.set(backLeft);
        backRightSwerveModule.set(backRight);
    }

    @Override
    public void periodic(){
       odometry.update(
        getGyroRot2d(),
        new SwerveModulePosition[] {
            frontLeftSwerveModule.getPosition(),
            frontRightSwerveModule.getPosition(),
            backLeftSwerveModule.getPosition(),
            backRightSwerveModule.getPosition()
         });
        
    }

    
}
