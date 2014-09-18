import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * It's the absolutely very liquid binary search tree (not sure of the actual extension)
 * Coded for homework
 * with some help from the psuedocode and piazza
 * @author Hemen Shah
 *
 * @param <E> a generic that extends comparable
 */
public class AVLTree<E extends Comparable> implements BinaryTree<E> {
	private Node root;
	private int size;
	
	@Override
	public boolean add(E item) {
		if (item == null) {
			return false;
		} else if (isEmpty()) {
			root = new Node(item);
			size++;
			return true;
		} else if (root.data.compareTo(item) == 0) {
			return false;
		} else {
			boolean b = add(item, root);
			rebalance(root);
			return b;
		}
	}
	private boolean add(E item, Node n) {
		if (item.compareTo(n.data) == 0) {
			return false;
		} else if (item.compareTo(n.data) < 0) {
			if (n.left == null) {
				n.left = new Node(item);
				size++;
				return true;
			} else {
				boolean b = add(item, n.left);
				n.left = rebalance(n.left);
				return b;
			}
		} else {
			if (n.right == null) {
				n.right = new Node(item);
				size++;
				return true;
			} else {
				boolean b = add(item, n.right);
				n.right = rebalance(n.right);
				return b;
			}
		}
	}
	
	
	private Node rebalance(Node n){
		if (n == null) {
			return n;
		}

		bfAndheight(n);
		if (n.bf < -1) {
			if (n.right.bf < 0) {
				n = rotateLeft(n);
			} else {
				n = rotateRL(n);
			}
		} else if (n.bf > 1) {
			if (n.left.bf > 0) {
				n = rotateRight(n);
			} else {
				n = rotateLR(n);
			}
		}
		return n;
	}
	private void bfAndheight(Node n) {
		if (n == null) {
			return;
		}
		int l, r;
		if (n.left == null) {
			l = -1;
		} else {
			l = n.left.height;
		}
		if (n.right == null) {
			r = -1;
		} else {
			r = n.right.height;
		}
		n.height = max(l, r) + 1;
		n.bf = l - r;
	}
	private Node rotateLeft(Node n) {
		Node newRoot = n.right;
		n.right = newRoot.left;
		newRoot.left = n;
		bfAndheight(newRoot.left);
		bfAndheight(newRoot.right);
		bfAndheight(newRoot);
		return newRoot;
	}
	private Node rotateRight(Node n) {
		Node newRoot = n.left;
		n.left = newRoot.right;
		newRoot.right = n;
		bfAndheight(newRoot.left);
		bfAndheight(newRoot.right);
		bfAndheight(newRoot);
		return newRoot;
	}
	private Node rotateRL(Node n) {
		n.right = rotateRight(n.right);
		bfAndheight(n.left);
		bfAndheight(n.right);
		bfAndheight(n);
		return rotateLeft(n);
	}
	private Node rotateLR(Node n) {
		n.left = rotateLeft(n.left);
		bfAndheight(n.left);
		bfAndheight(n.right);
		bfAndheight(n);
		return rotateRight(n);
	}
	
	private int max(int a, int b) {
		if (a < b) {
			return b;
		} else {
			return a;
		}
	}
	
	@Override
	public E max() {
		if (isEmpty()) {
			return null;
		} else if (root.right != null) {
			Node max = max(root.right);
			return max.data;
		} else {
			return root.data;
		}
	}
	private Node max(Node r) {
		if (r.right == null) {
			return r;
		} else {
			return max(r.right);
		}
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (root == null);
	}

