package teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;
import java.util.Observer;

import teamcode.pedroPathing.constants.FConstants;
import teamcode.pedroPathing.constants.LConstants;
import teamcode.robot.subsystems.Arm;
import teamcode.robot.subsystems.BoxtubeExtension;
import teamcode.robot.subsystems.BoxtubePivot;
import teamcode.robot.subsystems.Claw;
import teamcode.utils.MyTelem;

@Config
public class Robot {
    Follower follower;
    Servo wristLeft, wristRight;
    Servo turret;
    public Arm arm;
    public Servo swivel;
    public Servo c;
    Claw claw;

    DcMotor ext;

    BoxtubeExtension boxext;
    DcMotor pivot;
    BoxtubePivot boxtubePivot;

    List<LynxModule> ar;
    public Robot(HardwareMap hm, boolean auto){
        follower = new Follower(hm, FConstants.class, LConstants.class);

        wristLeft = hm.get(Servo.class, "wristLeft");
        wristRight = hm.get(Servo.class, "wristRight");

        wristLeft.setDirection(Servo.Direction.REVERSE);

        turret = hm.get(Servo.class, "turret");

        c = hm.get(Servo.class, "claw");
        swivel = hm.get(Servo.class, "swivel");

        ext = hm.get(DcMotor.class, "extension");
        pivot = hm.get(DcMotor.class, "pivot");

        pivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ext.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        if(auto){
            ext.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        pivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ext.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        claw = new Claw(c, swivel);
        arm = new Arm(wristLeft, wristRight, turret);
        boxext = new BoxtubeExtension(ext);
        boxtubePivot = new BoxtubePivot(pivot);

        CommandScheduler.getInstance().registerSubsystem(claw, arm, boxext, boxtubePivot);

        ar = hm.getAll(LynxModule.class);
        for(LynxModule huh : ar){
            huh.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

    }

    public void update(){
        CommandScheduler.getInstance().run();
        follower.update();

        for(LynxModule hub : ar){
            hub.clearBulkCache();
        }
        MyTelem.addData("HELLO WORLD", true);
        MyTelem.update();
    }
    public void stop(){
        for(LynxModule hub : ar){
            hub.clearBulkCache();
        }
        CommandScheduler.getInstance().reset();
    }
}
