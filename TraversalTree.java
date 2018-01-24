
import java.util.Stack;

public class TraversalTree {

    private String postfix;
    public Node root;
    public char rootch;
    public Node leftNode, rightNode;
    public int sum = 0;


    public TraversalTree(String postfix)
    {
        if (postfix == null) { throw new NullPointerException("The posfix should not be null"); }
        if (postfix.length() == 0)  { throw new IllegalArgumentException("The postfix should not be empty"); }
        this.postfix = postfix;
    }

    public boolean isOperator(char c){
        if (    c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/'){
            return true;
        }
        return false;
    }

    public Node createExpressionTree()
    {
        final Stack<Node> nodes = new Stack<Node>();

        for (int i = 0; i < postfix.length(); i++)
        {
            char ch  = postfix.charAt(i);
//        System.out.println(nodes);
            if (isOperator(ch))
            {
                root = new Node(null,ch,null);
                rightNode = nodes.pop();
                leftNode = nodes.pop();
                root.right = rightNode;
                root.left = leftNode;

                nodes.push(root);
            }
            else if (!Character.isWhitespace(ch))
            {
                nodes.add(new Node(null, ch, null));
            }
        }
        root = nodes.peek();
        rootch = root.ch;
        nodes.pop();

        return root;
    }

    public String inorder(Node n){
        if (n != null){
            inorder(n.left);
//System.out.print(n.ch);
            inorder(n.right);
        }
        return null;
    }

    public void infix(Node n){
        System.out.print(n);
    }

    public int calculate(Node n){

        if(isOperator(n.ch)){
            switch(n.ch){
                case '+': sum = calculate(n.left) + calculate(n.right) ; break;
                case '*': sum = calculate(n.left)  * calculate(n.right) ; break;
                case '-': sum = calculate(n.left) - calculate(n.right) ; break;
                case '/': sum = calculate(n.left)  / calculate(n.right) ; break;
                default  : sum = calculate(n.left)  + calculate(n.right) ; break;
            }
        }
        else{
            return toDigit(n.ch);
        }
        return sum;
    }

    private int toDigit(char ch)
    {
        return ch - '0';
    }

}