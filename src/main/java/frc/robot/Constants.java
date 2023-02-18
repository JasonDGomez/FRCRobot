// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    
    
    public static final double MAX_VOLTAGE = 12.0;
    public static final double WHEEL_DIAMETER = 0.098171;
    public static final double FALCON500_MAX_RPM = 6380.0;
    public static final double TRACK_WIDTH_METERS = 0.4445;  // in 17.5
    public static final double WHEEL_BASE_METERS = 0.4445;   // in 17.5

    public static final int FRONT_LEFT_STEER_ID = 3;
    public static final int FRONT_LEFT_DRIVE_ID = 0;

    public static final int FRONT_RIGHT_STEER_ID = 6;
    public static final int FRONT_RIGHT_DRIVE_ID = 5;

    public static final int BACK_LEFT_STEER_ID = 2;
    public static final int BACK_LEFT_DRIVE_ID = 1;

    public static final int BACK_RIGHT_STEER_ID = 4;
    public static final int BACK_RIGHT_DRIVE_ID = 7;

    public static final int FRONT_LEFT_CANCODER_ID = 10;
    public static final int FRONT_RIGHT_CANCODER_ID = 11;
    public static final int BACK_LEFT_CANCODER_ID = 8;
    public static final int BACK_RIGHT_CANCODER_ID = 9;

    public static final int GYRO_ID = 13;
    public static final int PDP_ID = 12;

    public static final double kS = 0.16602;
    public static final double kV = 2.0483;
    public static final double kA = .27591;
    public static final double kP = 2.6393;
 
    public static final Translation2d FRONT_LEFT_LOCATION = new Translation2d(TRACK_WIDTH_METERS / 2.0, WHEEL_BASE_METERS / 2.0 );
    public static final Translation2d FRONT_RIGHT_LOCATION = new Translation2d(TRACK_WIDTH_METERS / 2.0, -WHEEL_BASE_METERS / 2.0 );
    public static final Translation2d BACK_LEFT_LOCATION = new Translation2d(-TRACK_WIDTH_METERS / 2.0, WHEEL_BASE_METERS / 2.0 );
    public static final Translation2d BACK_RIGHT_LOCATION = new Translation2d(-TRACK_WIDTH_METERS / 2.0, -WHEEL_BASE_METERS / 2.0 );

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static final double STEER_GEAR_REDUC = 12.8;
    public static final double DRIVE_GEAR_REDUC = 5.14;


    public static final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(Constants.FRONT_LEFT_LOCATION, Constants.FRONT_RIGHT_LOCATION, Constants.BACK_LEFT_LOCATION, Constants.BACK_RIGHT_LOCATION);

    public static final double MAX_VELOCITY_METERS_PER_SECOND =  5.48; //6380 / (60 * 12.8) * WHEEL_DIAMETER * Math.PI;
    public static final double MAX_ANGULAR_VELOCITY = MAX_VELOCITY_METERS_PER_SECOND / Math.hypot(TRACK_WIDTH_METERS / 2, WHEEL_BASE_METERS / 2); // angular velocity = linear velocity / radius
    public static final double MAX_ANGULAR_ACCELERATION = MAX_ANGULAR_VELOCITY / 2;


    public static final int XBOX_CONTROLLER_ID = 0;

    public static final int VACUUM_MOTOR_ID = 0;

    public static final int INTAKE_MOTOR_ID = 0;

    public static final int SOLENOID_FORWARD_CHANNEL = 0;
    public static final int SOLENOID_REVERSE_CHANNEL = 0;

    public static final TrapezoidProfile.Constraints thetaControllerConstraints = new TrapezoidProfile.Constraints(MAX_ANGULAR_VELOCITY, MAX_ANGULAR_ACCELERATION );
     
    

    public enum MotorType{
        STEER_MOTOR, DRIVE_MOTOR;
    }

    public enum ModuleType{
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
    }


}
