/**
 * TreeNode.java
 *
 * A class for trees.
 *
 * @author CGG, HK, and Given Tanri
 * Wheaton College, CSCI 235, Spring 2020
 * Lab 10
 * Date 20200414
 */
public class TreeNode {

    /**
     * The datum of this TreeNode.
     */
    private int datum;

    /**
     * The left branch of this TreeNode.
     */
    private TreeNode left;

    /**
     * The right branch of this TreeNode.
     */
    private TreeNode right;

    /**
     * Constructor of TreeNode
     * @param datum The datum of this TreeNode
     * @param left The left branch of this TreeNode
     * @param right The right branch of this TreeNode
     * PRECONDITION: datum is a valid integer AND
     *               the branches are EITHER valid TreeNode objects OR null
     */
    public TreeNode(int datum, TreeNode left, TreeNode right) {
        this.datum = datum;
        this.left = left;
        this.right = right;
    }

    /**
     * Counts the number of TreeNode objects in this TreeNode
     * @return c The number of TreeNodes objects in this TreeNode
     */
    public int count() { 
	int c = 1;
	if (left != null) {
	    c += left.count();
	}
	if (right != null) {
	    c += right.count();
	}
	return c;
	
    }

    /**
     * Display the TreeNode content in the preorder format:
     * root, left branch, right branch
     * @return display The TreeNode content
     */
    public String displayPreOrder() {
	String display = ""; // String to display
      
	display += datum; // Root
	if (left != null) { // Left
	    display += left.displayPreOrder(); // Recursive
	}
	if (right != null) { // Right
	    display += right.displayPreOrder(); // Recursive
	}
	return display;
    }

    /**
     * Display the TreeNode content in the inorder format:
     * left branch, root, right branch
     * @return display The TreeNode content
     */
    public String displayInOrder() { 
	String display = ""; // String to display
     
	if (left != null) { // Left
	    display += left.displayInOrder(); // Recursive
	}
	display += datum; // Root
	if (right != null) { // Right
	    display += right.displayInOrder(); // Recursive
	}
	return display;
    }

    /**
     * Display the TreeNode content in the postorder format:
     * left branch, right branch, root
     * @return display The TreeNode content
     */
    public String displayPostOrder() { 
	String display = ""; // String to display
      
	if (left != null) { // Left
	    display += left.displayPostOrder(); // Recursive
	}
	if (right != null) { // Right
	    display += right.displayPostOrder(); // Recursive
	}
	display += datum; // Root
	
	return display;
    }
}
