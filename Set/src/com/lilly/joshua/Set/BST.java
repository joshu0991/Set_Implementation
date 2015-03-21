package com.lilly.joshua.Set;

import java.util.Iterator;
import java.util.Stack;

public class BST<T extends Comparable<T> > implements Iterable<T> {

	//root inits to null.
	BSTNode<T> root = null;
	
	//keep track of the size for debugging purposes.
	private int size = 0;

	//tree iterator class representing an iterator over the bst.
	private class TreeIterator<T> implements Iterator<T> {
		Stack<T> stack = new Stack<T>();
		BSTNode<?> cursor = BST.this.root;

		@Override
		public boolean hasNext() {
			return (!stack.empty() || cursor != null);
		}// has next

		
		@SuppressWarnings("unchecked")
		@Override
		//return the next element from the set. These are default lexographical order due
		//to the fact that elements are constrained to be comparable.
		public T next() {
			T savedCursor = null;
			// go left until we hit the left most
			// when we hit left most cursor will = null
			while (!stack.isEmpty() || cursor != null) {
				if (cursor != null) {
					stack.push((T) cursor);
					cursor = cursor.left;
				} else {
					if (!stack.isEmpty()) {
						cursor = (BSTNode<T>) stack.pop();
						savedCursor = (T) cursor.data;
						cursor = cursor.right;
						return savedCursor;
					}
				}
			}
			return savedCursor;
		}// next

	}// treeiterator class
	
	private class BSTNode<T> {
		BSTNode<T> left;
		BSTNode<T> right;
		T data;
		
		//return true if this class has a left element.
		public boolean hasLeft(){
			if(left == null){
				return false;
			} else { 
				return true;
			}
		}
		
		//return true if this class has a right element.
		public boolean hasRight(){
			if(right == null){
				return false;
			} else {
				return true;
			}
		}
		
		//constructor insert the data.
		BSTNode(T data){
			this.data = data;
			left = null;
			right  = null;
		}//BSTNode constructor
		
		//to string for bstclass. for testing only...
		public String toString(){
			if(this.data != null){
				return (String) data;
			} else {
				return null;
			}
		}
		
	}//BSTNode class

