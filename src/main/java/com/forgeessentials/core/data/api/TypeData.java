package com.forgeessentials.core.data.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class TypeData implements IReconstructData, Serializable {
	private static final long serialVersionUID = 7472587461049419571L;
	
	private final TypeWrapper type;
	private String uniqueKey;
	private final Map<String, Object> members;

	public TypeData(TypeWrapper type) {
		this.type = type;
		this.members = new HashMap<String, Object>();
	}
	
	public void putField(String name, Object value) {
		this.members.put(name, value);
	}
	
	@Override
	public Object getFieldValue(String name) {
		return this.members.get(name);
	}
	
	public boolean hasField(String field) {
		return this.members.containsKey(field);
	}
	
	public Set<Entry<String, Object>> getAllFields() {
		return this.members.entrySet();
	}
	
	public void setUniqueKey(String key) {
		this.uniqueKey = key;
	}

	@Override
	public String getUniqueKey() {
		return this.uniqueKey;
	}

	@Override
	public Class<?> getType() {
		return this.getWrapper().getType();
	}

	@Override
	public Collection<?> getAllValues() {
		return this.members.values();
	}
	
	public TypeWrapper getWrapper() {
		return this.type;
	}
	
	@Override
	public String toString() {
		String bin = "{";
		bin += "type=" + this.getType().getCanonicalName();
		bin += "," + "unique=" + this.getUniqueKey();
		bin += "[";
		for (Entry<String, Object> field : this.getAllFields()) {
			bin += field.getKey() + "=" + field.getValue().toString() + ",";
		}
		bin = bin.substring(bin.length() - ",".length());
		bin += "]";
		return bin + "}";
	}
}
