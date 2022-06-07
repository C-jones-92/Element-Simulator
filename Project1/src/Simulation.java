//TO DO: Add your name (as an author), complete the required methods.

/**
 *  The simulator. This tracks the elements in a grid
 *  and coordinates that with the display.
 *  
 *  @author Dave Feinberg, K. Raven Russell, and Corey Jones
 */
public class Simulation {

	//******************************************************
	//*******  DO NOT EDIT ANYTHING IN THIS SECTION  *******
	//*******        But do read this section!       *******
	//******************************************************

	/**
	 *  The default number of rows the grid should have.
	 */
	public static final int INIT_ROWS = 120;

	/**
	 *  The default number of columns the grid should have.
	 */
	public static final int INIT_COLS = 80;

	/**
	 *  The grid that represents the location of each element.
	 */
	private final DynamicArray<DynamicArray<Element>> grid;

	/**
	 *  The GUI display.
	 */
	private final Display display;

	//******************************************************
	//* END DO-NOT-EDIT SECTION -- DO NOT ADD MORE FIELDS! *
	//******************************************************

	/**
	 *  Sets up the instance variables (grid and display).
	 *  
	 *  @param withDisplay whether or not the display should be created (for testing purposes)
	 */
	public Simulation(boolean withDisplay) {
		// Initialize the grid (see above) to the default size (see above).
		// Fill the grid with empty void.

		this.grid = new DynamicArray<DynamicArray<Element>>();
		Empty empty = new Empty();

		for(int i = 0; i < INIT_ROWS; i++) {
			this.grid.add(i, new DynamicArray<Element>());
			for(int j = 0; j < INIT_COLS; j++) {
				this.grid.get(i).add(j, empty);
			}
		}


		// If the simulation should be created with a display, then set display
		// (see above) to a new display. Use the title "Project 1 Simulation",
		// the default number of rows and columns (see above), and the display
		// constructor (see Display.java) to do this.

		if(withDisplay) {
			this.display = new Display("Project 1 Simulation", 120, 80);
		}

		else
			this.display = null;
		// If the simulation should be created without a display, then initialize
		// the display to null (or Java will yell at you).


	}

	/**
	 *  This is called when the user clicks on a location using the given tool.
	 *  
	 *  @param row the row where the action happened
	 *  @param col the column where the action happened
	 *  @param newElem the element the user has created to put there
	 */
	public void locationClicked(int row, int col, Element newElem) {
		// Put the new element in the grid at the row and column indicated.
		this.grid.get(row).set(col, newElem);
	}

	/**
	 *  Copies each element of grid's color into the display.
	 */
	public void updateDisplay() {
		// Update each cell of the display (see above) with
		// the correct color for that cell. The display has one
		// cell per grid element (one-to-one) and the color of
		// the cell is just the color of the element from the grid.

		// Remember: Display has a setColor(row, col, color) method,
		// and elements have a getColor() method.

		for(int i = 0; i< this.grid.size(); i++) {
			for(int j = 0; j < this.grid.get(i).size(); j++) {

				this.display.setColor(i, j, this.grid.get(i).get(j).getColor());
			}

		}

	}

