/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.hatch.C_HatchStop;

public class Sys_Hatch extends Subsystem {

private DoubleSolenoid dsHatchDeploy;
private static Sys_Hatch mInstance = null;

public synchronized static Sys_Hatch getInstance() {
  if (mInstance == null){
    mInstance = new Sys_Hatch();
  }
  return mInstance;
}

public void configureHatch(){
  dsHatchDeploy = new DoubleSolenoid(RobotMap.hatchDeploy_ds_kForward, RobotMap.hatchDeploy_ds_kReverse);
}

public synchronized void hatchDeploy(boolean f_r){
  if(f_r){
    dsHatchDeploy.set(Value.kForward);
  } else {
    dsHatchDeploy.set(Value.kReverse);
  }
}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_HatchStop());
  }
}
