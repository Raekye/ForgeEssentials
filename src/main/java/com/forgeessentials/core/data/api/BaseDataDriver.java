package com.forgeessentials.core.data.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.LinkedList;

public abstract class BaseDataDriver implements IDataDriver {
	
	private final Multimap<String, String> classRegister = HashMultimap.<String, String>create();
	private boolean hasLoaded;

	@Override
	public void registerClass(ITypeInfo tagger) {
		// pass
	}

	@Override
	public boolean saveObject(TypeWrapper type, Object o) {
		boolean flag = false;
		if (!this.classRegister.containsEntry(this.getName(), type.getName())) {
			this.registerClass(null); // TODO: hmm
			this.classRegister.put(this.getName(), type.getName());
		}
		ITypeInfo t = null;
		if (t != null) {
			flag = true;
			saveData(type, t.getTypeDataFromObject(o));
		}
	}

	@Override
	public Object loadObject(TypeWrapper type, String loadingKey) {
		Object newobj = null;
		TypeData data = this.loadData(type, loadingKey);
		ITypeInfo info = null; // TODO: hmm
		
		if (data != null && data.getAllFields().size() > 0) {
			newobj = this.createFromFields(data, info);
		}
		return newobj;
	}

	@Override
	public Object[] loadAllObjects(TypeWrapper type) {
		List<Object> li = new LinkedList<Object>();
		TypeData[] objectData = loadAll(type);
		ITypeInfo info = null; // TODO: hmmm
		
		Object tmp;
		if (objectData != null) {
			for (TypeData data : objectData) {
				tmp = this.createFromFields(data, info);
				li.add(tmp);
			}
		}
		
		return li.toArray(new Object[li.size()]);
	}

	@Override
	public boolean deleteObject(TypeWrapper type, String loadingKey) {
		return deleteData(type, loadingKey);
	}
	
	private Object createFromFields(TypeData data, ITypeInfo info) {
		Object val = null;
		for (Map.Entry<String, Object> entry : data.getAllFields()) {
			
		}
	}

	@Override
	public void parseConfigs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getType() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
