package com.yash.utill;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL) 
@JsonPropertyOrder(value= {"status","filePath","message"})
public class ApiStatus {
	@ApiModelProperty(notes="This will give status of API")
	private String status;
	@ApiModelProperty(notes="This will generate status message")
	private String message;
	
	@ApiModelProperty(notes="This will generate the location of output file")

	private String filePath;

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
