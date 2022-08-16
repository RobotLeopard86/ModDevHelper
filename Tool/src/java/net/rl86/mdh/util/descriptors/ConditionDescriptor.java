package net.rl86.mdh.util.descriptors;

public class ConditionDescriptor {
	
	private String[] conditions;
	private String[] values;
	private boolean isAllTime;

	public ConditionDescriptor(String[] conditions, String[] values) {
		if(conditions.length != values.length) {
			throw new IllegalArgumentException("Arrays must be the same length!");
		}
		this.conditions = conditions;
		this.values = values;
	}
	
	public ConditionDescriptor() {
		isAllTime = true;
	}

	@Override
	public String toString() {
		if(!isAllTime) {
			String[] pairs = new String[conditions.length];
			
			for(int i = 0; i < pairs.length; i++) {
				pairs[i] = conditions[i] + ": " + values[i];
			}
			
			String result = "Condition:\n";
			
			for(int i = 0; i < pairs.length; i++) {
				result = result + pairs[i] + "\n";
			}
			
			return result;
		} else {
			return "Condition:\nAll Time";
		}
	}
	
	public String generateJsonLabel() {
		if(isAllTime) {
			return "\"\"";
		} else {
			String root = "";
			for(int i = 0; i < conditions.length; i++) {
				root = root + conditions[i] + "=" + values[i] + ",";
			}
			root = root.substring(0, root.length() - 1);
			return root;
		}
	}

}
