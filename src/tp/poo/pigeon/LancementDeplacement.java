package tp.poo.pigeon;

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
		
		this.monPigeon.moov((int)this.posX, (int)this.posY);
		
	}

}
