package com.forgeessentials.core.data.api;

public abstract class IMultiValInfo<T> implements ITypeInfo<T> {
	@Override
	public final boolean canSaveInline() {
		return false;
	}
	
	@Override
	public abstract Class<?>[] getGenericTypes();
}
