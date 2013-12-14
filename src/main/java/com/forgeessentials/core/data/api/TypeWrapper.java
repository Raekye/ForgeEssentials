package com.forgeessentials.core.data.api;

public class TypeWrapper {
	private final Class<?> type;
	private final Class<?>[] parameters;
	
	public static final String FILE_COMMA_REPLACEMENT = "_H_";
	public static final String FILE_ARRAY_REPLACEMENT = "_ARR_";
	
	public TypeWrapper(Class<?> wrapped) {
		this(wrapped, new Class[] {});
	}
	
	public TypeWrapper(Class<?> type, Class<?>... parameters) {
		this.type = type;
		this.parameters = parameters;
	}
	
	public Class<?> getType() {
		return this.type;
	}
	
	public boolean isInterface() {
		return this.getType().isInterface();
	}
	
	public boolean isEnume() {
		return this.getType().isEnum();
	}
	
	public boolean isArray() {
		return this.getType().isArray();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Class) {
			// classes are final; only loaded once by a classloader
			// shouldn't be classloader issues... otherwise need to check canonical name
			return o == this.getType();
		} else if (o instanceof TypeWrapper) {
			return ((TypeWrapper) o).getType() == this.getType();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.type.hashCode();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	public Class<?>[] getParameters() {
		return this.parameters;
	}
	
	public boolean hasParameters() {
		return this.getParameters().length > 0;
	}
	
	public String getName() {
		// canonical name replaces dollar signs in inner classes with dots
		return this.getType().getCanonicalName() + this.getParameterList();
	}
	
	private String getParameterList() {
		String bin = "(";
		for (int i = 0; i < this.getParameters().length; i++) {
			if (i > 0) {
				bin += ",";
			}
			bin += this.getParameters()[i].getCanonicalName();
		}
		return bin + ")";
	}
	
	public String getFileSafeName() {
		return this.getName().replaceAll("\\, ", FILE_COMMA_REPLACEMENT)
			                 .replaceAll("\\[\\]", FILE_ARRAY_REPLACEMENT);
	}
}
