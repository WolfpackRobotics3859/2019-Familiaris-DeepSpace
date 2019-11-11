/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class C_TurboDrive extends Command {
  public C_TurboDrive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot._Drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot._robotTime.timePrintln("[DRIVE] Turbo Drive Active.");
    Robot.cShifterEngage.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot._Drive.rapidCurvatureDrive(-OI.xbox1.getRawAxis(1), OI.xbox1.getRawAxis(4));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  Robot.cShifterEngage.finished = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
