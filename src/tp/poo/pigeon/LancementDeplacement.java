package tp.poo.pigeon;



import javafx.animation.Timeline;


public class LancementDeplacement implements Runnable {
	
	Timeline timeline;
	
	public LancementDeplacement(Timeline timeline) {
		this.timeline = timeline;		
	}
	
	@Override
	public void run() {

        timeline.play();  
	}

}
