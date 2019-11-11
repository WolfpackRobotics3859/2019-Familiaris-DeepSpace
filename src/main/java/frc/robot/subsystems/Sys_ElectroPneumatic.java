/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Sys_ElectroPneumatic extends Subsystem {
  
  private DoubleSolenoid hatch_claw, drive_shifter, climb_release; {
  hatch_claw = new DoubleSolenoid(RobotMap.hatch_ds_kForward,RobotMap.hatch_ds_kReverse);
  drive_shifter = new DoubleSolenoid(RobotMap.shifter_ds_kForward,RobotMap.shifter_ds_kReverse);
  climb_release = new DoubleSolenoid(RobotMap.climb_ds_kForward, RobotMap.climb_ds_kReverse);
  }

  public void configureSuperStructure() {
    drive_shifter.set(DoubleSolenoid.Value.kForward);
    hatch_claw.set(DoubleSolenoid.Value.kForward);
    climb_release.set(DoubleSolenoid.Value.kForward);
  }

  public synchronized void setShifter(boolean forward_reverse) {
    if(forward_reverse) {
      drive_shifter.set(DoubleSolenoid.Value.kReverse);
    } else {
      drive_shifter.set(DoubleSolenoid.Value.kForward);
    }
  }

  public synchronized void setClimb(boolean forward_reverse) {
    if(forward_reverse) {
      climb_release.set(DoubleSolenoid.Value.kReverse);
    } else {
      climb_release.set(DoubleSolenoid.Value.kForward);
    }
  }
  
  public synchronized void setHook(boolean forward_reverse) {
    if(forward_reverse) {
      hatch_claw.set(DoubleSolenoid.Value.kReverse);
    } else {
      hatch_claw.set(DoubleSolenoid.Value.kForward);
    }
  }
  
  @Override
  public void initDefaultCommand() {
  }
}
