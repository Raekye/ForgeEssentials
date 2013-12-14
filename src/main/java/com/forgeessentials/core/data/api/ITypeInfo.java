package com.forgeessentials.core.data.api;

public interface ITypeInfo<T> {
	public boolean canSaveInline();
	public void construct();
	public TypeWrapper getTypeOfField(String field);
	public String[] getFieldList();
	public TypeData getTypeDataFromObject(T obj);
	public T reconstruct(IReconstructData data);
	public TypeWrapper getType();
	public Class<?>[] getGenericTypes();
	public ITypeInfo<?> getTypeInfoForField(String field);
}
