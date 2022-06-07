// TO DO: add your implementation and JavaDocs.

import java.util.Collection;
import java.util.Iterator;
/**
 * Generic dynamic array data structure capable of holding type T objects.
 * @author Corey Jones
 *
 * @param <T> object type of dynamic array
 */
public class DynamicArray<T> implements Iterable<T> {
	/**
	 * Initial capacity of dynamic array.
	 */
	private static final int INITCAP = 2; //default initial capacity
	
	/**
	 * Generic array named storage to hold elements.
	 */
	private T[] storage; //underlying array, you MUST use this for credit (do not change the name or type)
	
	/**
	 * Integer count of current elements in array.
	 */
	private int currentElements; //keeps track of current number of elements in storage
	
	/**
	 * Initializes this object with final int INITCAP.
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(){
		// constructor
		this.storage = (T[])new Object[INITCAP];
	}
	
	/**
	 * Initializes this object with parameter initCapacity.
	 * @param initCapacity capacity of this dynamic array
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(int initCapacity) {
		//constructor
		
		//Checks to make sure capacity is valid
		if(initCapacity < 1) {

			throw new IllegalArgumentException("Capacity cannot be zero or negative.");

		}
		else
			this.storage = (T[])new Object[initCapacity];

	}
	
	/**
	 * Returns number of elements in dynamic array.
	 * @return number of elements in this array
	 */
	public int size() {	
		// Report the current number of elements.
		// O(1)


		return this.currentElements;
	}  
	
	/**
	 * Returns the max number of elements allowed in this dynamic array.
	 * @return max number of elements allowed in this dynamic array
	 */
	public int capacity() {
		// Report the max number of elements before expansion.
		// O(1)

		return this.storage.length;
	}
	/**
	 * Changes the item at the given index to be the given value.
	 * Returns the old item at that index.
	 * ELEMENTS CANNOT BE ADDED WITH THIS METHOD.
	 * @param index index of array element you want to set value of
	 * @param value value you would like to set array element at parameter index to
	 * @return returns old value at index
	 */
	public T set(int index, T value) {

		T temp;
		
		//Checks for bounds exceptions in index
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}

		else
			temp = this.storage[index];
		this.storage[index] = value;

		return temp;
	}
	
	/**
	 * Returns the item at the given index.
	 * @param index number of element you wish to get
	 * @return returns element at index number
	 */
	public T get(int index) {
		
		//Checks for bounds exception
		if(index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");

		}

		return this.storage[index];
	}
	
	/**
	 * Adds an element to the end of the list.
	 * If added, returns true.
	 * Will double capacity if no space is available.
	 * @param value T element you wish to add to the end of the dynamic array
	 * @return returns true if successful or false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean add(T value) {

		// Check to see if value is set or not at end of array
		if(currentElements < this.capacity()) {
			this.set(currentElements, value);
			currentElements++;
			return true;
		}

		//if set, create new temp array and double size. Then copy over initial array.
		T[] temporary = (T[]) new Object[this.capacity() * 2];
		int size = this.capacity();
		System.arraycopy(this.storage, 0, temporary, 0, this.size());
		this.storage = temporary;
		this.set(currentElements, value);
		currentElements++;
		return true;




	}
	/**
	 * Inserts the given value at the given index. 
	 * Will shift over elements if needed. 
	 * Doubles capacity if no space is available.
	 * @param index index you with to insert given value into
	 * @param value value of element you wish to insert at given index
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {

		//Checks bounds for index
		if(index > this.capacity() || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		
		//if array is empty, element can just be added.
		if(currentElements == 0) {
			this.storage[index] = value;
			currentElements++;
			return;
		}
		
		//if array is full, array is doubled and copied.
		if(this.currentElements == this.capacity()) {
			T[] temporary = (T[]) new Object[this.capacity() *2];
			System.arraycopy(this.storage, 0, temporary, 0, this.capacity());
			this.storage = temporary;
		}
		
		//Shift over elements from last element to insertion point
		//then copy them over right
		for (int i = currentElements; i > index; i--) {		
			this.storage[i] = this.storage[i-1];
		}
		//Set array element at index to value
		this.storage[index] = value;
		currentElements++;




	}

	/**
	 * Removes and returns the element at the given index.
	 * Will shift elements over to fill in the gap.
	 * Halves capacity of the storage if number of elements falls below 1/3 of capacity.
	 * @param index index at which element you want to be removed
	 * @return returns the removed element.
	 */
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		
		//Checks bounds of index
		if(index >= this.capacity() || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}

		T temp = this.storage[index];
		
		//Shift elements over to the left starting from index. 
		for( int i = index; i < currentElements; i++) {
			this.storage[i] = this.storage[i+1];
		}
		
		//Lowers current element count
		currentElements--;
		
		//Checks to see if current elements is at or below 1/3 of capacity
		//if true, the array is shrunk by half
		if(currentElements <= this.capacity()/3) {
			T[] temporary = (T[]) new Object[this.capacity()/2];
			System.arraycopy(this.storage, 0, temporary, 0, currentElements);
			this.storage = temporary;
		}

		return temp;
	}


	/**
	 * Iterator for DynamicArray Class.
	 * @return returns Iterator T
	 */
	public Iterator<T> iterator() {


		return new Iterator<T>() {
			int currentIndex;


			public T next() {

				return storage[currentIndex++];
			}

			public boolean hasNext() {

				if(storage[currentIndex] == null) {
					return false;
				}
				return currentIndex < currentElements;
			}
		};
	}

	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//******************************************************
	
	/**
	 * To string method used for debugging purposes.
	 * @return returns a string
	 */
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the list ifor easy viewing.
		StringBuilder s = new StringBuilder("Dynamic array with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();

	}
	
	/**
	 *  Main method used for debugging.
	 *  
	 *  @param args not used
	 */
	public static void main(String args[]){
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellend first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!

		DynamicArray<Integer> ida = new DynamicArray<>();
		if ((ida.size() == 0) && (ida.capacity() == 2)){
			System.out.println("Yay 1");
		}

		boolean ok = true;
		for (int i=0; i<3;i++)
			ok = ok && ida.add(i*5);

		if (ok && ida.size()==3 && ida.get(2) == 10 && ida.capacity() == 4 ){
			System.out.println("Yay 2");
		}

		ida.add(1,-10);
		ida.add(4,100);
		if (ida.set(1,-20)==-10 && ida.get(2) == 5 && ida.size() == 5 
				&& ida.capacity() == 8 ){
			System.out.println("Yay 3");
		}

		if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100 
				&& ida.size() == 2 && ida.capacity() == 4 ){
			System.out.println("Yay 4");
		}


		System.out.print("Printing values: ");
		for(Integer i : ida) {
			System.out.print(i);
			System.out.print(" ");
		}

	}
}