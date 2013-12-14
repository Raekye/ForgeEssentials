package com.forgeessentials.core.data.api;

import java.util.Map;

public class TypeEntryInfo implements ITypeInfo {
	
	private final TypeWrapper parent;
	private final Map<String, TypeWrapper> types;
	
	public TypeEntryInfo(TypeWrapper parent, Map<String, TypeWrapper> types) {
		this.parent = parent;
		this.types = types;
	}

	@Override
	public boolean canSaveInline() {
		return true;
	}

	@Override
	public void construct() {
		// not used
	}

	@Override
	public TypeWrapper getTypeOfField(String field) {
		return this.types.get(field);
	}

	@Override
	public String[] getFieldList() {
		return this.types.keySet().toArray(new String[this.types.size()]);
	}

	@Override
	public TypeData getTypeDataFromObject(Object obj) {
		// should be overwritten
		return null;
	}

	@Override
	public Object reconstruct(IReconstructData data) {
		// pass
		return null;
	}
	
	public TypeWrapper getParentType() {
		return this.parent;
	}

	@Override
	public TypeWrapper getType() {
		return null; // TODO: hmmm
	}

	@Override
	public Class<?>[] getGenericTypes() {
		return this.types.values().toArray(new Class[this.types.size()]);
	}

	@Override
	public ITypeInfo getTypeInfoForField(String field) {
		return null;
	}

}
