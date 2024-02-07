
package com.rumorify.ws.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author tokay
 *
 */
@Data
public class ApiError {
	private int status;

	private String message;

	private String path;

	private long timestam = new Date().getTime();

	private Map<String, String> validationErrors = new HashMap<>();
	
}
