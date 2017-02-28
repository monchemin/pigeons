package tp.poo.pigeon;



import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class LancementDeplacement implements Runnable {

	Pigeon monPigeon;
	double posX;
	double posY;
	public LancementDeplacement(Pigeon monPigeon, double posX, double posY) {
		this.monPigeon = monPigeon;
		this.posX = posX;
		this.posY = posY;
	}
	@Override
	public void run() {
		
		Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        Duration duration = Duration.millis(5000);
        KeyValue keyValueX = new KeyValue(this.monPigeon.translateXProperty(), this.posX);
        KeyValue keyValueY = new KeyValue(this.monPigeon.translateYProperty(), this.posY);
        KeyFrame keyFrameX = new KeyFrame(duration , keyValueX);
        KeyFrame keyFrameY = new KeyFrame(duration ,keyValueY); 
       
        
        //add the keyframe to the timeline
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
 
        timeline.play();
        //timer.start();
		
		//this.monPigeon.moov((int)this.posX, (int)this.posY);
		
	}

}
