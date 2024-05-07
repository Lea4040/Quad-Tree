package Project;
import Lab06.Queue;

import java.io.*;
import java.util.*;
public class ImgQuadTree {
	protected QTNode root = null;

	// (a) constructor:
	public ImgQuadTree(String filename){
		// read the file name and save it in a File variable
		File file = new File(filename);

		try(Scanner input = new Scanner(file)){
			// use the method to build the tree and save it in the root (see details below)
			root = buildQuadTree(input);
		}
		catch(FileNotFoundException ex){
			System.out.println(ex.getMessage());
		}
		
	}

	// this method is used in the constructor after reading the file name
	// I passed the Scanner as an argument to track the content of the file given in the constructor
	// this is a recursive method
	public QTNode buildQuadTree(Scanner input){
		if (input.hasNext()){
			// read intensity from the text and create the node using this value
			int intensity_value = Integer.parseInt(input.nextLine());
			QTNode node = new QTNode(intensity_value);

			// check if it is a leaf
			if (intensity_value == -1){
				// in the order of (NW, NE, SW, SE)
				node.NW = buildQuadTree(input);
				node.NE = buildQuadTree(input);
				node.SW = buildQuadTree(input);
				node.SE = buildQuadTree(input);

			}
			return node;
		}

		// if the file is empty
		return null;
	}





	// (a)-(i) --> getNumNodes():
	public int getNumNodes() {
		return getNumNodes(root);
	}

	// Helper method, to go recursively
	// Note: we can calculate the number of nodes by counting the lines in the text file because it is already represented as QuadTree
	// However, I will calculate the count from the QuadTree, e.i., root
	public int getNumNodes(QTNode node){
		if (node == null){
			return 0;
		}

		int count = 1;
		if (node.intensity == -1){ 	// non-leaf
			count += getNumNodes(node.NW);
			count += getNumNodes(node.NE);
			count += getNumNodes(node.SW);
			count += getNumNodes(node.SE);
		}
		// a leaf
		return count;
	}





	// (a)-(ii) --> getNumLeaves():
	// here, I used the Breadth-first algorithm
	public int getNumLeaves() {
		int countLeaves = 0;
		// to track the position when traversing
		QTNode pointer = root;
		// a queue to perform Breadth-first algorithm
		Queue<QTNode> queue = new Queue<>();

		if (pointer != null){
			queue.enqueue(pointer);
			while(!queue.isEmpty()){
				pointer = queue.dequeue();
				// a leaf
				if (pointer.intensity != -1){
					countLeaves++;
				}
				// non-leaf... because there is only 0 child (leaf) or 4 children
				else{
					queue.enqueue(pointer.NW);
					queue.enqueue(pointer.NE);
					queue.enqueue(pointer.SW);
					queue.enqueue(pointer.SE);
				}
			}
		}
		return countLeaves;

	}





	// (a)-(iii) --> getImageArray():
	public int[][] getImageArray() {
		int[][] image_2DArray = new int[256][256];
		// calling the helper method to fill the array with the intensities
		getImageArray(root, image_2DArray, 0, 0, 256);
		return image_2DArray;
	}

	// helper method... to fill the array
	// row and column here is representing the coordinate of the top left point in the array
	private void getImageArray(QTNode currentNode, int[][] imageArray, int row, int column, int length) {
		//base case
		if (currentNode == null || length == 0) {
			return;
		}

		// a leaf
		if (currentNode.intensity != -1) {
			// using a loop to fill all the required cells in the array
			// starting from row --> row + length
			// starting from column --> column + length
			for (int i = row; i < row + length; i++) {
				for (int j = column; j < column + length; j++) {
					imageArray[i][j] = currentNode.intensity;
				}
			}
		}
		// not a leaf --> go recursively
		else {
			// in the order of (NW, NE, SW, SE)
			getImageArray(currentNode.NW, imageArray, row, column, (length/2)); // NW --> same row and column
			getImageArray(currentNode.NE, imageArray, row, (column + (length/2)), (length/2)); //NE --> shift right (shift row)
			getImageArray(currentNode.SW, imageArray, (row + (length/2)), column, (length/2)); //SW --> shift down (shift column)
			getImageArray(currentNode.SE, imageArray, (row +(length/2)), column + (length/2), (length/2)); // SE --> shift right and down (shift row and column)
		}
	}





	// inner class
	private class QTNode {
		protected int intensity;
		protected QTNode NW, NE, SW, SE;

		public QTNode(){
			this.intensity = -1;
			this.NW = this.NE = this.SW = this.SE = null;
		}

		// another constructors
		public QTNode(int intensity){		// to represent a child node
			this.intensity = intensity;
			this.NW = this.NE = this.SW = this.SE = null;
		}



	}
}

