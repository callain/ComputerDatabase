package com.excilys.computerdatabase.validator;

public interface Validator<T> {
	public String validate(T obj);
}
