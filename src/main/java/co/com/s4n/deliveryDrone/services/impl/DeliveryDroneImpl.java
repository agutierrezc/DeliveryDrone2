package co.com.s4n.deliveryDrone.services.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.com.s4n.deliveryDrone.exceptions.BusinessException;
import co.com.s4n.deliveryDrone.model.Drone;
import co.com.s4n.deliveryDrone.model.NeighborhoodMap;
import co.com.s4n.deliveryDrone.model.commands.IDroneCommand;
import co.com.s4n.deliveryDrone.services.IDeliveryDrone;
import co.com.s4n.deliveryDrone.services.ILogger;

@Service
public class DeliveryDroneImpl implements IDeliveryDrone {

	@Value("${INVALID_COMMANDS_MESSAGE}")
	private String invalidCommnands;

	@Value("${INVALID_MAP_DIMENSIONS_MESSAGE}")
	private String invalidMapDimansions;
	
	@Value("${NOT_IMPLEMENTED_COMMAND_MESSAGE}")
	private String notImplementedCommand;
	
	@Value("${MAP_DIMENSIONS}")
	private String mapDimansions;
	
	@Value("${NUMBER_OF_PACKAGES}")
	private int numberOfPackage;

	@Autowired
	private NeighborhoodMap neighborhoodMap;
	
	@Autowired
	private Drone drone;
	
	@Autowired
	private Map<String, IDroneCommand> droneCommands = new ConcurrentHashMap<String, IDroneCommand>();
	
	@Autowired
	private ILogger logger;
	
	/**
	 * Crea un mapa con x, y posiciones a la redonda
	 * @throws BusinessException
	 */
	public void createMap() throws BusinessException {
		String[] dimansions = mapDimansions.split(",");
		
		if (dimansions.length != 2) {
			throw new BusinessException(invalidMapDimansions);
		}
		try {
			int axisX = Integer.parseInt(dimansions[0]);
			int axisY = Integer.parseInt(dimansions[1]);
			neighborhoodMap.setAxisY(axisY);
			neighborhoodMap.setAxisX(axisX);
		} catch (Exception e) {
			throw new BusinessException(invalidMapDimansions);
		}
	}
	
	@Override
	public NeighborhoodMap getNeighborhoodMap() {
		return neighborhoodMap;
	}

	/**
	 * inicia un serie de entregas, si una entrega no es posible por que se sale del mapa,
	 * muestra un mensaje de error pero continua con las entregas.
	 * La cantidad de entregas esta determinada por el parametro NUMBER_OF_PACKAGES,
	 * Cada vez que supere ese numero el dron debe volver al origen a recoger mas paquetes
	 * @param commands
	 * @throws BusinessException
	 */
	public void startDeliveries(String exportLogName, String... commands) {
		logger.addLine("== Reporte de entregas ==");
				
		int numberOfDeliveries = 0;
		for (String command : commands) {
			try {
				if(numberOfDeliveries >= numberOfPackage){
					goHome();
					numberOfDeliveries = 0;
				}
				if(isValidCommand(command)){
					executeDelivery(command);
				} else {
					logger.addLine(notImplementedCommand);
				}
			} catch (BusinessException e) {
				logger.addLine(e.getMessage());
			}
			logger.addLine(drone.getPositionAsString());
			numberOfDeliveries++;
		}
		logger.export(exportLogName);
	}
	
	public String getLog(){
		return logger.toString();
	}
	
	public void printLog(){
		System.out.println(getLog());
	}
	
	private void executeDelivery(String route) throws BusinessException{
		for (int i = 0; i < route.length(); i++) {
			String command = route.charAt(i) + "";
			IDroneCommand droneCommand = droneCommands.get(command);
			if (droneCommand == null){
				throw new BusinessException(notImplementedCommand);
			}
			droneCommand.executeCommand(drone);
		}
	}
	
	public String getDronePosition(){
		return drone.getPositionAsString();
	}
	
	private boolean isValidCommand(String command){
		if (command.isEmpty())
			return false;
		if (!command.matches("[AID]*"))
			return false;
		return true;
	}
	
	private void goHome() throws BusinessException{
		IDroneCommand droneCommand = droneCommands.get("GoHomeDroneCommand");
		droneCommand.executeCommand(drone);
	}
}
