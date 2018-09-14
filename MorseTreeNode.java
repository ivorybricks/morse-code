/* MorseTreeNode.java constructs a node which has nodes to the left and right.
 */
 
public class MorseTreeNode {
    public String data;          // data stored in this node
    public MorseTreeNode left;   // left subtree
    public MorseTreeNode right;  //  right subtree

    // post: constructs a leaf node with given data
    public MorseTreeNode(String data) {
        this(data, null, null);
    }

    // post: constructs a node with the given data and links
    public MorseTreeNode(String data, MorseTreeNode left,
                          MorseTreeNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
