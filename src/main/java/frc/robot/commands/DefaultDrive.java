package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class DefaultDrive extends CommandBase {
    
    private final SwerveDrivetrain subsystem;
    private final DoubleSupplier xSupplier;
    private final DoubleSupplier ySupplier;
    private final DoubleSupplier rotSupplier;


    public DefaultDrive(SwerveDrivetrain subsystem, DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rotSupplier){
        this.subsystem = subsystem;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.rotSupplier = rotSupplier;


        addRequirements(subsystem);

    }

    
    public void initialize(){}

    public void execute(){
        subsystem.drive(ChassisSpeeds.fromFieldRelativeSpeeds(ySupplier.getAsDouble(), xSupplier.getAsDouble(), rotSupplier.getAsDouble(), Rotation2d.fromDegrees(subsystem.getGyroAngle())));

        SmartDashboard.putNumber("Front Left Module Absolute Encoder: ", subsystem.getModule(Constants.ModuleType.FRONT_LEFT).getAbsEncoderAngle());
        SmartDashboard.putNumber("Front Right Module Absolute Encoder: ", subsystem.getModule(Constants.ModuleType.FRONT_RIGHT).getAbsEncoderAngle());
        SmartDashboard.putNumber("Back Left Module Absolute Encoder: ", subsystem.getModule(Constants.ModuleType.BACK_LEFT).getAbsEncoderAngle());
        SmartDashboard.putNumber("Back Right Module Absolute Encoder: ", subsystem.getModule(Constants.ModuleType.BACK_RIGHT).getAbsEncoderAngle());

    //    SmartDashboard.putNumber("Y AXIS: ", ySupplier.getAsDouble() / Constants.MAX_VELOCITY_METERS_PER_SECOND);
     //   SmartDashboard.putNumber("X AXIS: ", xSupplier.getAsDouble() / Constants.MAX_VELOCITY_METERS_PER_SECOND);
     //   SmartDashboard.putNumber("ROT AXIS: ", rotSupplier.getAsDouble() / Constants.MAX_ANGULAR_VELOCITY);

        SmartDashboard.putNumber("Gyro Angle: ", subsystem.getGyroAngle());

        

    }

    public void end(boolean interrupted) {
        subsystem.drive(new ChassisSpeeds(0,0,0));
    }

    public boolean isFinished(){
        return false;
    }

    
}
