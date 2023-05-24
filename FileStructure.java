import java.util.ArrayList;
import java.util.Iterator;

/*
 * FileStructure class forms the recursive algorithm responsible for searching through the different files and folders in the system
 */
public class FileStructure {
	private NLNode<FileObject> root;

	/*
	 * Constructor creates the tree with its nodes and branches
	 */
	public FileStructure(String fileObjectName) throws FileObjectException {
		FileObject currFile = new FileObject(fileObjectName);
		root = new NLNode<FileObject>(currFile, null);
		if (currFile.isDirectory())
			collect(root);

	}

	public NLNode<FileObject> getRoot() {
		return root;
	}

	/*
	 * Searches the folder for files of a specific type, such as java or txt files,
	 * and stores the path towards them in a container
	 */
	public Iterator<String> filesOfType(String type) {
		ArrayList<String> fileList = new ArrayList<String>();
		String[] path;

		if (root.getData().isFile()) {
			path = root.getData().getName().split(".");
			if (path[1] == type) {
				fileList.add(root.getData().getLongName());
				return fileList.iterator();
			}
		}

		return pathCollect(root, fileList, type);
	}

	/*
	 * Looks for a specific file within the tree and returns the path to the file
	 */
	public String findFile(String name) {
		return search(root, name);
	}

	/*
	 * Private recursive helper method which creates the tree of nodes and returns
	 * it to the constructor
	 */
	private NLNode<FileObject> collect(NLNode<FileObject> r) {
		Iterator<FileObject> childrenList = r.getData().directoryFiles();
		FileObject f;

		while (childrenList.hasNext()) {
			NLNode<FileObject> child = new NLNode<FileObject>();
			f = childrenList.next();
			child.setData(f);

			if (f.isFile()) {
				r.addChild(child);
				child.setParent(r);
				childrenList.remove();
			}

			else {
				r.addChild(collect(child));
				collect(child).setParent(r);
				childrenList.remove();
			}
		}

		return r;
	}

	/*
	 * Private recursive helper method which returns an iterator of a container
	 * containing all paths towards files of a specific type
	 */
	private Iterator<String> pathCollect(NLNode<FileObject> r, ArrayList<String> list, String type) {
		Iterator<NLNode<FileObject>> childrenList = r.getChildren();
		String[] path;

		while (childrenList.hasNext()) {
			NLNode<FileObject> child = new NLNode<FileObject>();
			child = childrenList.next();

			if (child.getData().isFile()) {
				path = child.getData().getName().split("[.]");
				if (type.equals("." + path[1]))
					list.add(child.getData().getLongName());
				childrenList.remove();
			} else {
				pathCollect(child, list, type);
				childrenList.remove();
			}
		}

		return list.iterator();
	}

	/*
	 * Private recursive helper method which looks for a specific file within the
	 * created tree
	 */
	private String search(NLNode<FileObject> r, String name) {
		Iterator<NLNode<FileObject>> childrenList = r.getChildren();
		String file;

		while (childrenList.hasNext()) {
			NLNode<FileObject> child = new NLNode<FileObject>();
			child = childrenList.next();

			if (child.getData().isFile()) {
				if (child.getData().getName().equals(name))
					return child.getData().getLongName();
			} else {
				file = search(child, name);
				if (file != "")
					return file;
			}
		}

		return "";
	}
}
