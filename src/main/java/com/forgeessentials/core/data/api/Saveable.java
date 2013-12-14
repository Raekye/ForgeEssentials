package com.forgeessentials.core.data.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Saveable {
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface SaveableField {
		String overrideParent() default "";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.FIELD })
	public @interface UniqueKey {
		// hmmm
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface Reconstructor {
		// hmmm
	}
}
