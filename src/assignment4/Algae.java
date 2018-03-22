package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Algae extends TestCritter {

	public String toString() {
		return "@";
	}
	
	public boolean fight(String not_used) {
		return false;
	}

	public void setX_Coord(){
		super.setX_coord(getRandomInt(Params.world_width));
	}

	public void setY_Coord(){
		super.setY_coord(getRandomInt(Params.world_height));
	}
	
	public void doTimeStep() {
		Algae al = new Algae();
		al.setX_Coord();
		al.setY_Coord();
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
		reproduce(al, Critter.getRandomInt(8));
	}
	// algae is supposed to spawn new ones
}
