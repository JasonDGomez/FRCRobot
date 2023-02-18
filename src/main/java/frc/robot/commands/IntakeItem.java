package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PneumaticSubsystem;

public class IntakeItem extends CommandBase {

         Intake intake;
         DoubleSupplier leftTrigger, rightTrigger;
    

        public IntakeItem(Intake intake, DoubleSupplier leftTrigger, DoubleSupplier rightTrigger ){
            this.intake = intake;
            this.leftTrigger = leftTrigger;
            this.rightTrigger = rightTrigger;
            addRequirements(intake);
        }


        public void execute(){
            intake.intakeItem(leftTrigger,rightTrigger);
         }
      
         public void end(boolean interrupted) {
             intake.doNothing();
         }
      
         public boolean isFinished(){
             return false;
         }



}
