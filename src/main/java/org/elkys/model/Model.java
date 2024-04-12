package org.elkys.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Model {

	public static class TemplateObject implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@JsonProperty("_value")
		private String value;
		
		@JsonProperty("_required")
		private Boolean required;
		
		@JsonProperty("default")
		private Object defaultValue;

		public TemplateObject() {
			super();
		}

		public TemplateObject(String value, Boolean required, Object defaultValue) {
			super();
			this.value = value;
			this.required = required;
			this.defaultValue = defaultValue;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Boolean getRequired() {
			return required;
		}

		public void setRequired(Boolean required) {
			this.required = required;
		}

		public Object getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(Object defaultValue) {
			this.defaultValue = defaultValue;
		}
		
	}
	
}
