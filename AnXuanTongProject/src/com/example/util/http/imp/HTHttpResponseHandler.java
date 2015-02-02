package com.example.util.http.imp;

import org.apache.http.Header;

import com.hengtong.library.enty.HTResponseObject;


public abstract class HTHttpResponseHandler {

	/**
	 * Fired when the request is started, override to handle in your own code
	 */
	public void onStart() {
	}

	/**
	 * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
	 */
	public void onFinish() {
	}

	/**
	 * Fired when a request returns successfully, override to handle in your own code
	 * @param statusCode the status code of the response
	 * @param headers return headers, if any
	 * @param responseBody the body of the HTTP response from the server
	 */
	public abstract void onSuccess(int statusCode, Header[] headers, HTResponseObject response);

	/**
	 * Fired when a request fails to complete, override to handle in your own code
	 * @param statusCode return HTTP status code
	 * @param headers return headers, if any
	 * @param responseBody the response body, if any
	 * @param error the underlying cause of the failure
	 */
	public abstract void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

}
