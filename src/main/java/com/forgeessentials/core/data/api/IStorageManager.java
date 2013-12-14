package com.forgeessentials.core.data.api;

public interface IStorageManager {
	public void registerDriver(String name, Class<? extends BaseDataDriver> clazz);
	public BaseDataDriver getRecommendedDriver();
	public void registerSaveableClass(Class<? extends ITypeInfo<?>> typeInfo, TypeWrapper type);
	public void registerSaveableClass(TypeWrapper type);
	public ITypeInfo<?> getTypeInfoForType(TypeWrapper type);
	public TypeData getTypeDataForType(TypeWrapper type);
	public TypeData getTypeDataForObject(TypeWrapper type);
	
}
