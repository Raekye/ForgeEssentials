package com.forgeessentials.core.data.api;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.rmi.server.UID;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public abstract class TypeMultiValInfo implements ITypeInfo {
	
	private final TypeWrapper wrapper;
	private final Map<String, TypeWrapper> entryFields;
	private final Map<String, TypeWrapper> fields;
	private TypeEntryInfo entryInfo;
	
	public static final String UID = "_$EntryId$_";
	
	public TypeMultiValInfo(TypeWrapper wrapper) {
		this.wrapper = wrapper;
		this.fields = new HashMap<String, TypeWrapper>();
		this.entryFields = new HashMap<String, TypeWrapper>();
		this.entryFields.put(UID, new TypeWrapper(String.class));
	}

	@Override
	public boolean canSaveInline() {
		return false;
	}

	@Override
	public void construct() {
		this.construct(this.fields);
		this.constructEntry(this.entryFields);
		this.entryInfo = new TypeEntryInfo(this.wrapper, this.entryFields);
	}
	
	public abstract void constructEntry(Map<String, TypeWrapper> entryFields);
	
	public void construct(Map<String, TypeWrapper> entryFields) {
		// optional override
	}

	@Override
	public TypeWrapper getTypeOfField(String field) {
		// TODO: original src
		return this.fields.get(field);
	}

	@Override
	public String[] getFieldList() {
		return this.fields.keySet().toArray(new String[this.fields.size()]);
	}
	
	public String[] getEntryFieldList() {
		return this.entryFields.keySet().toArray(new String[this.entryFields.size()]);
	}

	@Override
	public TypeData getTypeDataFromObject(Object o) {
		Set<TypeData> datas = this.getTypeDatasFromObject(o);
		TypeData data = null;
		
		ITypeInfo entry = this.getEntryInfo();
		ITypeInfo tmpInfo;
		
		String id = new UID().toString();
		String unique = this.getType().getFileSafeName() + id;
		
		int i = 0;
		for (TypeData dat : datas) {
			for (Map.Entry<String, Object> e : dat.getAllFields()) {
				tmpInfo = entry.getTypeInfoForField(e.getKey());
				dat.putField(e.getKey(), null);
			}
		}
		
		this.addExtraDataForObject(o, data);
		
		data.setUniqueKey(unique);
		return data;
	}
	
	public abstract Set<TypeData> getTypeDatasFromObject(Object o);

	@Override
	public Object reconstruct(IReconstructData data) {
		Collection<?> values = data.getAllValues();
		TypeData[] arr = new TypeData[values.size()];
		int i = 0;
		for (Object o : values) {
			arr[i] = (TypeData) o;
		}
		return reconstruct(data, arr);
	}
	
	public abstract Object reconstruct(IReconstructData data, TypeData[] typedatas);

	@Override
	public TypeWrapper getType() {
		return this.wrapper;
	}

	@Override
	public Class<?>[] getGenericTypes() {
		return this.wrapper.getParameters();
	}

	@Override
	public ITypeInfo getTypeInfoForField(String field) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getEntryName() {
		return "DataVal";
	}
	
	public static String getUIDFromUnique(String unique) {
		return unique.substring(unique.lastIndexOf('_'));
	}
	
	public TypeEntryInfo getEntryInfo() {
		return this.entryInfo;
	}
	
	public TypeData getEntryData() {
		return new TypeData(new TypeWrapper(Map.Entry.class, this.wrapper.getParameters()));
	}
	
	public void addExtraDataForObject(Object o, TypeData data) {
		// optional override
	}
}
