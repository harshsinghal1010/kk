package com.yash.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yash.model.PizzaOrder;
import com.yash.service.IPizzaService;
import com.yash.utill.ApiStatus;
import com.yash.utill.Endpoints;
import com.yash.utill.Utills;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(Endpoints.PIZZA)
public class PizzaController {

	@Autowired
	private ApiStatus apiStatus;

	private List<PizzaOrder> pizzaList;

	private Integer count;

	private File inputFile;
	private FileOutputStream outputStream;

	@Autowired
	private IPizzaService service;
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	


	@ApiOperation(value = "Take txt file as an input and return the status of it", response=ApiStatus.class)
	@PostMapping(path = Endpoints.ORDERS, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getFile(@RequestParam("file") MultipartFile uploadfile) throws IOException {

		if (uploadfile.getContentType().equals("text/plain")) {

			
//			String response = service.readFile(inputFile);
			ApiStatus api = service.orderService(uploadfile);
			if(api!=null)
			return showResponse(api, 200);
			
			else {
				apiStatus.setMessage("Wrong input file");
				apiStatus.setStatus("error");
				apiStatus.setFilePath(null);
				return showResponse(apiStatus, 400);
			}
				
				
		} else
			apiStatus.setMessage("Wrong input file");
			apiStatus.setStatus("error");
			apiStatus.setFilePath(null);
			return showResponse(apiStatus, 400);

	}

	
	
//	@ApiOperation(value = "Return output in output.txt file")
//	@GetMapping(Endpoints.OUTPUT)
//	private ResponseEntity<?> downloadFile1() throws IOException {
//
//		File file = new File(Utills.FILE_PATH + Utills.OUTPUT_FILE);
//		if(file.exists()) {
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//		return ResponseEntity.status(HttpStatus.OK)
//				// Content-Disposition
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//				// Content-Length
//				.contentLength(file.length()).body(resource);
//				
//		}
//		else
//			return showResponse("error", "file not found", 400);
//	}

	private ResponseEntity<?> showResponse(ApiStatus status, int code) {

		return ResponseEntity.status(code).body(status);
	}

	@ExceptionHandler({ Exception.class })
	private ResponseEntity<?> handleException(Exception e) throws IOException {
		apiStatus.setStatus("error");
		apiStatus.setFilePath(null);
		if (e.getMessage().equals("1")) {
			outputStream.close();
			inputFile.delete();
			apiStatus.setMessage("Wrong input file");

		} else
			apiStatus.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiStatus);
	}
	
	
	@GetMapping("/yo")
	private ResponseEntity<?> hello() {
		ApiStatus apiStatus = new  ApiStatus();
		apiStatus.setFilePath("a");
		apiStatus.setMessage("m");
		apiStatus.setStatus("s");
		return ResponseEntity.status(HttpStatus.OK).body(apiStatus);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
