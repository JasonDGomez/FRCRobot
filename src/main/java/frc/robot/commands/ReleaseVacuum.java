package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vacuum;

public class ReleaseVacuum extends CommandBase {
    Vacuum vacuum;

    public ReleaseVacuum(Vacuum vacuum){
       this.vacuum = vacuum;
       addRequirements(vacuum);
    }

   public void initialize(){


   }

   public void execute(){

       
   }

   public void end(boolean interrupted) {
       vacuum.zeroVacuum();
   }

   public boolean isFinished(){
       return false;
   }

}
