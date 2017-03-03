package tp.poo.pigeon;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

//Met a jour les elements suivants
// Retour des pigeons à leurs positions initiale 
// Disparition de la nourriture qui n'est plus fraîche
public class PreparationMouvement  {

	Pigeon monPigeon;
	double posX;
	double posY;
	
	public PreparationMouvement(Pigeon monPigeon, double posX, double posY) {
		this.monPigeon = monPigeon;
		this.posX = posX;
		this.posY = posY;
	}
	
	
	public Timeline moov() {
		
		Timeline timeline = new Timeline();
		this.monPigeon.setVitesse();
        Duration duration = Duration.millis(this.monPigeon.vitesse);
        KeyValue keyValueX = new KeyValue(this.monPigeon.translateXProperty(), this.posX);
        KeyValue keyValueY = new KeyValue(this.monPigeon.translateYProperty(), this.posY);
        KeyFrame keyFrameX = new KeyFrame(duration , keyValueX);
        KeyFrame keyFrameY = new KeyFrame(duration ,keyValueY); 
       
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
 
        //timeline.play();
        return timeline;
        
       
	}

}
