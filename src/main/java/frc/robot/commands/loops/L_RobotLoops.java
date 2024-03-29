/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.loops;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;


public class L_RobotLoops extends Command {

  public L_RobotLoops() {
    requires(Robot._Overwatch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot._robotTime.timePrintln("[OVERWATCH] General uninterruptible loops initiated.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot._Overwatch.getGlobalSensorValues();
    Robot._Intake.autoSensorZero();
    Robot._Overwatch.overwatchAssigner(OI.xbox1.getBumper(Hand.kRight), OI.xbox1.getBumper(Hand.kLeft));
    Robot._Overwatch.overwatchManager();
    SmartDashboard.putData(Robot._Intake);
    SmartDashboard.putData(Robot._Shooter);
    SmartDashboard.putData(Robot._Hatch);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
