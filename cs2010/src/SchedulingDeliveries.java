import java.util.*;
import java.io.*;

import org.w3c.dom.views.AbstractView;

// matric number: A0116631N
// name: Eugene
// List of Collaborator: Introduction to Algorithms by CLRS

interface PriorityElement {
	public int getPriority();
	public void increasePriority(int increment);
}

class Woman implements PriorityElement{
	private String name;
	private int dilation;
	public Woman(String name){
		this.setName(name);
		this.dilation = 0;
	}
	public String getName() {
	  return name;
  }
	public void setName(String name) {
	  this.name = name;
  }

	public void increasePriority(int dilatation) {
	  this.dilation += dilatation;
  }
	
	@Override
  public int getPriority() {
	  return this.dilation;
  }
	
	/***
	 * increase dilation will increase the priority.
	 * @param dilation
	 */
	public void updateDilation(int dilation) {
	  this.increasePriority(dilation);
  }
	
	@Override
	public boolean equals(Object obj){
		Woman woman = (Woman) obj;
		if (woman != null){
			return this.getName().equals(woman.getName());
		} else {
			return false;
		}
	}
}

class EmptyHeapException extends RuntimeException {
	public EmptyHeapException(){
		super();
	}
	
	public EmptyHeapException(String message){
		super(message);
	}
}

class BinaryHeap<E extends PriorityElement> {
	private Vector<E> elements;
	
	public BinaryHeap() {
		elements = new Vector<E>();
		elements.add(null);
	}
	
	/***
	 * insert the object into the binary heap.
	 * 
	 * @param element
	 */
	public void insert(E element){
		elements.add(element);
		this.shiftUp(this.size());
	}
	
	/***
	 * Return the largest object without removing the object.
	 * @return largest object
	 */
	public E maximum(){
		if (this.isEmpty()){
			throw new EmptyHeapException();
		} else {
			return this.elements.get(1);
		}
	}
	
	/***
	 * Remove and return the largest object 
	 * @return largest object
	 */
	public E extractMax(){
		return this.extract(1);
	}
	
	/***
	 * Remove and return the object from the index given.
	 * 
	 * @param index
	 * @return object
	 */
	public E extract(int index){
		if (this.isEmpty()){
			throw new EmptyHeapException();
		} else {
			E obj = this.elements.get(index);
			this.elements.set(index, this.elements.get(this.size()));
			this.elements.remove(this.size());

			// boundary case: after removing the last element, index may be greater than heap size
			if (index <= this.size()){ 
				this.shiftDown(index);
				this.shiftUp(index);
			}
			return obj;
		}
	}
	
	/**
	 * increase the priority of the given index
	 * 
	 * @param index
	 * @param priority_inc
	 */
	public void increasePriority(int index, int priority_inc){
		E element = this.elements.get(index);
		element.increasePriority(priority_inc);
		this.elements.set(index, element);
		this.shiftUp(index);
	}

	/***
	 * Return the Heap Size
	 * 
	 * @return
	 */
	private int size() {
	  // TODO Auto-generated method stub
	  return this.elements.size() - 1;
  }

	/***
	 * Retrieve the parent index
	 * @param index
	 * @return parent index
	 */
	private int parent(int index){
		// bitwise operation for round down of index / 2
		return index >> 1;
	}
	
	/***
	 * Retrieve the left subtree index
	 * @param index
	 * @return left index
	 */
	private int left(int index){
		//bitwise operation for 2 * index
		return index << 1; 
	}
	
	/***
	 * Retrieve the right subtree index 
	 * @param index
	 * @return right index
	 */
	private int right(int index){
		// bitwise operation for (2 * index) + 1
		return index << 1; 
	}
	
	/***
	 * Maintain the max-heap property.
	 * This method also known as MaxHeapify or floatdown.
	 * It will swap the current index down until 
	 * no more element below it or the priority below it is smaller
	 * 
	 * @param index
	 */
	private void shiftDown(int index){
		E element = this.elements.get(index);
		
		// Largest Declaration
		int largest_index = index;
		E largest = element;
		
		// Search to Left
		int left_index = this.left(index);
		if (left_index <= this.size()){
			E left = this.elements.get(left_index);
			if (left.getPriority() > element.getPriority()){
				largest_index = left_index;
				largest = this.elements.get(largest_index);
			}	
		}
		
		// Search to Right
		int right_index = this.right(index);
		if (right_index <= this.size()){
			E right = this.elements.get(right_index);
			if (right.getPriority() > largest.getPriority()){
				largest_index = right_index;
				largest = this.elements.get(largest_index);
			}			
		}
		
		// Swapping & Shifting
		if (largest_index != index){
			this.swap(largest_index, index);
			this.shiftDown(largest_index);
		}
	}
	

	/***
	 * Maintain the max-heap property
	 * It will swap the current index up until root 
	 * or until the priority of the element above is greater
	 * @param index
	 */
	private void shiftUp(int index) {
		E parent = this.elements.get(this.parent(index));
		E current = this.elements.get(index);
	  while (index > 1 && parent.getPriority() < current.getPriority()){
	  	this.swap(index, this.parent(index));
	  	index = this.parent(index);
	  }
  }

	/***
	 * Exchange two object indexes.
	 * 
	 * @param index1
	 * @param index2
	 */
	private void swap(int i1, int i2) {
	  E tmp = this.elements.get(i1);
	  this.elements.set(i1, this.elements.get(i2));
	  this.elements.set(i2, tmp);
  }

	/***
	 * Get the index of the object from the binary heap indexes
	 * 
	 * @param element
	 * @return index
	 */
	public int indexOf(E element) {
	  return this.elements.indexOf(element);
  }

	/***
	 * Check if the Binary heap has no object
	 * 
	 * @return
	 */
	public boolean isEmpty() {
	  return this.size() <= 0;
  }

}

class SchedulingDeliveries {
	BinaryHeap<Woman> delivery_pool; 
  public SchedulingDeliveries() {
  	delivery_pool = new BinaryHeap<Woman>();
  }

  void ArriveAtHospital(String womanName, int dilation) {
  	Woman woman = new Woman(womanName);
  	woman.updateDilation(dilation);
  	this.delivery_pool.insert(woman);
   }

  void UpdateDilation(String womanName, int increaseDilation) {
    // update the dilation of womanName to
    // dilation += increaseDilation
    // and modify the data structure necessarily
  	
  	int index = this.delivery_pool.indexOf(new Woman(womanName));	
  	if (index >= 1){
  		this.delivery_pool.increasePriority(index, increaseDilation);
  	}
  }

  void GiveBirth(String womanName) {
    // This womanName gives birth 'instantly'
    // remove her from your chosen data structure
  	int index = this.delivery_pool.indexOf(new Woman(womanName));
  	if (index >= 1){
  		this.delivery_pool.extract(index);  		
  	}
  }

  String Query() {
  	// report the name of the woman that the doctor
  	// has to give the most attention to. If there is no more woman to
  	// be taken care of, return a String "The delivery suite is empty"
  	String ans;
  	try {
  		ans = this.delivery_pool.maximum().getName();
  	} catch (EmptyHeapException e){
  		ans = "The delivery suite is empty";
  	}
    return ans;
  }

  void run() throws Exception {
    // do not alter this method
    // various AC solutions from several TAs and myself are in the region of [0.2-0.3]s in Mooshak and thus the 1s time limit is generous enough

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: GiveBirth(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method

    SchedulingDeliveries ps2 = new SchedulingDeliveries();
    ps2.run();
  }
}
