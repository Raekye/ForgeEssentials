package com.forgeessentials.core.data.api;

public interface IDataDriver {
	public void registerClass(ITypeInfo tagger);
	public String getName();
	public boolean saveObject(TypeWrapper type, Object o);
	public Object loadObject(TypeWrapper type, String loadingKey);
	public Object[] loadAllObjects(TypeWrapper type);
	public boolean deleteObject(TypeWrapper type, String loadingKey);
	public void parseConfigs();
	public void serverStart();
	public void getType();
	public boolean hasLoaded();
}
