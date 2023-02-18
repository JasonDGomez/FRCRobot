package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PneumaticSubsystem extends SubsystemBase {
   
    private final DoubleSolenoid solenoids;
  

    public PneumaticSubsystem(){
        solenoids = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.SOLENOID_FORWARD_CHANNEL, Constants.SOLENOID_REVERSE_CHANNEL);
    }

    public void pneumaticExtend(){
        solenoids.set(Value.kForward);
    }

    public void pneumaticRetract(){
        solenoids.set(Value.kReverse);
    }

    public void zeroPneumatic(){
        solenoids.set(Value.kOff);
    }

   
}
