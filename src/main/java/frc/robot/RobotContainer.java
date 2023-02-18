// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
 
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.ForkLiftMove;
import frc.robot.commands.IntakeItem;
import frc.robot.commands.PneumaticMove;
import frc.robot.subsystems.Forklift;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PneumaticSubsystem;
import frc.robot.subsystems.SwerveDrivetrain;
 

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
  private final XboxController controller = new XboxController(Constants.XBOX_CONTROLLER_ID);
  private final Forklift forklift = new Forklift();
  //private final Vacuum vacuum = new Vacuum();
  private final Intake intake = new Intake();
  SendableChooser<Command> chooser = new SendableChooser<>();
  PneumaticSubsystem pneumaticSubsystem = new PneumaticSubsystem();
 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    
    drivetrain.setDefaultCommand(new DefaultDrive(
      drivetrain,
      () -> -(deadband(controller.getLeftX()) * Constants.MAX_VELOCITY_METERS_PER_SECOND),
        () -> -(deadband(controller.getLeftY()) * Constants.MAX_VELOCITY_METERS_PER_SECOND),
        () -> (deadband(controller.getRightX()) * Constants.MAX_ANGULAR_VELOCITY) * 0.4
         ));

     intake.setDefaultCommand(new IntakeItem(intake, 
       () -> triggerDeadband(controller.getLeftTriggerAxis()),
         () -> triggerDeadband(controller.getRightTriggerAxis())));



      pneumaticSubsystem.setDefaultCommand(new PneumaticMove(pneumaticSubsystem,
       () -> triggerDeadband(controller.getLeftTriggerAxis()),
       () -> triggerDeadband(controller.getRightTriggerAxis()),
       () -> controller.getRightBumper()));

      forklift.setDefaultCommand(new ForkLiftMove(forklift,
       () -> controller.getAButton(),
       () -> controller.getYButton()
      
      ));
       
     
    
    
    configureButtonBindings();


     

    chooser.addOption("Straight Auton", loadPathplannerTrajectory("output/deploy/pathplanner/generatedJSON/sttt.wpilib.json", true));
    chooser.addOption("Curved Auton", loadPathplannerTrajectory("output/deploy/pathplanner/generatedJSON/sttt.wpilib.json", true));

    Shuffleboard.getTab("Autonomous").add(chooser);
     

  }

  public double deadband(double input){
    if(Math.abs(input) < .175){
       
      return 0;
    } else{
      return input;
    }
  }

  public double triggerDeadband(double input){
    if(Math.abs(input) < .175){
       
      return 0;
    } else{
      return input;
    }
  }

  


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        
        // new POVButton(controller, 180).whileTrue(new CreateVacuum(vacuum));
       //  new POVButton(controller,0).whileTrue(new ReleaseVacuum(vacuum));
         
         //Trigger rightBumper = new Trigger(() -> controller.getRightBumper());

         //rightBumper.whileTrue(new PneumaticExtend(pneumaticSubsystem));
         
          
        
         

         

  }


  public Command loadPathplannerTrajectory(String filename, boolean resetOdometry){
    Trajectory trajectory;
    
    try{
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(filename);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
       

    } catch(IOException exception){

      DriverStation.reportError("Unable to open trajectory", exception.getStackTrace());
      System.out.println("Unable to read from file");
      return new InstantCommand();

    }

    ProfiledPIDController thetaController = new ProfiledPIDController(1, 0, 0, Constants.thetaControllerConstraints);
     thetaController.enableContinuousInput(-Math.PI, Math.PI);


     if(resetOdometry){
      drivetrain.resetOdometry(trajectory.getInitialPose());
    }

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand( 
      trajectory,
     drivetrain::getPose, 
     Constants.kinematics,
     new PIDController(1, 0, 0),
     new PIDController(1, 0, 0),
     thetaController,
     drivetrain::setModuleStates,
     drivetrain
    );

    
    return swerveControllerCommand;
     



  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
   public Command getAutonomousCommand() {
    //An ExampleCommand will run in autonomous
    return chooser.getSelected();
   }

    


}
