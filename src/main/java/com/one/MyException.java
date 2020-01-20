package com.one;

public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException() {
		super();
	}

	public MyException(String s) {
		super(s);
	}

	public MyException(Throwable e) {
		super(e);
	}

	public MyException(String s, Throwable e) {
		super(s, e);
	}
}
