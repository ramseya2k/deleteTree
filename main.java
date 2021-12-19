import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(new File("input.txt"));
        BinaryTree root = new BinaryTree();
        while(input.hasNextLine())
        {
            String value = input.nextLine();
            if(value.contains("delete")) // if the input contains the word "delete", check if the number exists or if it doesnt 
            {
                String[] delete = value.split(" ");
                if(root.find(Integer.parseInt(delete[1]))) // finds the number 
                {
                    root.deleteKey(root.getNode(), Integer.parseInt(delete[1])); // deletes it from node 
                }
                else
                {
                    root.insertNode(Integer.parseInt(delete[1])); // otherwise inserts it 
                }
            }
            else
            {
                root.insertNode(Integer.parseInt(value)); // inserts number in tree 
            }
        }
        input.close(); // closes input 
        PrintStream out = new PrintStream(
        new FileOutputStream("output.txt", true), true); // writes to output file 
        System.setOut(out);
        System.out.println("PREORDER TRAVERSAL");
        System.out.println("==================");
        root.preOrder();
        System.out.println("\nINORDER TRAVERSAL");
        System.out.println("==================");
        root.inOrder();
        System.out.println("\nPOSTORDER TRAVERSAL");
        System.out.println("==================");
        root.postOrder();
        System.out.println("\nBREADTH TRAVERSAL");
        System.out.println("==================");
        root.breatdthLevelOrder();
        out.close();
    }
}

class BinaryTree
{
    Node root;
    int key;

    public void insertNode(int key) // recursion method 
    {
        root = insertNode(root, key);
    }

    public Node insertNode(Node leaf, int key)
    {
        if(leaf == null) // if it reaches the end, make a branch
        {
            leaf = new Node(key);
        }

        else if(key < leaf.getKey()) // if the key is less than the key, it will be on the left side
        {
            leaf.setLeft(insertNode(leaf.getLeft(), key));
        }

        else // if the key is greater than the key, it will be on the right side
        {
            leaf.setRight(insertNode(leaf.getRight(), key));
        }

        return leaf;
    }

    private int minValue(Node leaf) // gets minimum value of the tree
    {
        int value = leaf.getKey();
        while(leaf.getLeft() != null)
        {
            value = leaf.getLeft().getKey();
            leaf=leaf.getLeft();
        }
        return value;
    }

    public Node deleteKey(Node leaf, int key)
    {
        if(leaf == null) // base case
        {
            return null;
        }

        if(key < leaf.getKey()) // goes to the left side
        {
            leaf.setLeft(deleteKey(leaf.getLeft(), key));
        }

        else if(key > leaf.getKey()) // goes to the right side
        {
            leaf.setRight(deleteKey(leaf.getRight(), key));
        }

        else // if key has been found
        {
            if(leaf == null) // no child case
            {
                return null;
            }

            else if(leaf.getLeft() != null && leaf.getRight() != null) // two children case
            {
                int value = minValue(leaf.getRight());
                leaf.setRight(deleteKey(leaf.getRight(), key));
            }

            else // one child case
            {
                if(leaf.getLeft() != null)
                {
                    return leaf.getLeft();
                }

                else
                {
                    return leaf.getRight();
                }
            }
        }
        return leaf;
    }

    public boolean find(int key) // checks to see if the duplicate name exists in the tree
    {
        Node curr = root;
        boolean found = false;
        while (!found)
        {
            if (curr == null) // if node is exhausted, leave the loop and return false
            {
                break;
            }
            else if (curr.getKey() == key) // if node is exhausted, leave the loop and return true
            {
                found = true;
                break;
            }
            else if (key > curr.getKey()) // goes to the right side of the tree
            {
                curr = curr.getRight();
            }
            else // goes to the left side of the tree
            {
                curr = curr.getLeft();
            }
        }
        return found;
    }

    public void postOrder() // post order format = LRV
    {
        Node curr = root;
        postOrder(curr);
    }

    public void postOrder(Node curr)
    {
        if(curr!=null)
        {
            postOrder(curr.getLeft());
            postOrder(curr.getRight());
            System.out.print(curr.getKey() + " ");
        }
    }

    public void preOrder() // pre order format = VLR 
    {
        Node curr = root;
        preOrder(curr);
    }

    public void preOrder(Node curr)
    {
        if(curr != null)
        {
            System.out.print(curr.getKey() + " ");
            preOrder(curr.getLeft());
            preOrder(curr.getRight());
        }
    }

    public void inOrder() // in order format = LVR
    {
        Node curr = root;
        preOrder(curr);
    }

    public void inOrder(Node curr)
    {
        if(curr != null)
        {
            inOrder(curr.getLeft());
            System.out.print(curr.getKey()+" ");
            inOrder(curr.getRight());
        }
    }

    public Node getNode()
    {
        return this.root;
    }

    public void breatdthLevelOrder() // loops to display the tree 
    {
        int height = height(root);
        for(int i=0; i <= height ; i++)
        {
            breadthLevel(root, i);
        }
    }

    public int height(Node leaf) // gets height of the tree 
    {
        if(leaf == null)
            return 0;
        else
        {
            int left = height(leaf.getLeft());
            int right = height(leaf.getRight());
            if(right > left)
            {
                return right+1;
            }
            else
            {
               return left+1;
            }
        }
    }

    public void breadthLevel(Node leaf, int level) // displays each and every node in each level 
    {
        if(leaf == null)
        {
            return;
        }

        if(level == 1)
        {
            System.out.print(leaf.getKey() + " ");
        }
        else
        {
            breadthLevel(leaf.getLeft(), level-1);
            breadthLevel(leaf.getRight(), level-1);
        }
    }
}

class Node
{
    private int key;
    private Node left;
    private Node right;
    
    Node(int key)
    {
        this.key=key;
    } 

    public Node getLeft()
    {
        return this.left;
    }

    public Node getRight()
    {
        return this.right;
    }

    public void setLeft(Node left)
    {
        this.left=left;
    }

    public void setRight(Node right)
    {
        this.right=right;
    }

    public int getKey()
    {
        return this.key;
    }
}
