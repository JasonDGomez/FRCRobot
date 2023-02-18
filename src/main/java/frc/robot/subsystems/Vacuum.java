package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vacuum extends SubsystemBase {
    private final Spark motorController;

    public Vacuum(){
        motorController = new Spark(Constants.VACUUM_MOTOR_ID);
    }

    public void createVacuum(){
        motorController.set(1);
    }

    public void revertVacuum(){
       motorController.set(-1);
    }

    public void zeroVacuum(){
        motorController.set(0);
    }


}
