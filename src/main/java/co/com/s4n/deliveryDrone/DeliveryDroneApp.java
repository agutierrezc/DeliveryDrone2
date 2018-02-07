package co.com.s4n.deliveryDrone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.s4n.deliveryDrone.exceptions.BusinessException;
import co.com.s4n.deliveryDrone.services.IDeliveryDrone;

public class DeliveryDroneApp {
	private final static String BEANS_FILE = "DeliveryDrone-beans.xml";
	private static ApplicationContext context;
	
	public static void main(String[] args) throws BusinessException, IOException {
		
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		
		IDeliveryDrone iDeliveryDrone = context.getBean(IDeliveryDrone.class);
		iDeliveryDrone.createMap();
		
		if(args.length == 1){
			String filePath = args[0];
			
			String[] commands = DeliveryDroneApp.getCommandsFile(filePath);
			String outputFile = "out.txt";
			
			if (commands != null && commands.length > 0){
				iDeliveryDrone.startDeliveries(outputFile, commands);
			}
		} else {
			System.out.println("Es necesario ingresar la ruta del archivo in.txt");
		}
	}
	
	public static String[] getCommandsFile(String filePath){
		List<String> commands = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line;
			
			while ((line = br.readLine()) != null) {
				commands.add(line);
			}
			
			String[] commandsArray = new String[commands.size()];
			commands.toArray(commandsArray);
			
			return commandsArray;
		} catch (IOException e) {
			System.out.println("No se encuentro el archivo " + filePath);
		}
		return null;
	}
}