	/**
	 *  Resizes the grid (if needed) to a bigger or smaller size.
	 *  
	 *  @param numRows the new number of rows the grid should have
	 *  @param numCols the new number of columns the grid should have
	 *  @return whether or not anything was changed
	 */
	public boolean resize(int numRows, int numCols) {
		//Checks for valid row and columns
		while(numRows >= 1 && numCols >= 1) {
			Empty c = new Empty();
			//checks for both positive width and height change and adjusts rows and columns
			if(numRows >= this.grid.size() && numCols >= this.grid.get(0).size()) {

				for(int i = 0; i < numRows - this.grid.size(); i++) {
					this.grid.add(i, new DynamicArray<Element>());
					for(int j = 0; j < numCols; j++) {
						this.grid.get(i).add(j, c);
					}
				}
				
				for(int i = 0; i < numCols - this.grid.get(0).size(); i++) {
					for(int j = 0; j < this.grid.size();j++) {
						this.grid.get(j).add(c);
					}
				}

			}
			
			//check for positive height change and adjusts rows
			else if(numRows >= this.grid.size()) {				
				for(int i = 0; i < numRows - this.grid.size(); i++) {
					this.grid.add(i, new DynamicArray<Element>());
					for(int j = 0; j < numCols; j++) {
						this.grid.get(i).add(j, c);
					}
				}
			}
			//check for positive width change and adjusts columns
			else if(numCols >= this.grid.get(0).size()) {
				for(int i = 0; i < numCols - this.grid.get(0).size(); i++) {
					for(int j = 0; j < this.grid.size();j++) {
						this.grid.get(j).add(c);
					}
				}
			}

			// Removed rows are just removed.
			//checks for negative width and height change and adjusts rows and columns
			if( numRows <this.grid.size() && numCols < this.grid.get(0).size()){
				for(int i = 0; i < this.grid.size() - numRows; i++) {
					this.grid.remove(0);
				}
				for(int i = this.grid.get(0).size() - 1; i > numCols; i--) {
					for(int j = 0; j < this.grid.size();j++) {

						Element temp = this.grid.get(j).remove(i);
						if(temp.getWeight() >= this.grid.get(j).get(i-1).getWeight()) {
							this.grid.get(j).set(i-1, temp);
							temp.push(grid, j, i-1);


						}
					}
				}
			}
			//checks for negative height change and adjusts rows
			else if(numRows < this.grid.size()) {
				for(int i = 0; i < this.grid.size() - numRows; i++) {
					this.grid.remove(0);
				}
			}
			//checks for negative width change and adjusts columns
			else if(numCols < this.grid.get(0).size()) {
				for(int i = this.grid.get(0).size() - 1; i > numCols; i--) {
					for(int j = 0; j < this.grid.size();j++) {

						Element temp = this.grid.get(j).remove(i);
						if(temp.getWeight() >= this.grid.get(j).get(i-1).getWeight()) {
							this.grid.get(j).set(i-1, temp);
							temp.push(grid, j, i-1);


						}
					}
				}
			}
			return true;
		}
		// Removed columns work as follows:
		//    If the removed element is as heavy (or heavier)
		//        than the element to the left, it will try
		//        to push that element to the left. If it
		//        can push it, then it takes its place.
		//        Remember: the push() method will move the
		//        element if possible, don't reinvent the wheel!
		//    If the removed element is lighter or can't
		//        be pushed, the removed element is just lost.

		// It would be very, very wise to make yourself some private
		// helper methods to do this part of the project.
		return false;
	}

	/**
	 *  Indicates the private post where you have shown off your
	 *  new element(s).
	 *  
	 *  @return the post where you describe your new element
	 */
	public static String newElementPost() {
		//[GUI:Advanced] change this to return the FULL URL of
		//the post where the grader can find your new element
		//demo, but ONLY if you completed the [GUI:Advanced] part
		//of the project.
		return null;
	}

	//******************************************************
	//*******  DO NOT EDIT ANYTHING BELOW THIS LINE  *******
	//*******        But do read this section!       *******
	//******************************************************

	/**
	 *  Causes one random particle to maybe do something. Called
	 *  repeatedly.
	 */
	public void step() {
		int row = (int)(Math.random()*grid.size());
		int col = (int)(Math.random()*grid.get(row).size()) ;

		grid.get(row).get(col).fall(grid, row, col);
	}

	/**
	 *  Game loop of the program (step, redraw, handlers, etc.).
	 */
	public void run() {
		while (true) {
			//step
			for (int i = 0; i < display.getSpeed(); i++) step();

			//redraw everything
			updateDisplay();
			display.repaint();

			//wait for redrawing and for mouse
			display.pause(1);

			//handle person actions (resize and tool usage)
			if(display.handle(this) && display.pauseMode()) {
				//for debugging
				updateDisplay();
				display.repaint();
				display.pause(5000);
			}
		}
	}

	/**
	 *  Convenience method for GTA testing. Do not use this in
	 *  your code... for testing purposes only.
	 *  
	 *  @return the private grid element
	 */
	public DynamicArray<DynamicArray<Element>> getGrid() {
		return grid;
	}

	/**
	 *  Main method that kicks off the simulator.
	 *  
	 *  @param args not used
	 */
	public static void main(String[] args) {
		(new Simulation(true)).run();
	}
}
