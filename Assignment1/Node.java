class Node {

	int key;
	timeStamp name;
	

	Node leftChild;
	Node rightChild;

	Node(int key, timeStamp name) {

		this.key = key;
		this.name = name;

	}
	public String toString() {

		return name + " has the key " + key;

		/*
		 * return name + " has the key " + key + "\nLeft Child: " + leftChild +
		 * "\nRight Child: " + rightChild + "\n";
		 */

	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public timeStamp getName() {
		return name;
	}
	public void setName(timeStamp name) {
		this.name = name;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

}