package com.lilly.joshua.Set;

import java.util.Iterator;
import java.util.Stack;

public class BST<T extends Comparable<T> > implements Iterable<T> {

	BSTNode<T> root = new BSTNode<T>(null, null, null);
	
	private class TreeIterator<T> implements Iterator<T>{
		Stack<T> stack = new Stack<T>();
		BSTNode<?> cursor =BST.this.root;
		
		@Override
		public boolean hasNext() {
			System.out.println("Cursor is " + cursor.data);
			return (!stack.empty() || cursor != null);
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	/*
	private class BSTNode<T> {
		BSTNode<T> left;
		BSTNode<T> right;
		T data;
		
		BSTNode(BSTNode<T> left, BSTNode<T> right, T data){
			this.left = left;
			this.right = right;
			this.data = data;
		}
	}
	*/
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new TreeIterator<T>();
	}


}
