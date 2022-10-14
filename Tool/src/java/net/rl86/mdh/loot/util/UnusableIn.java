package net.rl86.mdh.loot.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.rl86.mdh.util.CommonUtilities.LootType;

@Retention(RUNTIME)
@Target(TYPE)
public @interface UnusableIn {
	public LootType[] value();
}
