/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class C_IntakeManualControl extends Command {
  public C_IntakeManualControl() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot._Shooter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot._robotTime.timePrintln("[SHOOTER] Warning - Enabling Manual Override - We are really in trouble now!!!");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot._Shooter.manualShooter(-OI.xbox2.getRawAxis(5));
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
