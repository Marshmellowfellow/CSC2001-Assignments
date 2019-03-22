import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This file contains an implementation of an AVL tree. An AVL tree
 * is a special type of binary tree which self balances itself to keep
 * operations logarithmic.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

public class PowerAVLApp <T extends Comparable<T>> implements Iterable<T> {
	
  public static void main(String[] args) {
	  	PowerAVLApp<Integer> tree = new PowerAVLApp<>();
		opCount addCount = new opCount(0);
		opCount count = new opCount(0);
		
		//Reading in the CSV file, creating a list of objects and sorting the list.
		String CSVName = "cleaned_data.csv";
		List<timeStamp> powerReadings = CSVread(CSVName);
		
    	int end = powerReadings.size();
    	if(args.length > 0) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if("-l" .contains(args[i])) {
	  				int j = args.length;
	  				if((i +1) < j) {
		  					end = Integer.valueOf(args[i + 1]);
	  				}
	  			}else if("-u" .contains(args[i])) {
	  			Collections.sort(powerReadings);
	  			}
	  		}
    	}
    	//Set max end value to the total number of elements contained to avoid error.
    	if(end > powerReadings.size()) {
    		end = powerReadings.size();
    	}
		
		for (int i = 0; i < end; i ++) {
	        String time = ((powerReadings.get(i)).getTime()).replaceAll("[/:.,]|12/2006/", "");
	        int key = Integer.valueOf(time);
			tree.insert(key, powerReadings.get(i), addCount);
		}
		
		
    	if(args.length > 0) {
    		//checking for the -c paramater to print the number total number of comparisons.
    		if("-c" .contains(args[0])) {
	  			if(args.length > 1) {
	    			System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
	  				String fileName = args[1];
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
						String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	  			}else {
	  	    		for (int i = 0; i < end; i ++) {
	  	    			System.out.println((powerReadings.get(i)).time  + "  " + (powerReadings.get(i)).global_active_power + "   		  " + (powerReadings.get(i)).getVoltage());
	  	    		}
	    			System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
	  			}
    			
    		}else if("-l" .contains(args[0])) {
	  			if(args.length > 2) {
		  			if("-c" .equals(args[2])) {
		  				System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
			  			if(args.length > 3) {
			  				String fileName = args[3];
			  				FileWriter fileWriter;
							try {
								fileWriter = new FileWriter(fileName, true);
								String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
				  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
				  				textWriter.write(fileWriter, fileName, text);
							} catch (IOException e) {
								e.printStackTrace();
							}
			  				
			  			}
			  		}
	  			}
    		}
    		else if("-k" .contains(args[0])) {
    			List<String> keys = KEYread(args[1]);
    			if(keys !=null) {
	    			opCount searchCount= new opCount(0);
	    			int sum = 0;
	    			for(int j = 0; j< keys.size();j++) {
	    				sum = sum + searchCount.opCount;
	    				searchCount.opCount = 0;
	    	    		String time = (keys.get(j)).replaceAll("[/:.,]|12/2006/", "");
	    	    		int key;
	    	    		if(time.length() > 8 ) {
	    	    			key = 0;
	    	    		}else {
	    	    			try {
	    	    			key = Integer.valueOf(time);
	    	    			}catch (Exception e) {
	    	    				key= 0;
	    	    			}
	    	    		}
	    	    		timeStamp search = tree.search(key, searchCount);
	    	    		if(search !=null) {
	    		    		System.out.println(search.getTime() + "  " + search.getGlobal_active_power() + "              " + search.getVoltage());
	    	    		}else {
	    	    			//System.out.println("Search for " + keys.get(j));
	    	    		}
	    			}for(int i = 0; i < (args.length) ; i++) {
		    			if("-c" .contains(args[i])) { 
			  				if(args.length > (i+1)) { 
				  				String fileName = args[i + 1];
				  				FileWriter fileWriter;
								try {
									fileWriter = new FileWriter(fileName, true);
									String text = (String.valueOf(sum/20 + ", " + addCount.opCount));
					  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
					  				
					  				textWriter.write(fileWriter, fileName, text);
								} catch (IOException e) {
									e.printStackTrace();
								}
				  			}else {
				  			}
			  			}
			  		}
    		}	
    			
    		}else if("-s" .contains(args[0])){
	    		String time = args[1].replaceAll("[/:.,]|12/2006/", "");
	    		int key;
	    		if(time.length() > 8 ) {
	    			key = 0;
	    		}else {
	    			try {
	    			key = Integer.valueOf(time);
	    			}catch (Exception e) {
	    				key= 0;
	    				System.out.println("Search for " + args[1]);
	    				System.out.println("Date/Time not found");
	    			}
	    		}
	    		timeStamp search = tree.search(key, count);
	    		if(search != null) {
	    			System.out.println("Date/Time            Global Avtive Power  Voltage");
		    		System.out.println((search).getTime() + "  " + (search).getGlobal_active_power() + "              " + (search).getVoltage());
		    		for(int i = 0; i < (args.length) ; i++) {
		    			if("-c" .contains(args[i])) { 
		    				System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
			  				if(args.length > (i+1)) { 
				  				String fileName = args[i + 1];
				  				FileWriter fileWriter;
								try {
									fileWriter = new FileWriter(fileName, true);
									String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
					  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
					  				
					  				textWriter.write(fileWriter, fileName, text);
								} catch (IOException e) {
									e.printStackTrace();
								}
				  			}else {
				  			}
			  			}
			  		}
	    		}else {
    				System.out.println("Search for " + args[1]);
    				System.out.println("Date/Time not found");
		    		for(int i = 0; i < (args.length) ; i++) {
		    			if("-c" .contains(args[i])) { 
			  				System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
			  				if(args.length > (i+1)) { 
				  				String fileName = args[i + 1];
				  				FileWriter fileWriter;
								try {
									fileWriter = new FileWriter(fileName, true);
									String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
					  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
					  				textWriter.write(fileWriter, fileName, text);
					  				
								} catch (IOException e) {
									e.printStackTrace();
								}
				  			}
			  				
			  			}else {
			  			}
			  		}
	    		}
    		
    		}
    	}
    	else {
    		for (int i = 0; i < end; i ++) {
    			System.out.println((powerReadings.get(i)).time  + "  " + (powerReadings.get(i)).global_active_power + "   		  " + (powerReadings.get(i)).getVoltage());
    		}
    	}
		

  }
  
  @Override
  public String toString() {
    return (TreePrinter.getTreeDisplay(root));
   }
  
  public class Node implements TreePrinter.PrintableNode { 
    // 'bf' is short for Balance Factor
    public int bf;
    public timeStamp timeData;
    // The value/data contained within the node.
    public T value;
    // The height of this node in the tree.
    public int height;
    // The left and the right children of this node.    
    public Node left, right;
    public Node(T value, timeStamp timeData) {
      this.value = value;
      this.timeData = timeData;
    }

    @Override 
    public TreePrinter.PrintableNode getLeft() {
      return left;
    }

    @Override
    public TreePrinter.PrintableNode getRight() {
      return right;
    }

    public String getText() {
      return value.toString();
    }

  }

  // The root node of the AVL tree.
  public Node root;

  // Tracks the number of nodes inside the tree.
  private int nodeCount = 0;

  // The height of a rooted tree is the number of edges between the tree's
  // root and its furthest leaf. This means that a tree containing a single 
  // node has a height of 0.
  public int height() {
    if (root == null) return 0;
    return root.height;
  }

  // Returns the number of nodes in the tree.
  public int size() {
    return nodeCount;
  }

  // Returns whether or not the tree is empty.
  public boolean isEmpty() {
    return size() == 0;
  }

  public void display() {
	  TreePrinter.getTreeDisplay(root);
  }
  // Return true/false depending on whether a value exists in the tree.
  public boolean contains(T value) {
    return contains(root, value);
  }

  // Recursive contains helper method.
  private boolean contains(Node node, T value) {
    
    if (node == null) return false;

    // Compare current value to the value in the node.
    int cmp = value.compareTo(node.value);

    // Dig into left subtree.
    if (cmp < 0) return contains(node.left, value);

    // Dig into right subtree.
    if (cmp > 0) return contains(node.right, value);

    // Found value in tree.
    return true;
  }
  public timeStamp search(T value, opCount count) {
	    return search(root, value, count);
	  }

  // Recursive contains helper method.
  private timeStamp search(Node node, T value, opCount count) {
	 
	count.opCount = count.opCount + 1;
    if (node == null) {
    	return null;
    }

    // Compare current value to the value in the node.
    int cmp = value.compareTo(node.value);
    // Dig into left subtree.
    count.opCount = count.opCount + 1;
    if (cmp < 0) {
    	return search(node.left, value, count);
    }else{
        count.opCount = count.opCount + 1;
        if (cmp == 0) {
        	return node.timeData;
        }else {
        	return search(node.right, value, count);	
        }
    }
    
  }
  
  // Insert/add a value to the AVL tree. The value must not be null, O(log(n))
  public boolean insert(T value,timeStamp timeData, opCount addCount) {
	addCount.opCount = addCount.opCount + 1;
    if (value == null) return false;
    addCount.opCount = addCount.opCount + 1;
    if (!contains(root, value)) {
      root = insert(root, value, timeData, addCount);
      nodeCount++;
      return true;
    }
    return false;
  }

  // Inserts a value inside the AVL tree.
  private Node insert(Node node, T value, timeStamp timeData, opCount addCount) {
    // Base case.
    if (node == null) {
    	return new Node(value, timeData);
    }
    // Compare current value to the value in the node.
    int cmp = value.compareTo(node.value);
    // Insert node in left subtree.
    addCount.opCount = addCount.opCount + 1;
    if (cmp < 0) {
    node.left = insert(node.left, value, timeData, addCount);
    // Insert node in right subtree.
    } else {
      node.right = insert(node.right, value, timeData, addCount);
    }
    // Update balance factor and height values.
    update(node);
    // Re-balance tree.
    return balance(node);

  }

  // Update a node's height and balance factor.
  private void update(Node node) {
    int leftNodeHeight  = (node.left  == null) ? -1 : node.left.height;
    int rightNodeHeight = (node.right == null) ? -1 : node.right.height;
    // Update this node's height.
    node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
    // Update balance factor.
    node.bf = rightNodeHeight - leftNodeHeight;
  }

  // Re-balance a node if its balance factor is +2 or -2.
  private Node balance(Node node) {
    // Left heavy subtree.
    if (node.bf ==-2) {
      // Left-Left case.
      if (node.left.bf < 0) {
        return leftLeftCase(node);
      // Left-Right case.
      } else {
        return leftRightCase(node);
      }

    // Right heavy subtree needs balancing.
    } else if (node.bf ==+2) {
      // Right-Right case.
      if (node.right.bf > 0) {
        return rightRightCase(node);
      // Right-Left case.
      } else {
        return rightLeftCase(node);
      }
    }
    // Node either has a balance factor of 0, +1 or -1 which is fine.
    return node;
  }
  private Node leftLeftCase(Node node) {
    return rightRotation(node);
  }
  private Node leftRightCase(Node node) {
    node.left = leftRotation(node.left);
    return leftLeftCase(node);
  }
  private Node rightRightCase(Node node) {
    return leftRotation(node);
  }
  private Node rightLeftCase(Node node) {
    node.right = rightRotation(node.right);
    return rightRightCase(node);
  }
  private Node leftRotation(Node node) {
    Node newParent = node.right;
    node.right = newParent.left;
    newParent.left = node;
    update(node);
    update(newParent);
    return newParent;
  }

  private Node rightRotation(Node node) {
    Node newParent = node.left;
    node.left = newParent.right;
    newParent.right = node;
    update(node);
    update(newParent);
    return newParent;
  }

  // Remove a value from this binary tree if it exists, O(log(n))
  public boolean remove(T elem) {

    if (elem == null) return false;

    if (contains(root, elem)) {
      root = remove(root, elem);
      nodeCount--;
      return true;
    }

    return false;
  }

  // Removes a value from the AVL tree.
  private Node remove(Node node, T elem) {
    
    if (node == null) return null;
    
    int cmp = elem.compareTo(node.value);

    // Dig into left subtree, the value we're looking
    // for is smaller than the current value.
    if (cmp < 0) {
      node.left = remove(node.left, elem);

    // Dig into right subtree, the value we're looking
    // for is greater than the current value.
    } else if (cmp > 0) {
      node.right = remove(node.right, elem);

    // Found the node we wish to remove.
    } else {

      // This is the case with only a right subtree or no subtree at all. 
      // In this situation just swap the node we wish to remove
      // with its right child.
      if (node.left == null) {
        return node.right;
        
      // This is the case with only a left subtree or 
      // no subtree at all. In this situation just
      // swap the node we wish to remove with its left child.
      } else if (node.right == null) {
        return node.left;

      // When removing a node from a binary tree with two links the
      // successor of the node being removed can either be the largest
      // value in the left subtree or the smallest value in the right 
      // subtree. As a heuristic, I will remove from the subtree with
      // the most nodes in hopes that this may help with balancing.
      } else {
        // Choose to remove from left subtree
        if (node.left.height > node.right.height) {
          // Swap the value of the successor into the node.
          T successorValue = findMax(node.left);
          node.value = successorValue;
          // Find the largest node in the left subtree.
          node.left = remove(node.left, successorValue);
        } else {
          // Swap the value of the successor into the node.
          T successorValue = findMin(node.right);
          node.value = successorValue;
          // Go into the right subtree and remove the leftmost node we
          // found and swapped data with. This prevents us from having
          // two nodes in our tree with the same value.
          node.right = remove(node.right, successorValue);
        }
      }
    }

    // Update balance factor and height values.
    update(node);

    // Re-balance tree.
    return balance(node);

  }

  // Helper method to find the leftmost node (which has the smallest value)
  private T findMin(Node node) {
    while(node.left != null) 
      node = node.left;
    return node.value;
  }

  // Helper method to find the rightmost node (which has the largest value)
  private T findMax(Node node) {
    while(node.right != null) 
      node = node.right;
    return node.value;
  }

  // Returns as iterator to traverse the tree in order.
  public java.util.Iterator<T> iterator() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T> () {
      Node trav = root;
      @Override 
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();        
        return root != null && !stack.isEmpty();
      }
      @Override 
      public T next () {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        while(trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }
        Node node = stack.pop();
        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }  
        return node.value;
      }
      @Override 
      public void remove() {
        throw new UnsupportedOperationException();
      }      
    };
  }



  // Make sure all left child nodes are smaller in value than their parent and
  // make sure all right child nodes are greater in value than their parent.
  // (Used only for testing)
  public boolean validateBSTInvarient(Node node) {
    if (node == null) return true;
    T val = node.value;
    boolean isValid = true;
    if (node.left  != null) isValid = isValid && node.left.value.compareTo(val)  < 0;
    if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;
    return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
  }
  
  public static List<timeStamp> CSVread(String FileName){
      String line = "";
	   List<timeStamp> powerValues = new ArrayList<>();
	   int lineNo = 0;
	   
	   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
	       while ((line = br.readLine()) != null) {
	    	   	if(lineNo > 0) {
		           String[] Element = line.split(",");
		           powerValues.add(new timeStamp(Element[3],Element[1],Element[0]));
	       		}
	    	   	lineNo ++;
	       }
	       } 
	       catch (IOException e) {
	           e.printStackTrace();
	       }
	       return powerValues;
  }
  public static List<String> KEYread(String FileName){
      String line = "";
	   List<String> keys = new ArrayList<>();
	   int lineNo = 0;
	   
	   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
	       while ((line = br.readLine()) != null) {
	    	   	if(lineNo > 0) {
		           String[] Element = line.split(",");
		           keys.add(Element[0]);
	       		}
	    	   	lineNo ++;
	       }
	       } 
	       catch (IOException e) {
	           System.out.println("No such file or directory");	
	           return null;
	       }
	       return keys;
  }
}















