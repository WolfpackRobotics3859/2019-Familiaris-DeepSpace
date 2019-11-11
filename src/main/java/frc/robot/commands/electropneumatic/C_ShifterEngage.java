/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.electropneumatic;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ShifterEngage extends Command {
  
  public C_ShifterEngage() {
  }

  @Override
  protected void initialize() {
    Robot._ElectroPneumatic.setShifter(true);
    Robot._robotTime.timePrintln("[E/AIR] Drive high gear engaged.");
  }

  @Override
  protected void execute() {
  }

  public boolean finished = false;
  @Override
  protected boolean isFinished() {
    return finished;
  }

  @Override
  protected void end() {
    Robot._robotTime.timePrintln("[E/AIR] Drive low gear engaged.");
    Robot._ElectroPneumatic.setShifter(false);
    finished = false;
  }

  @Override
  protected void interrupted() {
    end();
  }
}
