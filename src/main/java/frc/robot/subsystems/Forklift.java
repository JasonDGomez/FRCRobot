package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Forklift extends SubsystemBase {
      private final TalonFX motor;

      public Forklift(){
        motor = new TalonFX(Constants.FORKLIFT_MOTOR_ID);
      }

      public void forkLiftMoveUp(){
         motor.set(ControlMode.PercentOutput, 1);
      }

      public void forkLiftMoveDown(){
        motor.set(ControlMode.PercentOutput, -1);
      }

      public void doNothing(){
        motor.set(ControlMode.PercentOutput, 0);
      }




}
