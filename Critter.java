package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import org.omg.CORBA.DynAnyPackage.Invalid;

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
		if(energy >= Params.walk_energy_cost){
			switch(direction++){
				case 1:
					x_coord++;
					break;
				case 2:
					x_coord++;
					y_coord--;
					break;
				case 3:
					y_coord--;
					break;
				case 4:
					x_coord--;
					y_coord--;
					break;
				case 5:
					x_coord--;
					break;
				case 6:
					x_coord--;
					y_coord++;
					break;
				case 7:
					y_coord++;
					break;
				case 8:
					x_coord++;
					y_coord++;
					break;
				default: break;
			}
			energy -= Params.walk_energy_cost;
		}
	}
	
	protected final void run(int direction) {
		if(energy >= Params.run_energy_cost){
			switch(direction++){
				case 1:
					x_coord+=2;
					break;
				case 2:
					x_coord+=2;
					y_coord-=2;
					break;
				case 3:
					y_coord-=2;
					break;
				case 4:
					x_coord-=2;
					y_coord-=2;
					break;
				case 5:
					x_coord-=2;
					break;
				case 6:
					x_coord-=2;
					y_coord+=2;
					break;
				case 7:
					y_coord+=2;
					break;
				case 8:
					x_coord+=2;
					y_coord+=2;
					break;
				default: break;
			}
			energy -= Params.run_energy_cost;
		}
	}

	//-------------------------check bounds --------------------------------------------------------------------------//
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
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
			cr.x_coord = getRandomInt(Params.world_width - 1);
			cr.y_coord = getRandomInt(Params.world_height - 1);
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
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
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
		// Complete this method.
	}
	
	public static void worldTimeStep() {
		// Complete this method.
		for(Critter cr : population){
			cr.doTimeStep();
		}
		for(int i = 0; i < population.size(); i++){
			for(int j = i + 1; j < population.size(); j++){
				if((population.get(i).x_coord == population.get(j).x_coord) && (population.get(i).y_coord
						== population.get(j).y_coord)){
					int cr1_energy = 0;
					int cr2_energy = 0;
					if(population.get(i).fight(population.get(j).toString())){
						cr1_energy = getRandomInt(population.get(i).energy - 1) + 1;
					}
					if(population.get(j).fight(population.get(i).toString())){
						cr2_energy = getRandomInt(population.get(j).energy - 1) + 1;
					}

					if(cr1_energy >= cr2_energy){
						population.get(i).energy += population.get(j).energy / 2;
						population.remove(j);
					}
					else{
						population.get(j).energy += population.get(i).energy / 2;
						population.remove(i);
					}

				}
			}
		}
	}
	
	public static void displayWorld() {
		// Complete this method.
		int height = Params.world_height;
		int width = Params.world_width;

		String[][] world = new String[height + 1][width + 1];

		for(int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(i == 0 || i == height - 1){
					world[i][j] = "-";
				}
				else if (j == 0 || j == width - 1){
					world[i][j] = "|";
				}
				else {
					world[i][j] = " ";
				}
			}
		}

		world[0][0] = "+";
		world[height - 1][0] = "+";
		world[0][width - 1] = "+";
		world[height - 1][width - 1] = "+";

		for(Critter cr : population){
			world[cr.y_coord + 1][cr.x_coord + 1] = cr.toString();
		}

		for(String[] s : world){
			System.out.println(s);
		}
	}
}