public class MorseTree {

    private MorseTreeNode overallRoot; // root of the overall tree

    /*
    Constructor and the methods to create the MorseTree:
    create(), finishLeftSubTree(), finishRightSubTree()
     */

    // Constructor - Constructs a Morse Tree
    public MorseTree() {
        this.overallRoot = new MorseTreeNode("start");
        create(overallRoot);

    }


    /*
    Note: The code for creating the MorseTree, similar 
    to a binary tree, has been separated into two methods:
    finishLeftSubTree() and finishRightSubTree().
    */
    
    private void create(MorseTreeNode root) {
        add(this.overallRoot, "E"); //left child of the root
        add(this.overallRoot, "T"); //right child of the root

        finishLeftSubTree(this.overallRoot);
        finishRightSubTree(this.overallRoot);


    }
    private void finishLeftSubTree(MorseTreeNode overallRoot){
        add(overallRoot.left, "I");
        add(overallRoot.left, "A");
        add(overallRoot.left.left, "S");
        add(overallRoot.left.left, "U");
        add(overallRoot.left.right, "R");
        add(overallRoot.left.right, "W");
        add(overallRoot.left.right.left, "L");
        add(overallRoot.left.right.left, "null");
        add(overallRoot.left.right.left.left, "null");
        add(overallRoot.left.right.left.left, "null");
        add(overallRoot.left.right.left.right, "+");
        add(overallRoot.left.right.left.right, "null");
        add(overallRoot.left.right.right, "P");
        add(overallRoot.left.right.right, "J");
        add(overallRoot.left.right.right.left, "null");
        add(overallRoot.left.right.right.left, "null");
        add(overallRoot.left.right.right.right, "null");
        add(overallRoot.left.right.right.right, "1");
        add(overallRoot.left.left.left, "H");
        add(overallRoot.left.left.left, "V");
        add(overallRoot.left.left.right, "F");
        add(overallRoot.left.left.right, "null");
        add(overallRoot.left.left.right.right, "null");
        add(overallRoot.left.left.right.right, "2");
        add(overallRoot.left.left.left.left, "5");
        add(overallRoot.left.left.left.left, "4");
        add(overallRoot.left.left.left.right, "null");
        add(overallRoot.left.left.left.right, "3");
        add(overallRoot.left.left.left, "null");
        add(overallRoot.left.left.left, "null");
    }

    private void finishRightSubTree(MorseTreeNode overallRroot){
        add(overallRoot.right, "N");
        add(overallRoot.right, "M");
        MorseTreeNode rightSub = overallRoot.right;
        add(rightSub.left, "D");
        add(rightSub.left, "K");
        add(rightSub.left.left, "B");
        add(rightSub.left.left, "X");
        add(rightSub.left.left.left, "6");
        add(rightSub.left.left.left, "=");
        add(rightSub.left.left.right, "/");
        add(rightSub.left.left.right, "null");
        add(rightSub.left.right, "C");
        add(rightSub.left.right, "Y");
        add(rightSub.left.right.left, "null");
        add(rightSub.left.right.left, "null");
        add(rightSub.left.right.right, "Y");
        add(rightSub.left.right.right, "Y");
        add(rightSub.right, "G");
        add(rightSub.right.left, "Z");
        add(rightSub.right.left, "Q");
        add(rightSub.right.left.left, "7");
        add(rightSub.right.left.left, "null");
        add(rightSub.right.left.right, "null");
        add(rightSub.right.left.right, "null");
        add(rightSub.right, "O");
        add(rightSub.right.right, "null");
        add(rightSub.right.right, "null");
        add(rightSub.right.right.right, "8");
        add(rightSub.right.right.right, "null");
        add(rightSub.right.right.left, "9");
        add(rightSub.right.right.left, "0");
    }


    /* Accessor methods:
    printSideways(), translateToEnglish(), translateToMorse(),
    construct(), search()
     */

    public void printSideways() {
        printSideways(overallRoot, 0);
    }

    private void printSideways(MorseTreeNode root, int level) {
        if (root != null) {
            printSideways(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("        ");
            }
            System.out.println(root.data);
            printSideways(root.left, level + 1);
        }
    }

    /* NOTE: For the purposes of this program,
       I have decided to use the convention of
       implementing the slash mark ("/") for spaces
       between words in Morse Code.
     */

    //This method reads Morse Code and translates it to English.
    public String translateToEnglish(String s) {
        MorseTreeNode current = overallRoot;

        //String s is a chunk of Morse Code which represents ONE English letter
        //Loop through the dots and dashes
        for (int i = 0; i < s.length(); i++) {
            Character test = s.charAt(i);

            if (test == '.') { //go left for a dot
                current = current.left;
            } else if (test == '-') { //go right for a dash
                current = current.right;
            } else if (test == '/') { //a slash returns a space
                return " ";
            } else { //something isn't right...so throw an exception for the fun of it!
                throw new IllegalArgumentException("File must contain morse code, and must not contain anything else.");
            }
        }
        return current.data; //You have arrived at the correct Node. Now, return the letter value.
    }

    //This method reads a word and translates into Morse Code.
    public String translateToMorse(String s) {
        String result = "";
        boolean moreThanOneLetter = s.length() > 1;

        /*
        String s is always going to be a word.
        This for-loop loops through the word.
         */
        for (int i = 0; i < s.length(); i++) {
            Character test = s.charAt(i);

            /* The method construct() finds the Morse Code that represents one letter.
            This is then added to the result.
             */

            result += construct(overallRoot, test.toString(), 0);

            /* If more letters are coming up,
            add a space to distinguish the letters.
             */
            if (moreThanOneLetter) {
                result += " ";
            }
        }
        return result;
    }

    //This method find the Morse Code for an English letter "s"
    private String construct(MorseTreeNode root, String s, int level) {

        //See if the current node's data has the letter we are looking for.
        if (s.toUpperCase().equals(root.data)) {
            return "";
        } else if (level == 5) { //Level 5 means we have reached the end of the tree. Return "!"
            return "!";
        } else {

            /*
            The search() method looks to see if our letter is on the
            left of our node.
             */

            if (search(root.left, s)) {
                //If it is on the left, add a "." for a dot and find the rest of the Code.
                return "." + construct(root.left, s, level + 1);

            /*
            The search() method looks to see if our letter is on the
            right of our node.
             */

            } else if (search(root.right, s)) {
                //If it is on the right, add a "-" for a dash and find the rest of the Code.
                return "-" + construct(root.right, s, level + 1);
            }
            return "!"; //Error! / Weird result
        }
    }

    //See if our desired English letter is at the current root.data
    private boolean search(MorseTreeNode root, String s) {
        if (root == null) { //The root is not applicable
            return false;
        } else if (root.data.equals(s.toUpperCase())) { //We found our letter!
            return true;
        } else { //Else, continue to search both sides
            return search(root.left, s) || search(root.right, s);
        }
    }


    /*
    Mutator methods:
    add()
     */
    /* The add() method for the MorseTree.*/
    private MorseTreeNode add(MorseTreeNode root, String value) {
        if (root == null) {
            root = new MorseTreeNode(value);
        } else if (root.left == null) {
            root.left = add(root.left, value);
        } else if (root.right == null) {
            root.right = add(root.right, value);
        }
        return root;
    }


}
