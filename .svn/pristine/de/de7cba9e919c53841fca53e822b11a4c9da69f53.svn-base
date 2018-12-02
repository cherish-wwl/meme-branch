package com.meme.core.validator.entity;

import java.util.Map;

public class ColumnRule {

	private String column;
	private Map<String, String> rules;

	public ColumnRule(){
	}
	
	public ColumnRule(String column, Map<String, String> rules) {
		this.column = column;
		this.rules = rules;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Map<String, String> getRules() {
		return rules;
	}

	public void setRules(Map<String, String> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(column).append(":{");
		if (null != rules && rules.size() > 0) {
			int i = 0;
			for (Map.Entry<String, String> entry : rules.entrySet()) {
				if (i == 0)
					sb.append(entry.getKey()).append(":").append(entry.getValue());
				else
					sb.append(",").append(entry.getKey()).append(":").append(entry.getValue());
				i++;
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
