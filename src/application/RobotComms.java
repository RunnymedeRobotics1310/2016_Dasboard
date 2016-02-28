package application;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.IRemote;
import edu.wpi.first.wpilibj.tables.IRemoteConnectionListener;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class RobotComms {

	private Controller controller;
	ITable smartDashboard;
	ITable autoChooser;
	boolean connected = false;
	
	IRemoteConnectionListener connectionListener = new IRemoteConnectionListener() {

		@Override
		public void connected(IRemote remote) {
			connected = true;
			addSmartdashboardListener();
		}

		@Override
		public void disconnected(IRemote remote) {
			connected = false;
//			removeSmartDashboardListener();
		}};
		
	
	public RobotComms(Controller controller) {
		this.controller = controller;

		// Initialize access to the network tables for the robot
		NetworkTable.setClientMode();
		NetworkTable.setTeam(1312);
		NetworkTable.setIPAddress("169.254.148.109");
		
		NetworkTable networkTable = NetworkTable.getTable("SmartDashboard");
		networkTable.addConnectionListener(connectionListener, true);
	}

	public void addSmartdashboardListener() {

		NetworkTable networkTable = NetworkTable.getTable("SmartDashboard");
		
		while (!networkTable.isConnected()) {
			synchronized(this) {
				try {
					this.wait(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		smartDashboard = (ITable) networkTable;
		
		System.out.println(smartDashboard.toString());

		System.out.println("Contains Auto Chooser : " + smartDashboard.containsSubTable("Example Auto Chooser"));
		System.out.println("Contains Loop Counter : " + smartDashboard.containsKey("Loop Counter"));
		
		autoChooser = smartDashboard.getSubTable("Example Auto Chooser");
		
		smartDashboard.addTableListener("Loop Counter", 
				new ITableListener() {
					@Override
					public void valueChanged(ITable source, String key, Object value, boolean isNew) {
						// TODO Auto-generated method stub
						double doubleValue = (Double) value;
						System.out.println(doubleValue);
						if (controller != null) {
							controller.setLoopCounter(doubleValue);
						}
					}}, true);

		System.out.println(autoChooser.getString("selected", "Not found"));
		
	}
	
	public void setAutoSelection(String selection) {
		if (connected) {
			if (selection.equals("Auto1")) {
				autoChooser.putString("selected",  "Default Auto");
				System.out.println("Set selected auto to : Default Auto");
			} else if (selection.equals("Auto2")) {
				autoChooser.putString("selected",  "My Auto");
				System.out.println("Set selected auto to : Default Auto");
			}
		}
	}

	

}
