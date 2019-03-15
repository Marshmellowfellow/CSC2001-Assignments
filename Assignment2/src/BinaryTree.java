import java.util.List;

public class BinaryTree {

	Node root;

	public void addNode(Node newNode, opCount addCount) {

		String time = (newNode.name).getTime().replaceAll("[/:.,]|12/2006/", "");
		int key = Integer.valueOf(time);

		// If there is no root this becomes root
		addCount.opCount = addCount.opCount + 1;
		if (root == null) {

			root = newNode;

		} else {

			// Set root as the Node we will start
			// with as we traverse the tree

			Node focusNode = root;

			// Future parent for our new Node

			Node parent;

			while (true) {

				// root is the top parent so we start
				// there

				parent = focusNode;

				// Check if the new node should go on
				// the left side of the parent node
				addCount.opCount = addCount.opCount + 1;
				if (key < focusNode.key) {
					// Switch focus to the left child

					focusNode = focusNode.leftChild;

					// If the left child has no children
					addCount.opCount = addCount.opCount + 1;
					if (focusNode == null) {

						// then place the new node on the left of it

						parent.leftChild = newNode;
						return; // All Done

					}

				} else { // If we get here put the node on the right
					focusNode = focusNode.rightChild;

					// If the right child has no children
					addCount.opCount = addCount.opCount + 1;
					if (focusNode == null) {

						// then place the new node on the right of it

						parent.rightChild = newNode;
						return; // All Done

					}

				}

			}
		}

	}

	// All nodes are visited in ascending order
	// Recursion is used to go to one node and
	// then go to its child nodes and so forth

	public void inOrderTraverseTree(Node focusNode) {

		if (focusNode != null) {

			// Traverse the left node

			inOrderTraverseTree(focusNode.leftChild);

			// Visit the currently focused on node

			System.out.println(focusNode);

			// Traverse the right node

			inOrderTraverseTree(focusNode.rightChild);

		}

	}

	public void preorderTraverseTree(Node focusNode) {

		if (focusNode != null) {

			System.out.println(focusNode);

			preorderTraverseTree(focusNode.leftChild);
			preorderTraverseTree(focusNode.rightChild);

		}

	}

	public void postOrderTraverseTree(Node focusNode) {

		if (focusNode != null) {

			postOrderTraverseTree(focusNode.leftChild);
			postOrderTraverseTree(focusNode.rightChild);

			System.out.println(focusNode);

		}

	}

	public Node findNode(int key, opCount count) {

		// Start at the top of the tree

		Node focusNode = root;

		// While we haven't found the Node
		// keep looking

		while (focusNode.key != key) {

			// If we should search to the left
			
			count.opCount = count.opCount + 2;
			if (key < focusNode.key) {

				// Shift the focus Node to the left child

				focusNode = focusNode.leftChild;

			} else {

				// Shift the focus Node to the right child
				
				focusNode = focusNode.rightChild;

			}

			// The node wasn't found
			count.opCount = count.opCount + 1;
			if (focusNode == null)
				return null;

		}
		return focusNode;

	}
	
    Node sortedArrayToBST(List<timeStamp> powerReadings, int start, int end) {     	
        /* Base Case */
        if (start > end) { 
            return null; 
        } 
  
        /* Get the middle element and make it root */
        int mid = (start + end) / 2; 
        String time = ((powerReadings.get(mid)).getTime()).replaceAll("[/:.,]|12/2006/", "");
        int key = Integer.valueOf(time);
        
        Node node = new Node(key,powerReadings.get(mid));
        /* Recursively construct the left subtree and make it 
         left child of root */
        node.leftChild = sortedArrayToBST(powerReadings, start, mid - 1); 
  
        /* Recursively construct the right subtree and make it 
         right child of root */
        node.rightChild = sortedArrayToBST(powerReadings, mid + 1, end); 
          
        
        return node; 
    } 
}