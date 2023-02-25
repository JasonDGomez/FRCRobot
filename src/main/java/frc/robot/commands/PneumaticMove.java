package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticSubsystem;

public class PneumaticMove extends CommandBase {

    PneumaticSubsystem pneumatics;
    DoubleSupplier leftTrigger, rightTrigger;
    BooleanSupplier rightBumper;
    boolean intakeOut;


    public PneumaticMove(PneumaticSubsystem pneumatics, DoubleSupplier leftTrigger, DoubleSupplier rightTrigger, BooleanSupplier rightBumper){
        this.pneumatics = pneumatics;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;
        this.rightBumper = rightBumper;
        intakeOut = false;
        addRequirements(pneumatics);
    }


    public void initialize(){
    

    }
 
    public void execute(){
        if(rightTrigger.getAsDouble() > 0 || rightBumper.getAsBoolean() && !intakeOut){
            pneumatics.pneumaticExtend();
            intakeOut = true;
        } else if((rightTrigger.getAsDouble() == 0 && leftTrigger.getAsDouble() == 0) && !rightBumper.getAsBoolean() && intakeOut){
            pneumatics.pneumaticRetract();
            intakeOut = false;
        } else{
            pneumatics.zeroPneumatic();
        }
    }
 
    public void end(boolean interrupted) {
        pneumatics.zeroPneumatic();
    }
 
    public boolean isFinished(){
        return false;
    }
}
