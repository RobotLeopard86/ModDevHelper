package net.rl86.mdh.loot.predicates;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

@Target(TYPE)
public @interface ResourceName {
	
	public String value();
}
