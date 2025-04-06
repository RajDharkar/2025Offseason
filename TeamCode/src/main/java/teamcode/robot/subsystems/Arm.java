package teamcode.robot.subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.Servo;


public class Arm implements Subsystem {
    public Servo wristLeft;
    public Servo wristRight;
    public Servo turret;

    public static double wristIntake = 0;
    public static double wristSpecDepo = 0;
    public static double wristSample = 0;
    public static double turretIntake = 0;
    public static double turretSpecDepo = 0;
    public static double turretSample = 0;

    public Arm(Servo wristLeft, Servo wristRight, Servo turret){
        this.wristLeft = wristLeft;
        this.wristRight = wristRight;
        this.turret = turret;
    }
    public WristState wristState;
    public TurretState turretState;
    public enum WristState{
        INTAKE, SPEC_DEPO, SAMPLE_DEPO
    }
    public enum TurretState{
        INTAKE, SPEC_DEPO, SAMPLE_DEPO
    }

    public void setWristState(WristState state){
        switch(state){
            case INTAKE:
                wristLeft.setPosition(wristIntake);
                wristRight.setPosition(wristIntake);
                break;
            case SPEC_DEPO:
                wristLeft.setPosition(wristSpecDepo);
                wristRight.setPosition(wristSpecDepo);
                break;
            case SAMPLE_DEPO:
                wristRight.setPosition(wristSample);
                wristLeft.setPosition(wristSample);
                break;
        }
    }

    public void setTurretState(TurretState state){
        switch(state){
            case INTAKE:
                turret.setPosition(turretIntake);
                break;
            case SPEC_DEPO:
                turret.setPosition(turretSpecDepo);
                break;
            case SAMPLE_DEPO:
                turret.setPosition(turretSample);
                break;
        }
    }
}
