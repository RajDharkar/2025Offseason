package teamcode.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
public class BoxtubePivot implements Subsystem {
    DcMotor pivot;

    public static double p = 0, i = 0, d = 0, f = 0;
    PIDController extensionPID = new PIDController(p, i, d);
    public double targetPosition;
    public double ticksInDegree = 0; //needs to be updated

    public BoxtubePivot(DcMotor pivot){
        this.pivot = pivot;
        extensionPID.setPID(p, i, d);
        extensionPID.setTolerance(10);
        targetPosition = 0;
    }

    @Override
    public void periodic(){
        extensionPID.setPID(p, i, d);
        int pos = getCurrentPosition();

        double power = extensionPID.calculate(pos, targetPosition);
        double angle = targetPosition / ticksInDegree;
        angle = Math.toRadians(angle);
        pivot.setPower(power + Math.cos(angle));
    }

    public int getCurrentPosition(){
        return pivot.getCurrentPosition();
    }

    public void setTargetPosition(int targetPos){
        targetPosition = targetPos;
    }
    public boolean hasReached(){
        return extensionPID.atSetPoint();
    }

}