	//iterator for retrunign an iterator to this tree.
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new TreeIterator<T>();
	}//iterator

	
	//non-recursive function for inserting data
	public boolean insert(T data){
		if(root == null){//if the root is null we found the location
			root = new BSTNode<T>(data);
			++size;
			return true;
		} else { //else enter the recursive insert
			return insert(root, data);
		}
	}//insert
	
	//insert a value into a bst recursivly. 
	private boolean insert(BSTNode<T> p, T value){
		int comp = p.data.compareTo(value);
		//if it's equal to zero the data is already in the  list return false 
		//no duplicates in a bst
		if(comp == 0){
			return false;
		}
		//if comp is less than zero we need to insert the data to the left
		//if the location is empty or traverse to the left.
		else if(comp > 0){
			//if left is null we have found the location.
			if(p.left == null){
				++size;
				p.left = new BSTNode<T>(value);
				return true;
			} else {
				return insert(p.left, value);//else we need to recurse left.
			}
		//if comp is greater than zero we need to insert the data to the right
		//if the location is empty or traverse right.
		} else if(comp < 0) {
			//if right is null we have the location
			if(p.right == null){
				++size;
				p.right = new BSTNode<T>(value);
				return true;
			} else {
				return insert(p.right, value);//else we need to recurse right
			}
		}
		//some unforeseen thing happened if we're here.
		return false;
	}//insert
	
	//private recursive version called by find.
	private BSTNode<T> search(T val, BSTNode<T> root){
		if (root != null) {
			if (root.data.compareTo(val) == 0) {
				//if (root != null)
				//	System.out.println("Found " + root.data);
					return root;
			} else if (root.data.compareTo(val) > 0) {
				return search(val, root.left);
			} else if (root.data.compareTo(val) < 0) {
				return search(val, root.right);
			} else {// we are here by mistake return null for debugging.
				System.out.println("Failed");
				return null;
			}
		} else {
			//System.out.println("Failed");
			return null;
		}
	}//search
	
	//public facing non-recursive find. Just calls the recursive version.
	public BSTNode<T> find(T val){
		if(root == null){
			return root;
		}else{
			return search(val, root);
		}
	}//find
	
	//find out if a value exist in a tree.
	public boolean hasElement(T data){
		if(find(data) != null || root == null){
			return true;
		}else{
			return false;
		}
	}//hasElement
	
	public boolean delete(T val){
		boolean removed = false;
		// the value isn't in the tree.
		if (!hasElement(val)) {
			return false;
		}
		
		BSTNode<T> parent = findParent(val, root);
		BSTNode<T> target = find(val);
		int count = countChildren(target);
		
		//case where the root = parent.
		if(parent == target){
			//one element in the tree that we will be removing
			if(count == 0){
				cleanUp(root);
				return true;
			} else if(count == 1){
				if(root.hasLeft() ){
					root = root.left;
					return true;
				}else{
					root = root.right;
					return true;
				}
			}else if(count == 2){
				return true;
			}
		}
		
		switch (count) {
		
		// it's a leaf hack it off!
		case (0):
			switch (leftOrRight(parent, val)) {
			
			case LEFT:
				parent.left = null;
				removed = true;
				break;
			case RIGHT:
				parent.right = null;
				removed = true;
				break;
			
			}
		break;
		
		//one child
		case (1):
			
			switch (leftOrRight(parent, val)) {
			//target lies to the left of the parent.
			case LEFT:
				switch(leftOrRight(target, val)){
				//the target has a child on the left.
				case LEFT:
					parent.left = target.left;
					removed = true;
					cleanUp(target);
					break;
				//the target has a child on the right.
				case RIGHT:
					parent.left = target.right;
					removed = true;
					cleanUp(target);
					break;
				}
				break;
			//target lies to the right of parent.
			case RIGHT:
				switch(leftOrRight(target, val)){
				//target has a child on the left.
				case LEFT:
					parent.right = target.left;
					removed = true;
					cleanUp(target);
					break;
				//target has a child on the right.	
				case RIGHT:
					parent.right = target.right;
					removed = true;
					cleanUp(target);
					break;
				}
				break;
			}
		break;
		
		//two children
		case(2):
			System.out.println("Two children");
			removed = true;
			break;
		
		}
		//System.out.println("Target " + target);
		//System.out.println("Prev " + parent);
		System.out.println("Deleted " + target);
		return removed;
	}//delete
	
	@SuppressWarnings("unchecked")
	private BSTNode<T> findParent(T val, BSTNode<T> root){
		//if this is the trees root we have a special case.
		//parent = target
		if(root.data.compareTo(val) == 0){
			return root;
		} else {
			return findParent(root, val,  null);
		}
	}//findParent
	
	
	
	private BSTNode<T> findParent(BSTNode<T> root, T val, BSTNode<T> parent){
			if (root != null) {
				if (root.data.compareTo(val) == 0) {
					if (root != null)
						System.out.println("Found " + parent.data);
						return parent;
				} else if (root.data.compareTo(val) > 0) {
					return findParent(root.left, val, root);
				} else if (root.data.compareTo(val) < 0) {
					return findParent(root.right, val, root);
				} else {// we are here by mistake return null for debugging.
					System.out.println("Failed");
					return null;
				}
			} else {
				//System.out.println("Failed");
				return null;
			}
	}	
	
	//decide where the node is
	private leftRight leftOrRight(BSTNode<T> node, T val){
		if(node != root){
		if(node.left != null){
			if(node.left.data.compareTo(val) == 0){
				return leftRight.LEFT;
			}
		}
		if(node.right != null) {
			if(node.right.data.compareTo(val) == 0){
				return leftRight.RIGHT;
			}
		}
		}
		//Should only end up here when the root is the parent and the target
		//since we previously guaranteed that the data passed to this function
		//was a parent node.
		return null;
	}
	
	private int countChildren(BSTNode<T> node){
		int counter = 0;
		if(node.hasLeft()){
			++counter;
		}
		if(node.hasRight()){
			++counter;
		}
		return counter;
	}//countchildren
	
	private enum leftRight{
		LEFT, RIGHT;
	}//left or right enum
	
	//make sure removed node gets slotted for garbage collection and doesn't
	//hang out where it isn't wanted.
	private void cleanUp(BSTNode<T> node){
		node.left = null;
		node.right = null;
		node.data = null;
	}

}//BST.java
