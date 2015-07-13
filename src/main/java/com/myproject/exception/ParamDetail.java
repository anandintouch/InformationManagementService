package com.myproject.exception;

public class ParamDetail {
	private String param;
	private String messageTemplate;

	public ParamDetail() {
		// Default Constructor
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(final String param) {
		this.param = param;
	}

	/**
	 * @return the messageTemplate
	 */
	public String getMessageTemplate() {
		return messageTemplate;
	}

	/**
	 * @param messageTemplate
	 *            the messageTemplate to set
	 */
	public void setMessageTemplate(final String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
}
