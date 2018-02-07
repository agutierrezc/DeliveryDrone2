package co.com.s4n.deliveryDrone;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.com.s4n.deliveryDrone.exceptions.BusinessException;
import co.com.s4n.deliveryDrone.model.NeighborhoodMap;
import co.com.s4n.deliveryDrone.services.IDeliveryDrone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:DeliveryDrone-beans.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DeliveryDroneTest {
	
	@Value("${INVALID_MAP_DIMENSIONS_MESSAGE}")
	private String invalidMapDimansions;
	
	@Value("${MAP_DIMENSIONS}")
	private String mapDimansions;
	
	@Value("${INVALID_COMMANDS_MESSAGE}")
	private String invalidCommnands;
	
	@Value("${NOT_IMPLEMENTED_COMMAND_MESSAGE}")
	private String notImplementedCommand;
	
	@Value("${NUMBER_OF_PACKAGES}")
	private int numberOfPackage;
	
	@Autowired
	private IDeliveryDrone iDeliveryDrone;
	
	@Test
	public void createMapTest() { 
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			
			String currentMap = neighborhoodMap.getAxisX() + "," + neighborhoodMap.getAxisY();
			assertEquals(mapDimansions, currentMap);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateCorrectPointInMap(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(-9, -9);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(true, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateCorrectPointInMap2(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(9, 9);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(true, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateCorrectPointInMap3(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(-9, 9);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(true, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateCorrectPointInMap4(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(9, -9);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(true, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIncorrectPointInMap(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(-10, -10);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(false, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIncorrectPointInMap2(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(10, 10);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(false, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIncorrectPointInMap3(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(-10, 10);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(false, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIncorrectPointInMap4(){
		try {
			iDeliveryDrone.createMap();
			NeighborhoodMap neighborhoodMap = iDeliveryDrone.getNeighborhoodMap();
			Point position = new Point(10, -10);
			boolean isValidPosition = neighborhoodMap.isValidPosition(position);
			
			assertEquals(false, isValidPosition);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendDelivery() throws BusinessException{
		String[] routs = {"AAAAIAAD"};
		String expectedPosition = "(-2, 4) dirección Norte";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendThreeDeliveries() throws BusinessException{
		String[] routs = {"AAAAIAAD", "DDAIAD","AAIADAD"};
		String expectedPosition = "(0, 0) dirección Occidente";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendFourDeliveries() throws BusinessException{
		String[] routs = {"AAAAIAAD", "DDAIAD","AAIADADAAI", "AADAA"};
		String expectedPosition = "(2, 2) dirección Oriente";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendManyDeliveries() throws BusinessException{
		String[] routs = {"AAIAAADADAAI", "AADAA","AAAAIAAD", "DDAIAD", "IIAAAADA", "DAIADDDAA", "AAAAAAADDAAAAIIIAAA"};
		String expectedPosition = "(-3, 3) dirección Occidente";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendOutOfLimitsDelivery() throws BusinessException{
		String[] routs = {"AAAAAAAAAAAD"};
		String expectedPosition = "(0, 9) dirección Norte";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendWrongDelivery() throws BusinessException{
		String[] routs = {"AAAAAN"};
		String expectedPosition = "(0, 0) dirección Norte";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}
	
	@Test
	public void sendWrongDeliveries() throws BusinessException{
		String[] routs = {"AAAAAD", "AAAAAI", "AAAN"};
		String expectedPosition = "(5, 5) dirección Norte";
		iDeliveryDrone.createMap();
		iDeliveryDrone.startDeliveries("out.txt",routs);
		iDeliveryDrone.printLog();
		
		assertEquals(expectedPosition, iDeliveryDrone.getDronePosition());
	}

}
