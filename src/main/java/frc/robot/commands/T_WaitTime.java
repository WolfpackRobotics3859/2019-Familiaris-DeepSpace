/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class T_WaitTime extends Command {
  private Timer timer1 = new Timer();
  private double secondsToWait;
  public T_WaitTime(double seconds) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    secondsToWait = seconds;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    timer1.start();
    Robot._robotTime.timePrintln("[TIME] Waiting " + secondsToWait + " seconds.");

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(timer1.get()>secondsToWait){
      timer1.stop();
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    timer1.reset();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
