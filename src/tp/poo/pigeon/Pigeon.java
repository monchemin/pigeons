package tp.poo.pigeon;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

//Definis la forme, position et implante les d�placements d'un pigeon
public class Pigeon extends Parent {
	protected int positionX; //abscisse actuelle
	protected int positionY; //ordonn�e actuelle
	protected int positionInitialX; //abscisse intitiale
	protected int positionInitialY; //ordonn�e intitiale
	protected Color color; //couleur
	protected int vitesse; // vitesse aleatoire du pigeo
	
	
	public Pigeon(int posX, int posY, Color color) {
		// a la cr�ation la postion actuelle est identique � l'initiale
		this.positionX = posX;
		this.positionY = posY;
		this.positionInitialX = posX;
		this.positionInitialY = posY;
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
		this.setVitesse();
		this.setPosition();
	}
	
	protected void setPosition()// d�placement
	{
		this.setTranslateX(this.positionX); //position x du pigeon sur la scene
		this.setTranslateY(this.positionY);
	}
	public void setInitial()
	{ // chaque pigon prend revient � sa position initial
		Timeline timeline = new Timeline();
        
        Duration duration = Duration.millis(Config.INITIAL);
        KeyValue keyValueX = new KeyValue(this.translateXProperty(), this.positionInitialX);
        KeyValue keyValueY = new KeyValue(this.translateYProperty(), this.positionInitialY);
        KeyFrame keyFrameX = new KeyFrame(duration , keyValueX);
        KeyFrame keyFrameY = new KeyFrame(duration ,keyValueY); 
       
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
 
        timeline.play();	
	}
	public void deplacement(double x,double y)
	{ 
		// chaque pigeon prend revient � sa position initial
		Timeline timeline = new Timeline();
        
        Duration duration = Duration.millis(Config.INITIAL);
        KeyValue keyValueX = new KeyValue(this.translateXProperty(), x);
        KeyValue keyValueY = new KeyValue(this.translateYProperty(), y);
        KeyFrame keyFrameX = new KeyFrame(duration , keyValueX);
        KeyFrame keyFrameY = new KeyFrame(duration ,keyValueY); 
      
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
 
        timeline.play();
		
	}
	
	public void setVitesse()
	{
		this.vitesse = (new Random().nextInt(10) + 1)*Config.TEMPSDEPLACEMENT/5;
	}
}
