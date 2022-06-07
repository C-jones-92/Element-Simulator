
import java.awt.Color;

/**
 *  Representation of a bit of metal.
 *  
 *  @author Corey Jones
 */
public class Water extends Element {
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int getWeight() {
		return 5;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {

		if(row < grid.size() - 1 && col <= grid.get(row).size()){
			Element temporary = grid.get(row).get(col);
			Element below = grid.get(row + 1).get(col);




			if(col == 0) {
				Element right = grid.get(row).get(col + 1);
				int random = (int) Math.floor(Math.random() * 3) + 1;
				switch(random) {
					
					case 1:
						if(temporary.getWeight() > below.getWeight()) {
							grid.get(row).set(col, below);
							grid.get(row + 1).set(col, temporary);
							break;
						}
						break;

					case 2:

						if(temporary.getWeight() > right.getWeight() && right != null) {
							grid.get(row).set(col, right);
							grid.get(row).set(col + 1, temporary);
							break;
						}

						break;
					case 3:
						break;
				}
			}

			else if(col == grid.get(row).size() - 1) {
				Element left = grid.get(row).get(col - 1);
				int random = (int) Math.floor(Math.random() * 3) + 1;
				switch(random) {
					case 1:
						if(temporary.getWeight() > below.getWeight()) {
							grid.get(row).set(col, below);
							grid.get(row + 1).set(col, temporary);
							break;
						}
						break;

					case 2:

						if(temporary.getWeight() > left.getWeight() && left != null) {
							grid.get(row).set(col, left);
							grid.get(row).set(col - 1, temporary);
							break;
						}

						break;
					case 3:
						break;
				}
			}


			else {
				Element right = grid.get(row).get(col + 1);
				Element left = grid.get(row).get(col-1);
				int random = (int) Math.floor(Math.random() * 3) + 1;
				switch(random) {
				
					case 1:
						if(temporary.getWeight() > below.getWeight()) {
							grid.get(row).set(col, below);
							grid.get(row + 1).set(col, temporary);
							break;
						}
						break;

					case 2:

						if(temporary.getWeight() > right.getWeight() && right != null) {
							grid.get(row).set(col, right);
							grid.get(row).set(col + 1, temporary);
							break;
						}

						break;
					case 3:

						if(temporary.getWeight() > left.getWeight() && left != null) {
							grid.get(row).set(col, left);
							grid.get(row).set(col - 1, temporary);
							break;
						}
						break;
				}
			}

		}

	}


	/**
	 *  {@inheritDoc}
	 */
	@Override
	public boolean push(DynamicArray<DynamicArray<Element>> grid, int row, int col) {

		//Checks for valid row and column input
		if(row <= 0 || col <= 0) return false;

		//Random number 1 or 2.
		int random = (int) (Math.random()*2 + 1);

		switch(random) {

			//Tries pushUp() first then pushLeft()
			case 1: 
				if(this.pushUp(grid, row, col)) return true;
				if(this.pushLeft(grid, row, col)) return true;

			//Tries pushLeft() first then pushUp()
			case 2:
				if(this.pushLeft(grid, row, col)) return true;
				if(this.pushUp(grid, row, col)) return true;
		}




		return false;

	}

	/**
	 *  Main method that shows an example of testing an element.
	 *  
	 *  @param args not used
	 */
	public static void main(String args[]){
		//This is an outline of how to test that an element
		//is doing what you want it to (without using the
		//simulator. You should definitely write similar tests
		//for Sand and Water.

		//All tests work like this:
		//    (1) setup a scenario
		//    (2) call some method(s) that will change (or not change) the scenario
		//    (3) check that what should happen did happen
		//Example:


		//(1) setup a scenario

		//create a grid and a piece of Metal
		DynamicArray<DynamicArray<Element>> grid = new DynamicArray<>();
		//todo: initialize the grid to a 3x3 area
		Empty empty = new Empty();
		for(int i = 0; i < 3; i++) {
			grid.add(i, new DynamicArray<Element>());
			for(int j = 0; j < 3; j++) {
				grid.get(i).add(j, empty);
			}
		}

		Sand s = new Sand();

		//put the element in the middle
		grid.get(1).set(1, s);


		//(2) call some method(s) that will change (or not change) the scenario
		s.fall(grid, 1, 1);


		//(3) check that what should happen did happen

		//in this case, the metal should not have moved
		//if it was sand, it would be at grid.get(2).get(1)
		//if it was water, it might be at grid.get(1).get(0), grid.get(1).get(2), or grid.get(2).get(1)

		if(grid.get(2).get(1) == s) {
			//^ carful about == here, that's checking for the same memory location...
			System.out.println("Yay");
		}

		//make more scenarios or continue this one!
	}
}