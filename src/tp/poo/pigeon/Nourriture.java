package tp.poo.pigeon;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Nourriture extends Parent {
	protected double positionX;
	protected double positionY;
	protected Long tempsCreation; //temps de creation
	
	public Nourriture(double posX, double posY) {
		this.positionX = posX;
		this.positionY = posY;
		this.tempsCreation = System.currentTimeMillis(); //recuperation du temps de creation pour vérifier la viablité
		//création de la nourriture represente par un circle
		createCercle();	
	}
	protected void createCercle()
	{
		Circle cercle = new Circle();
		cercle.setRadius(20);
		cercle.setFill(Color.FIREBRICK);
		cercle.setCenterX(positionX);
		cercle.setCenterY(positionY);
		this.getChildren().add(cercle);
		
		//this.setVisible(false);
		
		
	}

}
