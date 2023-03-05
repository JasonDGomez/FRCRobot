package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import frc.robot.Constants;

public class SwerveMotor {
     public final TalonFX motor;
     private double supplyCurrentLimit;
     private double nominalVoltage = 12.0;

     public SwerveMotor(int id, double supplyCurrentLimit, boolean isInverted){

         this.supplyCurrentLimit = supplyCurrentLimit;

         TalonFXConfiguration motorConfig = new TalonFXConfiguration();
         motorConfig.supplyCurrLimit.currentLimit = supplyCurrentLimit;
         motorConfig.supplyCurrLimit.enable = true;
         motorConfig.voltageCompSaturation = nominalVoltage;
         
         motor = new TalonFX(id);
         motor.configAllSettings(motorConfig);
         motor.setNeutralMode(NeutralMode.Brake);

         motor.setInverted(isInverted);
         

     }

     public SwerveMotor(int id, double supplyCurrentLimit, double kP, double kI, double kD){

         this.supplyCurrentLimit = supplyCurrentLimit;

         motor = new TalonFX(id);

         TalonFXConfiguration motorConfig = new TalonFXConfiguration();
         motorConfig.supplyCurrLimit.currentLimit = supplyCurrentLimit;
         motorConfig.supplyCurrLimit.enable = true;

         motorConfig.slot0.kP = kP;
         motorConfig.slot0.kI = kI;
         motorConfig.slot0.kD = kD;
        
         
         motor.configAllSettings(motorConfig);
         motor.setNeutralMode(NeutralMode.Brake);

     }


     public void setSteerMotor(double goalAngleRadians){
         // / 2 * Math.PI * 2048.0 * 12.8;

         goalAngleRadians %= (2.0 * Math.PI);

         if(goalAngleRadians < 0.0){
             goalAngleRadians += (2.0 * Math.PI);
         }

         double currentAngleInRadians = getMotorEncoder() / 2048.0 * 2.0 * Math.PI / Constants.STEER_GEAR_REDUC; 

         double currentAngleInRadiansMod = currentAngleInRadians % (2.0 * Math.PI);

         if(currentAngleInRadiansMod < 0.0){
             currentAngleInRadiansMod += (2.0 * Math.PI);
         }

         double adjustedGoalAngleRadians = goalAngleRadians + currentAngleInRadians - currentAngleInRadiansMod;

         if(goalAngleRadians - currentAngleInRadiansMod > Math.PI){
             adjustedGoalAngleRadians -= 2.0 * Math.PI;
         } else if (goalAngleRadians - currentAngleInRadiansMod < -Math.PI){
             adjustedGoalAngleRadians += 2.0 * Math.PI;
         }


         double adjustedGoalAngleTicks = adjustedGoalAngleRadians / (2.0 * Math.PI) * 2048.0; // Might need to take out the 12.8 will see
          
         motor.set(ControlMode.Position, adjustedGoalAngleTicks * Constants.STEER_GEAR_REDUC);
  
     }

     public void setDriveMotor(double velocity){
        // double output = (velocity * 60.0 / (Math.PI * Constants.WHEEL_DIAMETER)) * 6.12;

        if(velocity == 0){
            motor.set(ControlMode.PercentOutput, -0.05);
        }
         
        else{
         motor.set(ControlMode.PercentOutput, velocity / Constants.MAX_VELOCITY_METERS_PER_SECOND);
        }
        
     }

     
     public double getMotorEncoder(){
         return motor.getSelectedSensorPosition();
     }

     public void setMotorEncoder(double val){
          motor.setSelectedSensorPosition(0);
     }
 


      

     


     
}
