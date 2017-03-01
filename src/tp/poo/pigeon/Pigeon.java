package tp.poo.pigeon;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Pigeon extends Parent {
	protected int positionX; //abscisse actuelle
	protected int positionY; //ordonnée actuelle
	protected int positionInitialX; //abscisse intitial
	protected int positionInitialY; //ordonnée intitial
	protected Color color; //couleur
	
	
	public Pigeon(int posX, int posY, Color color) {
		// a la création la postion actuelle est identique à l'initiale
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
		Polygon unPigeon = new Polygon(); // le pigeon est juste un triangle (polygone à trois points)
		unPigeon.getPoints().addAll(new Double[]{
	            0.0, 0.0,
	            20.0, 10.0,
	            10.0, 20.0 });
		
		unPigeon.setFill(this.color);
		//positionnement du pigeon
		this.getChildren().add(unPigeon); // ajout du polygone à l'objet pigeon 
		this.setPosition(); // position y du pigeon sur la scene
		
	}
	public void moov(int newposX , int newposY)
	{
		this.positionX = newposX;
		this.positionY = newposY;
		this.setPosition();
	}
	
	protected void setPosition()// déplacement
	{
		this.setTranslateX(this.positionX); //position x du pigeon sur la scene
		this.setTranslateY(this.positionY);
	}
	public void setInitial()
	{ // chaque pigon prend revient à sa position initial
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
	{ // chaque pigon prend revient à sa position initial
		Timeline timeline = new Timeline();
        
        Duration duration = Duration.millis(Config.INITIAL);
        KeyValue keyValueX = new KeyValue(this.translateXProperty(), x);
        KeyValue keyValueY = new KeyValue(this.translateYProperty(), y);
        KeyFrame keyFrameX = new KeyFrame(duration , keyValueX);
        KeyFrame keyFrameY = new KeyFrame(duration ,keyValueY); 
       
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
 
        timeline.play();
		
		
		
	}
}
