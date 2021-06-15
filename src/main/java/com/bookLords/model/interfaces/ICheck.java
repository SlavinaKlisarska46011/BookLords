package com.bookLords.model.interfaces;

import com.bookLords.model.exceptions.InvalidDataException;

public interface ICheck {

	default public String isValidString(String string) throws InvalidDataException {
		if (string != null && !string.isEmpty()) {
			return string;
		} else {
			throw new InvalidDataException("Invalid string!");
		}
	}

	default public int isValidId(int id) throws InvalidDataException {
		if (id > 0) {
			return id;
		} else {
			throw new InvalidDataException("Invalid id!");
		}
	}
}
