package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zahra Atzuri
 * zfa84
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		if(energy > Params.walk_energy_cost){
			move(direction, 1);
		}
		energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		if(energy > Params.run_energy_cost){
			move(direction, 2);
		}
		energy -= Params.run_energy_cost;
	}


	protected final void move(int direction, int num) {
		switch(++direction){
			case 1:
				x_coord += num;
				break;
			case 2:
				x_coord += num;
				y_coord -= num;
				break;
			case 3:
				y_coord -= num;
				break;
			case 4:
				x_coord -= num;
				y_coord -= num;
				break;
			case 5:
				x_coord -= num;
				break;
			case 6:
				x_coord -= num;
				y_coord += num;
				break;
			case 7:
				y_coord += num;
				break;
			case 8:
				x_coord += num;
				y_coord += num;
				break;
			default:
				break;
		}

		if(x_coord > Params.world_width - 1) {
			x_coord = (x_coord % (Params.world_width - 1)) - 1;
		}
		if(y_coord > Params.world_height - 1) {
			y_coord = (y_coord % (Params.world_height - 1)) - 1;
		}
        if(x_coord < 0) {
            x_coord += Params.world_width;
        }
		if(y_coord < 0) {
			y_coord += Params.world_height;
		}

	}

	protected final void rest(){
		energy -= Params.rest_energy_cost;
	}


	protected final void reproduce(Critter offspring, int direction) {
		if(energy > Params.min_reproduce_energy) {
			offspring.energy = (int) Math.floor(.5 * energy);
			this.energy = (int) Math.ceil(.5 * energy);

			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			offspring.energy += Params.walk_energy_cost;
			offspring.walk(direction);

			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class c = Class.forName(critter_class_name);
			Critter cr = (Critter) c.newInstance();
			cr.energy = Params.start_energy;
			cr.x_coord = getRandomInt(Params.world_width) + 1;
			cr.y_coord = getRandomInt(Params.world_height) + 1;
			population.add(cr);

		}
		catch(ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		for (Critter c : population) {
			if(c.getClass().getName().equalsIgnoreCase(critter_class_name)){
				result.add(c);
			}
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			critter_count.merge(crit_string, 1, (a, b) -> a.intValue() + b);
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	
	public static void worldTimeStep() {
		ArrayList<Integer> movedCritters = new ArrayList<>();	// checks for critters that moved

		for (int i = 0; i < population.size(); i++){
			int x_cord = population.get(i).x_coord;
			int y_cord = population.get(i).y_coord;

			population.get(i).doTimeStep();

			if((x_cord != population.get(i).x_coord) && (y_cord != population.get(i).y_coord)){
				movedCritters.add(i);
			}
		}

		for(int i = 0; i < population.size(); i++){
			for(int j = 0; j < population.size(); j++) {
				if (i != j) {
					Critter cr1 = population.get(i);
					Critter cr2 = population.get(j);
					int x_cord_cr1 = cr1.x_coord;
					int y_cord_cr1 = cr1.y_coord;
					int x_cord_cr2 = cr2.x_coord;
					int y_cord_cr2 = cr2.y_coord;

					if ((cr1.x_coord == cr2.x_coord) && (cr1.y_coord == cr2.y_coord)) {
						// check if fight
						boolean cr1_f = cr1.fight(cr2.toString());
						boolean cr2_f = cr2.fight(cr1.toString());

						// check if moved after fight & add to moved if true
						if (!(cr1.x_coord == cr2.x_coord) && !(cr1.y_coord == cr2.y_coord)) {
							if((cr1.x_coord != x_cord_cr1) && (cr1.y_coord != y_cord_cr1)){
								movedCritters.add(i);
							}
							else if((cr2.x_coord != x_cord_cr2) && (cr2.y_coord != y_cord_cr2)){
								movedCritters.add(j);
							}
						}

						// if both critters moved
						if (movedCritters.contains(i) && movedCritters.contains(j)) {
							selectWinner(cr1,cr2);
						}
						// if one or the other does not move
						else if (!movedCritters.contains(i) || !movedCritters.contains(j)) {

							// check if both want to fight
							if (cr1_f && cr2_f) {
								selectWinner(cr1, cr2);
							}

							// neither wants to fight
							else{

								// check if cr1 doesn't want to fight and doesn't move
								if(!cr1_f && !movedCritters.contains(i)){

									// check if Algae
									if(cr1.toString().equals("@")){
										cr2.energy += cr1.energy;
										cr1.energy = 0;
									}
									else{
										if(cr1.energy > Params.run_energy_cost){
//											cr1.run(0);
										}
										else{
											cr1.walk(0);
										}
									}
								}

								// check if cr2 doesn't want to fight and doesn't move
								if(!cr2_f && !movedCritters.contains(j)){

									// check if Algae
									if(cr2.toString().equals("@")){
										cr1.energy += cr2.energy;
										cr2.energy = 0;
									}
									else{
										if(cr2.energy > Params.run_energy_cost){
											cr2.run(4);
										}
										else{
											cr2.walk(4);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < population.size(); i++) {
			Critter c = population.get(i);

			// take into account resting critters
			if(!movedCritters.contains(c)){
				c.rest();
			}
			//remove dead critters;
			if(c.energy <= 0){
				population.remove(i);
			}
		}

		// add the babies
		for (int i = 0; i < babies.size(); i++) {
			population.add(babies.get(i));

		}
		babies.clear();
	}


	// this is a helper function
	protected static void selectWinner(Critter cr1, Critter cr2){
		int cr1_energy = 0;
		int cr2_energy = 0;

		if (cr1.fight(cr2.toString()) && (cr1.energy > 0)) {
			cr1_energy = getRandomInt(cr1.energy) + 1;
			assert (cr1_energy > 0);
		}
		if (cr2.fight(cr1.toString()) && (cr2.energy > 0)) {
			cr2_energy = getRandomInt(cr2.energy) + 1;
			assert (cr2_energy > 0);
		}

		// if neither want to fight
		if (cr1_energy == 0 && cr2_energy == 0) {
			cr1.energy = 0;
			cr2.energy = 0;
		}
		// if cr1 wants to fight (or both)
		else if (cr1_energy >= cr2_energy) {
			cr1.energy += cr2.energy / 2;
			cr2.energy = 0;
		}
		// if cr2 wants to fight
		else {
			cr2.energy += cr1.energy / 2;
			cr1.energy = 0;
		}
	}


	public static void displayWorld() {
		int height = Params.world_height + 1;
		int width = Params.world_width + 1;
		char[][] world = new char[height + 1][width + 1];

		// add borders
		for(int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				if(i == 0 || i == height){
					world[i][j] = '-';
				}
				else if (j == 0 || j == width){
					world[i][j] = '|';
				}
				else {
					world[i][j] = ' ';
				}
			}
		}
		world[0][0] = '+';
		world[height][0] = '+';
		world[0][width] = '+';
		world[height][width] = '+';

		// add critters
		for(int i = 0; i < population.size(); i++){
			Critter cr = population.get(i);

			if( (cr.x_coord <= width) && (cr.y_coord <= height) && (cr.x_coord > 0) && (cr.y_coord > 0)){
				world[cr.y_coord][cr.x_coord] = cr.toString().charAt(0);
			}
		}

		// print grid
		for(int i = 0; i <= height; i++){
			for (int j = 0; j <= width; j++) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
	}
}
