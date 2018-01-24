import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;


public class Homework1  extends JPanel
		implements TreeSelectionListener {

	static TraversalTree eT;
	JTree tree;
	JEditorPane htmlPane;
	String get;
	DefaultMutableTreeNode ThisNode;
	DefaultMutableTreeNode top;

	public Homework1(){
		super(new GridLayout(1,0));

		//Create the nodes.
		top = new DefaultMutableTreeNode(eT.rootch);
		createNodes(top,eT.root);

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		tree.putClientProperty("JTree.lineStyle","None");
		ImageIcon NodeIcon =  createImageIcon("middle.gif");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(NodeIcon);
		renderer.setClosedIcon(NodeIcon);
		tree.setCellRenderer(renderer);




		//Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		//Create the HTML viewing pane.
		htmlPane = new JEditorPane();

		JScrollPane htmlView = new JScrollPane(htmlPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));

		//Add the split pane to this panel.
		add(splitPane);
	}




	public static void main(String[] args) {

		String pf = "251-*32*+";
//       String pf = "25-";
		if(args.length>0)pf=args[0];
		eT = new TraversalTree(pf);
		eT.createExpressionTree();
		eT.inorder(eT.root);
		System.out.print("infix : ");
		eT.infix(eT.root);
		eT.calculate(eT.root);
		System.out.printf(" = " + eT.sum);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Homework1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		Homework1 newContentPane = new Homework1();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void createNodes(DefaultMutableTreeNode top , Node t) {

		if(t.left!=null)
		{
			DefaultMutableTreeNode L = new DefaultMutableTreeNode(t.left.ch);
			top.add(L);
			createNodes(L,t.left);
		}
		if(t.right!=null)
		{
			DefaultMutableTreeNode R = new DefaultMutableTreeNode(t.right.ch);
			top.add(R);
			createNodes(R,t.right);
		}
	}



	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Homework1.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	public void valueChanged(TreeSelectionEvent tse) {

		ThisNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		//            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		tree.getLastSelectedPathComponent();
		if(ThisNode == null){
			return;
		}
		String text = inorder(ThisNode);
		if(!ThisNode.isLeaf()) text += "=" + calculate(ThisNode);
		htmlPane.setText(text);

	}


	//ทำฟังก์ชั่นคิดใน Jtree ใหม่เหอะ ไม่สามารถลากจาก Traversal ได้จริง ร้องไห้แล้ว

	//https://docs.oracle.com/javase/8/docs/api/javax/swing/tree/DefaultMutableTreeNode.html


	public String inorder(DefaultMutableTreeNode node) { //เอาไว้เรียงใน Jtree
		if (node == null) return "null";
		if(node == ThisNode && !node.isLeaf()) { //เช็คว่า node ไม่ใช่ leaf
			return 	inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling()); //letf node right
		}else if(eT.isOperator(node.toString().charAt(0)) && node != top) {
			return "(" + inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling()) + ")";
		}else {
			return node.toString(); //.toString ทำเป็นตัวอักษร
		}
	}



	public int calculate(DefaultMutableTreeNode node) { //เอาไว้คิด Jtree
		if(node.isLeaf()) return Integer.parseInt(node.toString()); //แปลง เป็น int
		int sum = 0;
		switch(node.toString()) {
			case "+" : sum = calculate(node.getNextNode()) + calculate(node.getNextNode().getNextSibling()); break;
			case "-" :sum = calculate(node.getNextNode()) - calculate(node.getNextNode().getNextSibling()); break;
			case "*" :sum = calculate(node.getNextNode()) * calculate(node.getNextNode().getNextSibling()); break;
			case "/" :sum = calculate(node.getNextNode()) / calculate(node.getNextNode().getNextSibling()); break;
			default : sum = calculate(node.getNextNode()) + calculate(node.getNextNode().getNextSibling()); break;
		}
		return sum;
	}
}
