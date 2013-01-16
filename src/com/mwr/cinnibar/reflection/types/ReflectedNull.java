package com.mwr.cinnibar.reflection.types;

import com.mwr.cinnibar.api.Protobuf.Message.Argument;

public class ReflectedNull extends ReflectedType {

	@Override
	public Argument getArgument() {
		return Argument.newBuilder().setType(Argument.ArgumentType.NULL).build();
	}
	
	@Override
	public Object getNative() {
		return null;
	}
	
	@Override
	public Class<?> getType() {
		return null;
	}
	
}
