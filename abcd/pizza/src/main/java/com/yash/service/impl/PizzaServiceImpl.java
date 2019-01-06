package com.yash.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yash.model.PizzaOrder;
import com.yash.service.IPizzaService;
import com.yash.utill.ApiStatus;
import com.yash.utill.PizzaOrderComparator;
import com.yash.utill.Utills;

@Service
public class PizzaServiceImpl implements IPizzaService {

	@Autowired
	private ApiStatus apiStatus;

	private List<PizzaOrder> pizzaList;
	private BufferedReader bufferedReader;
	private FileOutputStream outputStream;


	public String readFile(File file) {
		// TODO Auto-generated method stub
		pizzaList = new ArrayList<>();
		String output = "";
		FileReader fr;
		try {
			fr = new FileReader(file);
			bufferedReader = new BufferedReader(fr);
			String line;

			String test = bufferedReader.readLine(); // to remove order and date text from line
			
			// to test file is correct or not
			if(test.substring(0, 5).equals("Order"))
			{
			while ((line = bufferedReader.readLine()) != null) {
				// process the line
				splitFileData(line);
			}

			bufferedReader.close();
			// file.delete();
			Collections.sort(pizzaList, new PizzaOrderComparator());
			output = generateOutput();
			}
			else
			{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			return null;
		
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// deleteFile(file);
		return output;
	}

	public String deleteFile(File file) {
		file.delete();
		System.err.println("delete");
		return "file deleted";
	}

	private void splitFileData(String data) {

		if (!data.equals("")) {

			data = data.replaceAll("\\s+", " ");
//			try {
			String arr[] = data.split(" ");

			Long l = Long.parseLong(arr[1]);
			long timeStamp = l * 1000;
			Date date = new Date(timeStamp);
			pizzaList.add(new PizzaOrder(arr[0], date));
//			}
			
//			catch (Exception e) {
//				// TODO: handle exception
//				
//				
//			}

		}

	}

	private String generateOutput()  {

		String finalData = "Given Pizza Orders with time are : ";
		for (PizzaOrder orders : pizzaList) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(orders.getOrderDate());

			finalData += "Order " + orders.getOrderName() + " at : " + time + " ";

		}
		try {

		File file = new File(Utills.FILE_PATH + Utills.OUTPUT_FILE);
		if (!file.exists()) {
			
				file.createNewFile();
			
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(finalData);
		bw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}

		return finalData;

	}

	@Override
	public ApiStatus orderService(MultipartFile file)  {
		// TODO Auto-generated method stub
			createDirectory();

		File inputFile = new File(Utills.FILE_PATH + file.getOriginalFilename());
		try {
		
		
			inputFile.createNewFile();
		

		outputStream = new FileOutputStream(inputFile);
		outputStream.write(file.getBytes());

		String message = readFile(inputFile);
		

		inputFile.delete();

		if(message!=null) {
		apiStatus.setMessage(message);
		apiStatus.setFilePath(Utills.FILE_PATH+Utills.OUTPUT_FILE);
		apiStatus.setStatus("success");
		return apiStatus;
		}
		else
		{
			
			return null;
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputFile.delete();
			return null;
		}
		finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void createDirectory() {
		// TODO Auto-generated method stub
		File file = new File(Utills.FILE_PATH);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.err.println("Directory is created!");
			} else {
				System.err.println("Failed to create directory!");
			}

		}
	}

}