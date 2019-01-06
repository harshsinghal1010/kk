package com.yash.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yash.utill.ApiStatus;


public interface IPizzaService {
	
	public ApiStatus orderService(MultipartFile file) throws IOException;

}
