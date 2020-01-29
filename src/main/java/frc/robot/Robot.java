/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.MotorSafety;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  private final Spark m_rearleftMotor = new Spark(0);
  private final Spark m_rearrightMotor = new Spark(2);

  private final Spark m_frontleftMotor = new Spark(1);
  private final Spark m_frontrightMotor = new Spark(3);

  private final DifferentialDrive m_rearRobotDrive = new DifferentialDrive(m_rearleftMotor, m_rearrightMotor);
  private final DifferentialDrive m_frontRobotDrive = new DifferentialDrive(m_frontleftMotor, m_frontrightMotor);
  private final XboxController m_driverController = new XboxController(0);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_rearrightMotor.setInverted(false);
    m_frontrightMotor.setInverted(false);

    UsbCamera usbCam = CameraServer.getInstance().startAutomaticCapture("USB cam", "/dev/video0");
    usbCam.setResolution(320, 240);
    usbCam.setFPS(60);

    m_rearrightMotor.setSafetyEnabled(false);
    m_frontrightMotor.setSafetyEnabled(false);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

    m_rearRobotDrive.tankDrive(-0.5, -0.5);
  //  m_frontRobotDrive.tankDrive(-3, -3);
    Timer.delay(10.0);
    m_rearRobotDrive.tankDrive(0, 0);
  //  m_frontRobotDrive.tankDrive(0, 0);
    Timer.delay(2.0);
    m_rearRobotDrive.tankDrive(0.5, 0.5);
  //  m_frontRobotDrive.tankDrive(3, 3);
    Timer.delay(10.0);
    m_rearRobotDrive.tankDrive(0, 0);
  //  m_frontRobotDrive.tankDrive(0, 0);

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    m_rearRobotDrive.tankDrive(m_driverController.getY(Hand.kLeft), m_driverController.getY(Hand.kRight));
//    m_frontRobotDrive.tankDrive(m_driverController.getY(Hand.kLeft), m_driverController.getY(Hand.kRight));
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
