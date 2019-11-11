/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.loops.L_RobotLoops;
import frc.robot.OI;

public class Sys_Overwatch extends Subsystem {

  //Retrieve Values for all On-Board Sensors
  private double encoder_intake_arm;
  private double encoder_shooter_arm;

  public void getGlobalSensorValues(){
    encoder_intake_arm = Robot._Intake.praiseTheOverseerWithEncoderValues();
    encoder_shooter_arm = Robot._Shooter.praiseTheOverseer();
  }

  public synchronized boolean isIntakeExtended(){
    if(encoder_intake_arm<(Constants.overwatch_intake_IsClear)){
      return true;
    } else {
      return false;
    }
  }

  public synchronized boolean isShooterStowed(){
    if(encoder_shooter_arm<(Constants.overwatch_shooter_isStowed)){
      return true;
    } else {
      return false;
    }
  }

  public int shotSelector = 1;
  public synchronized double selectedShooterPosition() {
    if(shotSelector==1){
      return Constants.shooter_ForwardShot;
    } else {
      return Constants.shooter_RearShot;
    }
  }
  public synchronized double selectedShooterSpeed() {
    if(shotSelector==1){
      return Constants.wheels_SHOT_shootingF;
    } else {
      return Constants.wheels_SHOT_shootingR;
    }
  }
  
  public synchronized void shotManager(boolean forwardOrRear) {
    if(forwardOrRear){
      Robot._robotTime.timePrintln("[OVERWATCH] Forward shot selected.");
      shotSelector = 1;
    } else {
      Robot._robotTime.timePrintln("[OVERWATCH] Rear shot selected.");
      shotSelector = 2;
    }
  }

  private boolean intakeOut = false;
  private boolean intakeSweep = false;
  private boolean intakeStow = true;

  private boolean shooterOut = false;
  private boolean shooterStow = true;

  private boolean wheelsIntake = false;
  private boolean wheelsShoot = false;
  private boolean wheelsHold = true;

  private boolean ePneumaticHook = false;

  private boolean ignoreLeftInput = false;
  public boolean climbOverride = false;
  private int mode = 0;
  public void overwatchAssigner(boolean isRBpressed, boolean isLBpressed){
    
    double leftInput = OI.xbox1.getTriggerAxis(Hand.kLeft);
    double rightInput = OI.xbox1.getTriggerAxis(Hand.kRight);
    

    if(rightInput>0.1){
      ignoreLeftInput = true;
    } else {
      ignoreLeftInput = false;
    }
    //Places intake priority over shooter to prevent command conflicts on the intake arm.

  if(!climbOverride){
    if(!ignoreLeftInput){
      if(leftInput>=0.1 && leftInput<0.5){
        mode = 1;
      }
      if(leftInput>=0.5 && leftInput<0.95){
        mode = 2;
      }
      if(leftInput>=0.95){
        mode = 3;
      } else if(leftInput<0.1) {
        mode = 4;
      }
    } 
    if(ignoreLeftInput) {
      if(rightInput>0.1){
        mode = 5;
      } else {
        mode = 6;
      }
    }
    } else {
      mode = 7;
  }

    switch(mode){
      case 1: intakeOut = true;
              ePneumaticHook = true;
              intakeStow = false;
              shooterOut = false;
              shooterStow = true;
              wheelsIntake = false;
              wheelsHold = true;
              break;
      case 2: intakeOut = true;
              ePneumaticHook = true;
              intakeStow = false;
              shooterOut = true;
              shooterStow = false;
              wheelsIntake = false;
              wheelsHold = true;
              break;
      case 3: intakeOut = true;
              ePneumaticHook = true;
              intakeStow = false;
              shooterOut = true;
              shooterStow = false;
              wheelsIntake = false;
              wheelsHold = false;
              break;
      case 4: intakeOut = false;
              ePneumaticHook = false;
              intakeStow = true;
              shooterOut = false;
              shooterStow = true;
              wheelsIntake = false;
              wheelsHold = true;
              shotSelector = 1;
              break;
      case 5: intakeOut = true;
              ePneumaticHook = false;
              intakeStow = false;
              shooterOut = false;
              shooterStow = true;
              wheelsIntake = true;
              wheelsHold = false;
              break;
      case 6: intakeOut = false;
              ePneumaticHook = false;
              intakeStow = true;
              shooterOut = false;
              shooterStow = true;
              wheelsIntake = false;
              wheelsHold = true;
              break;
      case 7: intakeOut = true;
              ePneumaticHook = false;
              intakeStow = false;
              shooterOut = false;
              shooterStow = false;
              wheelsIntake = false;
              wheelsHold = true;
    }
    intakeSweep = isRBpressed;
    wheelsShoot = isLBpressed;
  }
  
  public void overwatchManager(){
    if(intakeOut && !intakeSweep){
      Robot.cIntakeOut.start();
    }
    if(intakeStow && !intakeSweep){
      if(encoder_intake_arm <= -100){
      Robot.cIntakeStow.start();
      } else {
      Robot.cIntakeStaticVoltageHome.start();
      }
    }
    if(shooterOut){
      Robot.cShooterShootPosition.start();
    }
    if(shooterStow){
      Robot.cShooterStow.start();
    }
    if(wheelsIntake){
      Robot.cWheelsIntake.start();
    }
    if(wheelsShoot){
      Robot.cWheelsShoot.start();
    }
    if(wheelsHold){
      Robot.cWheelsHold.start();
    }
    if(intakeSweep){
      Robot.cIntakeSweep.start();
    }
    if(ePneumaticHook){
      if(!OI.xbox2.getAButton()){
      Robot.cHookEngage.finished = false;
      Robot.cHookEngage.start();
      }
    }
    if(!ePneumaticHook){
      Robot.cHookEngage.finished = true;
    }
    if(mode==7){
      Robot.cShooterManualControl.start();
    }
  } 
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new L_RobotLoops());
  }
}
