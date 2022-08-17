package net.rl86.mdh.loot.predicates;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.rl86.mdh.util.CommonUtilities.LootType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PredicateData {
	public LootType[] availableTo() default {};
	public String id() default "";
}
