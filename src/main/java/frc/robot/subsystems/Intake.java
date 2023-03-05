package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private final TalonFX motor;
    
    private boolean intakeOut;

    public Intake(){
        

        motor = new TalonFX(Constants.INTAKE_MOTOR_ID);
        motor.setNeutralMode(NeutralMode.Brake);
        
        intakeOut = false;
    }


    public void intakeItem(DoubleSupplier leftTrigger, DoubleSupplier rightTrigger){


        SmartDashboard.putNumber("Left Trigger", leftTrigger.getAsDouble());
        SmartDashboard.putNumber("Right Trigger", rightTrigger.getAsDouble());

        if(leftTrigger.getAsDouble() > 0 && rightTrigger.getAsDouble() == 0 ){
            motor.set(ControlMode.PercentOutput, leftTrigger.getAsDouble() / 2);
        
        } else if(leftTrigger.getAsDouble() == 0 && rightTrigger.getAsDouble() > 0){
            motor.set(ControlMode.PercentOutput, -rightTrigger.getAsDouble() / 5); 
              
        } else{
            slightReverse();
        }
    }

   // public void releaseItem(){
    //   motor.set(ControlMode.PercentOutput,0);
   // }

    public void doNothing(){
      
        motor.set(ControlMode.PercentOutput,0);
}

    public void slightReverse(){
        motor.set(ControlMode.PercentOutput,-0.05);
    }


}
