package co.com.s4n.deliveryDrone.services;

import co.com.s4n.deliveryDrone.exceptions.BusinessException;
import co.com.s4n.deliveryDrone.model.NeighborhoodMap;

public interface IDeliveryDrone {
	
	public void createMap() throws BusinessException;
	
	public void startDeliveries(String exportLogName, String... commands);
	
	public String getLog();
	
	public void printLog();
	
	public NeighborhoodMap getNeighborhoodMap();
	
	public String getDronePosition();
}
