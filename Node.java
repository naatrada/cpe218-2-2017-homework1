
public class Node
{
    Node left,right;
    char ch;

    Node(Node l,char item,Node r) {
        ch = item;
        left = null;
        right = null;
    }
    public String toString() {
        return (right == null && left == null) ? Character.toString(ch) : "(" + left.toString()+ ch + right.toString() + ")";
    }

}
    