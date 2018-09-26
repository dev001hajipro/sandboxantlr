package com.github.dev001hajipro.lang.mu2;

// 言語が持つ型のラッパー
public class Value {

	public static Value VOID = new Value(new Object());
	
	final Object value;
	
	
	public Value(Object object) {
		this.value = object;
	}
	
	public Boolean asBoolean() {
		return (Boolean)value;
	}

	public Double asDouble() {
		return (Double)value;
	}
	
	public String asString() {
		return String.valueOf(value);
	}
	
	public boolean isDouble() {
		return value instanceof Double;
	}
	
	@Override
	public int hashCode() {
		if (value == null) {
			return 0;
		}
		return this.value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (value == obj) {
			return true;
		}
		if (value == null || obj == null || obj.getClass() != value.getClass()) {
			return false;
		}
		Value that = (Value)obj;
		return this.value.equals(that.value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
