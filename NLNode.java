import java.util.Comparator;
import java.util.Iterator;

/*
 * The NLNode class is responsible for creating the nodes which will form the tree,
 * including assigning the parent node and its children
 */
public class NLNode<T> {
	private NLNode<T> parent;
	private ListNodes<NLNode<T>> children;
	private T data;

	public NLNode() {
		parent = null;
		data = null;
		children = new ListNodes<NLNode<T>>();
	}

	public NLNode(T d, NLNode<T> p) {
		children = new ListNodes<NLNode<T>>();
		data = d;
		parent = p;
	}

	public void setParent(NLNode<T> p) {
		this.parent = p;
	}

	public NLNode<T> getParent() {
		return this.parent;
	}

	public void addChild(NLNode<T> newChild) {
		newChild.setParent(this);
		this.children.add(newChild);
	}

	public Iterator<NLNode<T>> getChildren() {
		return this.children.getList();
	}

	public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
		return this.children.sortedList(sorter);
	}

	public T getData() {
		return this.data;
	}

	public void setData(T d) {
		this.data = d;
	}

	public static void main(String[] args) {

	}

}
