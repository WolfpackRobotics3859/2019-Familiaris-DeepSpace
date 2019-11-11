/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_TimedDrive extends Command {
  private Timer timer1 = new Timer();
  private double driveSpeed,dSteer;
  private double duration;
  
  public C_TimedDrive(double time, double speed, double steer) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot._Drive);
    duration = time;
    driveSpeed = speed;
    dSteer = steer;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot._robotTime.timePrintln("[DRIVE] Timed drive at " + driveSpeed + " power for " + duration + "seconds.");
    timer1.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot._Drive.setDrive(driveSpeed, dSteer);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(timer1.get()>=duration){
      timer1.stop();
      return true;
    } else{
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    timer1.reset();
    Robot._robotTime.timePrintln("[DRIVE] Timed Drive Terminated.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
