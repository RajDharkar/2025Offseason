package teamcode.robot.commands.subsystemcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamcode.robot.Robot;

public class PivotTo extends CommandBase {
    public Robot robot;

    public PivotTo(Robot robot, int target){
        this.robot = robot;
        robot.boxtubePivot.setTargetPosition(target);
    }

    @Override
    public boolean isFinished(){
        return robot.boxtubePivot.hasReached();
    }
}
