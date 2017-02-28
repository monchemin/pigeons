package tp.poo.pigeon;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Pigeon extends Parent {
	protected int positionX; //abscisse 
	protected int positionY; //ordonn�e
	protected Color color; //couleur
	
	
	public Pigeon(int posX, int posY, Color color) {
		this.positionX = posX;
		this.positionY = posY;
		this.color = color;
		this.setPigeon();
	}
	
	protected void setPigeon()
	{
		//creation de pigeon
		Polygon unPigeon = new Polygon(); // le pigeon est juste un triangle (polygone � trois points)
		unPigeon.getPoints().addAll(new Double[]{
	            0.0, 0.0,
	            20.0, 10.0,
	            10.0, 20.0 });
		
		unPigeon.setFill(this.color);
		//positionnement du pigeon
		this.getChildren().add(unPigeon); // ajout du polygone � l'objet pigeon 
		this.setPosition(); // position y du pigeon sur la scene
		
	}
	public void moov(int newposX , int newposY)
	{
		this.positionX = newposX;
		this.positionY = newposY;
		this.setPosition();
	}
	
	protected void setPosition()// d�placement
	{
		this.setTranslateX(this.positionX); //position x du pigeon sur la scene
		this.setTranslateY(this.positionY);
	}
}
