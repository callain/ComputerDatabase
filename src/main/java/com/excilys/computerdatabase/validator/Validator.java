package com.excilys.computerdatabase.validator;

public interface Validator<T> {
	public int validate(T obj);
}
