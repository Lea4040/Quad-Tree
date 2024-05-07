

# Quadtree Project

**Quadtree**, a data structure similar to trees, is one of the most significant data structures for many applications. It can be used for image compression or data storage. The way quadtrees operate is that they recursively divide a 2D space into 4 quadrants. Each node in the tree either has zero (a leaf) or four children (non-leaf) only. For a better visualization, each child in the quadtree represents a part of the image in the following order: North-West, North-East, South-West, and South-East.

# Table of Contents

 - [Problem-solving Strategies](#problem-solving-strategies)
 	- [Inner Class QTNode](#inner-class-qtnode)
 	- [Task - A](#task---a)
	 	- [i - getNumNodes](#i---getnumnodes)
	 	- [ii - getNumLeaves](#ii---getnumleaves)
		- [iii - getImageArray](#iii---getimagearray)
 	- [Task - B](#task---b)
 	- [Task - C](#task---c)
 - [Test Case Screenshots](#test-case-screenshots)
 	- [Converting quadtree file to image.](#converting-quadtree-file-to-image)
 	- [Converting image to quadtree file.](#converting-image-to-quadtree-file)
 - [Challenges](#challenges)
 - [References](#references)




# Problem-solving Strategies

## Inner Class QTNode
This is a simple inner class to represent the quadtree node, the intensity, and its children (NW, NE, SW, SE). It has two constructors: one to define the intensity as -1 and its children as null, and the other to initialize the intensity by a given value in the argument.

## Task - A

For this task, it is required to complete the constructor of the **ImgQuadTree** class. I created a file with the file name given. Then, I used a try-catch block to ensure that no errors would be raised. In the try block, I created a scanner to read the data from the file and passed it to the method **buildQuadTree** to build a quadtree.

The **buildQuadTree** is a recursive method with a scanner argument and a return value of QTNode. The purpose of this method is to recursively create a quadtree. Since the representation of the number of the text is in preorder, I started by reading the input of the file. If it is -1, it means it represents a non-leaf value, and I should read the first 4 lines in the text as they represent its 4 children. Otherwise, it is a leaf and should return the node created. If the file is empty or it has reached the end of the file, it should return null.


### i - getNumNodes
To get the number of nodes in the quadtree, I created a helper method to recursively count the number of nodes in the quadtree, and its base case is if the node is null, i.e., a leaf node. I started by initializing the count to one and checking the intensity of the node passed in the argument. If the intensity is not -1, we return the count because it is a leaf. Otherwise, it calculates the count four times recursively, i.e., one for each child of the node.



### ii - getNumLeaves
Here, there are multiple ways to traverse a tree. I used the **Breadth-first** algorithm using a queue to ensure all the children of each node were counted. I create a variable pointer to track the location of the node in the tree and an integer **countLeaves** to be returned. I created a queue and started enqueuing the children of each node. A while loop is created to dequeue and enqueue all the elements in the quadtree for traversal. Each time a node is dequeued, its intensity will be checked. If it does not equal -1, then increase countLeaves. Otherwise, I enqueue all four children. I keep going through the while loop until the queue is empty. Then, I return the countLeaves.


### iii - getImageArray
In this task, a 2-D array is created to be filled by properly expanding the quadtree and returning the array. I used a helper recursive method to fill in the image array by tracking the coordinates of the 2-D array and passing them to the argument along with the 2-D array. In the helper method, I passed the **currentNode, the imageArray, row, col, length** in the argument. Row and col represent the current coordinates of the imageArray (the top left corner of the image part). Length represents the length of the 2D array passed (the length of the image). The base case is if we reach a leaf or if the length is equal to zero. In this method, I checked if the currentNode's intensity was not equal to -1.

If yes, then I iterate over the 2-D array from:
* row --> row + length
* col --> col + length

If not, then the method makes four recursive calls to all the children of the **currentNode**. The length will be decreased to half, but the row and column coordinates will change to the following:
* NW: (row, col)
* NE: (row, col + length/2)
* SW: (row + length/2, col)
* SE: (row + length/2, col + length/2)



## Task - B
By providing the text file with the compressed data. This method compiles the class **ImgQuadTreeDisplayer**, builds the tree, and shows the image successfully.

## Task - C
In this task, I created a class **ImgQuadTreeFileCreator**. The purpose is to read an uncompressed text file that represents the intensity of an image and create a new file that contains the intensity of the image as a quadtree file. There are three methods in this class:

**1) QTFile:**
This is a recursive method with the following parameters: **writer (PrintWriter), row, col, length, and 2-D imageAsArray** (traversing the 2-D by the row and col passed in the argument). The base case is if the length is equal to zero. In this method, I created an integer, **tempIntensity**, which has the first intensity value of the 2-D array image of the top left coordinate (row, col), and a Boolean variable, **isSameColor**, to indicate that the image from (row, col) to (row + length, col + length) has the same intensity number. This number is determined by traversing the image in 2-D and checking if the location of a (row, col) in the array does not equal the tempIntensity.  
Then, I check if isSameColor is true, meaning it is a leaf, and I write the intensity to the text file. Otherwise, I go recursively four times, changing the values (row, col) that represent the coordinates of the 2-D array.

**2) getImageArray:**
This method will take the Scanner as an input, create an empty 2-D array of [256]*[256], write all the elements of the text file to the array, and then return it.

**3) main:**
In this method, I asked the user to provide the name of the uncompressed text file. Then, the program will try to open the file and create an output file. After that, a 2-D array will be created by calling the getImageArray method and writing the result to the file by calling the method QTFile.



# Test Case Screenshots

## Converting quadtree file to image
The output of the class ImgQuadTreeDisplayer (walkingtotheskyQT):
![The output of the class ImgQuadTreeDisplayer](https://i.ibb.co/ZMLCVcS/walking-to-the-sky-image-output.png)

The image:
![enter image description here](https://i.ibb.co/dtR8H9w/walking-to-the-sky-image.png)




## Converting image to quadtree file

The project file content before running the class ImgQuadTreeFileCreator:
![enter image description here](https://i.ibb.co/dmNV459/without-smiley-face-quadtree.png)

The output after running the class:
![enter image description here](https://i.ibb.co/N26VxZq/create-quadtree-file-output.png)

The project file content after running the class ImgQuadTreeFileCreator:
![enter image description here](https://i.ibb.co/ZSGPQ5h/with-smiley-face-quadtree.png)

The output of the class ImgQuadTreeDisplayer (smileyface_QuadTreeFile):
![enter image description here](https://i.ibb.co/6bLSkHD/smiley-face-output.png)

The image:
![enter image description here](https://i.ibb.co/qjsx4vM/smily-face-image.png)



# Challenges

* **imgQuadTree class:**
The implementation of the methods of counting the nodes and leaves was easier by applying the algorithm. However, getImageArray was the most challenging method in this class. Going through the 2-D array recursively and in order (NW, NE, SW, SE) was a bit difficult to track because you do not yet have a method for testing and recognizing the errors. Moreover, tracking the coordinates was a little bit challenging, and the image kept printing incorrectly (vertically mirroring the image).
 
* **ImgQuadTreeDisplayer class:**
The only challenge I faced in this class was calling the file and passing it to the console. An error (file not found) was raised each time the programs ran, even when the file was within the same directory as the Java classes. The only way that the file can run successfully is by passing the absolute path of the required text file.

* **ImgQuadTreeFileCreator class:**
The same problem in the imgQuadTree class was raised, but knowing the solution to it saves a lot of time. Additionally, the space complexity of QTFile was O(n^2) because the 2-D array, which represents the part of the 2-D array of the image, was created inside the method. I tried using a class variable, but it was not efficient in terms of the access modifiers. To resolve this issue, I created the 2-D array and passed the reference to the QTFile parameter.


# References

- KFUPM ICS202 slides
- Data Structure and algorithm textbook
- https://stackoverflow.com/
- https://stackedit.io/
- https://postimages.org/


