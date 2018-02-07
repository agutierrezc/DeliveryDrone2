package co.com.s4n.deliveryDrone.model;

import java.awt.Point;

import org.springframework.stereotype.Repository;

@Repository
public class NeighborhoodMap {
	
	private int axisY;
	
	private int axisX;
	
	public NeighborhoodMap(){
		this.axisX = 0;
		this.axisY = 0;
	}

	public int getAxisY() {
		return axisY;
	}

	public void setAxisY(int axisY) {
		this.axisY = axisY;
	}

	public int getAxisX() {
		return axisX;
	}

	public void setAxisX(int axisX) {
		this.axisX = axisX;
	}
	
	//Determina si una posicion es validad de acuerdo a sus ejes
	public boolean isValidPosition(Point position){
		if (position.x >= this.axisX)
			return false;
		if (position.y >= this.axisY)
			return false;
		if (position.x <= this.axisX * -1)
			return false;
		if (position.y <= this.axisY * -1)
			return false;
		return true;
	}

}
