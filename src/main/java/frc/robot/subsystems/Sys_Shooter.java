/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.shooter.C_ShooterStow;

public class Sys_Shooter extends Subsystem {
  private TalonSRX m_ArmTalon;
  private static Sys_Shooter mInstance = null;
  
  public synchronized static Sys_Shooter getInstance() {
    if (mInstance == null){
      mInstance = new Sys_Shooter();
    }
    return mInstance;
  }

  public synchronized int praiseTheOverseer(){
    return m_ArmTalon.getSelectedSensorPosition();
  }
  
  public void configureShooter() {
    
    m_ArmTalon= new TalonSRX(RobotMap.shooter_Talon_Arm);
    m_ArmTalon.configFactoryDefault();
    m_ArmTalon.set(ControlMode.PercentOutput, 0);
    m_ArmTalon.setInverted(true); //set this later
    m_ArmTalon.configVoltageCompSaturation(5.5, Constants.kTimeoutMs);
    m_ArmTalon.enableVoltageCompensation(false);
  
    m_ArmTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    m_ArmTalon.setSensorPhase(true); // set this later
    m_ArmTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    m_ArmTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    m_ArmTalon.configNominalOutputForward(0, Constants.kTimeoutMs);
    m_ArmTalon.configNominalOutputReverse(0, Constants.kTimeoutMs);
    m_ArmTalon.configPeakOutputForward(1, Constants.kTimeoutMs);
    m_ArmTalon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    m_ArmTalon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    m_ArmTalon.config_kF(Constants.kSlotIdx, Constants.shooterArmPID.kF, Constants.kTimeoutMs);
    m_ArmTalon.config_kP(Constants.kSlotIdx, Constants.shooterArmPID.kP, Constants.kTimeoutMs);
    m_ArmTalon.config_kI(Constants.kSlotIdx, Constants.shooterArmPID.kI, Constants.kTimeoutMs);
    m_ArmTalon.config_kD(Constants.kSlotIdx, Constants.shooterArmPID.kD, Constants.kTimeoutMs);
    m_ArmTalon.configMotionCruiseVelocity(Constants.shooterCruiseVelocity, Constants.kTimeoutMs); 
    m_ArmTalon.configMotionAcceleration(Constants.shooterMaxAccel, Constants.kTimeoutMs); 
    m_ArmTalon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    m_ArmTalon.setNeutralMode(NeutralMode.Brake);
    m_ArmTalon.configContinuousCurrentLimit(18, 0); //changed
    m_ArmTalon.configPeakCurrentLimit(20, 0); //changed
    m_ArmTalon.configPeakCurrentDuration(100, 0); //changed

    System.out.println("[SHOOTER] Initialized.");
  }

  public synchronized void shooterGoTo(double value) {
    if(Robot._Overwatch.isIntakeExtended()){
      m_ArmTalon.set(ControlMode.MotionMagic, value);
    } else {
      m_ArmTalon.set(ControlMode.MotionMagic, m_ArmTalon.getSelectedSensorPosition());
    }
  }

  public synchronized void stop() {
    m_ArmTalon.neutralOutput();
  }

  public void manualShooter(double value) {
        m_ArmTalon.set(ControlMode.PercentOutput, value);
    }

  public synchronized void manualZeroSensor() {
    m_ArmTalon.setSelectedSensorPosition(0);
  }

  public void enableVoltageCompensation(boolean enable){ //new function
    if(enable){
      System.out.println("[SHOOTER] Current Limiting Enabled.");
    } else {
      System.out.println("[SHOOTER] Current Limiting Disabled.");
    }
    m_ArmTalon.enableVoltageCompensation(enable);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_ShooterStow());
  }
}
