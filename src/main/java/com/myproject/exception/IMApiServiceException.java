package com.myproject.exception;

import java.util.List;

/**
 * 
 * @author anand
 *
 */
public class IMApiServiceException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054802655164905162L;

	private final IMApiServiceExceptionType exceptionType;

	private List<ParamDetail> paramDetail;

	public IMApiServiceException(
			final IMApiServiceExceptionType exceptionType) {
		this(exceptionType, null, null, null);
	}

	public IMApiServiceException(
			final IMApiServiceExceptionType exceptionType,
			final List<ParamDetail> paramDetail) {
		this(exceptionType, paramDetail, null, null);
	}

	public IMApiServiceException(
			final IMApiServiceExceptionType exceptionType,
			final List<ParamDetail> paramDetail, final String message) {
		this(exceptionType, paramDetail, message, null);
	}

	public IMApiServiceException(
			final IMApiServiceExceptionType exceptionType,
			final List<ParamDetail> paramDetail, final Throwable cause) {
		this(exceptionType, paramDetail, null, cause);

	}

	public IMApiServiceException(
			final IMApiServiceExceptionType exceptionType,
			final List<ParamDetail> paramDetail, final String message,
			final Throwable cause) {
		super(message, cause);
		this.exceptionType = exceptionType;
		this.paramDetail = paramDetail;
	}

	public List<ParamDetail> getParamDetailList() {
		return this.paramDetail;
	}

	public void setParamDetailList(List<ParamDetail> paramDetail) {
		 this.paramDetail = paramDetail;
	}
	
	public IMApiServiceExceptionType getExceptionType() {
		return this.exceptionType;
	}
}