	@Override
	public E min() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return null;
		} else if (root.left != null) {
			Node min = min(root.left);
			return min.data;
		} else {
			return root.data;
		}
	}
	private Node min(Node l) {
		if (l.left == null) {
			return l;
		} else {
			return min(l.left);
		}
	}

	@Override
	public boolean contains(E item) {
		if (item == null) {
			return false;
		} else {
			return contains(item, root);
		}
	}
	private boolean contains(E item, Node n) {
		if (n == null) {
			return false;
		} else if (n.data.equals(item)) {
			return true;
		} else if (n.data.compareTo(item) < 0) {
			return contains(item, n.right);
		} else {
			return contains(item, n.left);
		}
	}

	@Override
	public boolean remove(E item) {
		if (item ==  null) {
			return false;
		}
		int i = size;
		root = removeRecurse(item, root);
		return (i > size);
	}
	private Node removeRecurse(E item, Node n) {
		if (n == null) {
			return n;
		}
		if (n.data.compareTo(item) < 0) {
			n.right = removeRecurse(item, n.right);
		} else if (n.data.compareTo(item) > 0) {
			n.left = removeRecurse(item, n.left);
		} else if (n.left != null && n.right != null) {
			Node succ = min(n.right);
			n.data = succ.data;
			n.right = removeRecurse(n.data, n.right);
			size--;
		} else if (n.left != null) {
			n = n.left;
			size--;
		} else {
			n = n.right;
			size--;
		}
		return rebalance(n);
	}

	@Override
	public Iterator<E> iterator() {
		return this.getInOrder().iterator();
	}
	private List<E> getInOrder() {
		if (isEmpty()) {
			return new ArrayList<E>(0);
		} else {
			List<E> l = new ArrayList<E>(size);
			getInOrder(root.left, l);
			l.add(root.data);
			getInOrder(root.right, l);
			return l;
		}
	}
	private void getInOrder(Node n, List<E> l){
		if (n == null) {
			return;
		} else {
			getInOrder(n.left, l);
			l.add(n.data);
			getInOrder(n.right, l);
			return;
		}
	}

	@Override
	public List<E> getPostOrder() {
		if (isEmpty()) {
			return new ArrayList<E>(0);
		} else {
			List<E> l = new ArrayList<E>(size);
			getPostOrder(root.left, l);
			getPostOrder(root.right, l);
			l.add(root.data);
			return l;
		}
	}
	private void getPostOrder(Node n, List<E> l) {
		if (n == null) {
			return;
		} else {
			getPostOrder(n.left, l);
			getPostOrder(n.right, l);
			l.add(n.data);
			return;
		}
	}

	@Override
/*	public List<E> getLevelOrder() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return new ArrayList<E>(0);
		} else {
			List<E> l = new ArrayList<E>(size);
			List<Node> nodes = new ArrayList<Node>();
			nodes.add(root);
			getLevelOrder(l, nodes);
			return l;
		}
	}
	private void getLevelOrder(List<E> l, List<Node> nodes) {
		if (nodes.isEmpty()) {
			return;
		}
		Node n = nodes.remove(0);
		l.add(n.data);

		if(n.left != null) {
			nodes.add(n.left);
		}
		if (n.right != null) {
			nodes.add(n.right);
		}
		if (!nodes.isEmpty()) {
			getLevelOrder(l, nodes);
		}
	}*/
	public List<E> getLevelOrder() {
		List<E> l = new ArrayList<E>(size);
		List<Node> nodes = new ArrayList<Node>();
		if (isEmpty()) {
			return l;
		}
		nodes.add(root);
		while(!nodes.isEmpty()) {
			Node n = nodes.remove(0);
			l.add(n.data);
			if (n.left != null) {
				nodes.add(n.left);
			} 
			if (n.right != null) {
				nodes.add(n.right);
			}
		}
		return l;
	}

	@Override
	public List<E> getPreOrder() {
		if (isEmpty()) {
			return new ArrayList<E>(0);
		} else {
			List<E> l = new ArrayList<E>(size);
			l.add(root.data);
			getPre(root.left, l);
			getPre(root.right, l);
			return l;
		}
	}
	private void getPre(Node n, List<E> l) {
		if (n == null) {
			return;
		} else {
			l.add(n.data);
			getPre(n.left, l);
			getPre(n.right, l);
			return;
		}
	}

	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	private class Node {
		E data;
		Node left = null;
		Node right = null;
		int height = 0;
		int bf = 0;
		
		private Node(E data) {
			this.data = data;
		}
	}
}
