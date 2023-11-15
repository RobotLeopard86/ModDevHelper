package net.rl86.mdh.loot.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Retention(RUNTIME)
@Target(TYPE)
public @interface LootMemberType {

	public enum MemberType {
		PREDICATE("Predicate"),
		FUNCTION("Function"),
		NUMPROVIDER("Number Provider"),
		TABLE("Root"),
		LIST("Value List"),
		POOL("Pool"),
		ENTRY("Entry");

		private String displayName;

		private MemberType(String name) {
			displayName = name;
		}

		@Override
		public String toString() {
			return displayName;
		}
	}

	public MemberType value();
}
