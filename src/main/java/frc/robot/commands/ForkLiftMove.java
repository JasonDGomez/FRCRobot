package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Forklift;

public class ForkLiftMove extends CommandBase {
     private final Forklift forklift;
     private final BooleanSupplier buttonA, buttonY;

     public ForkLiftMove(Forklift forklift, BooleanSupplier buttonA, BooleanSupplier buttonY){
        this.forklift = forklift;
        this.buttonA = buttonA;
        this.buttonY = buttonY;
        addRequirements(forklift);
     }

     public void execute(){
        if(buttonA.getAsBoolean() && !buttonY.getAsBoolean()){

          forklift.forkLiftMoveDown();

        } else if(buttonY.getAsBoolean() && !buttonA.getAsBoolean()){

          forklift.forkLiftMoveUp();

        } else{
            forklift.doNothing();
        }
     }
  
     public void end(boolean interrupted) {
         forklift.doNothing();
     }
  
     public boolean isFinished(){
         return false;
     }




}
