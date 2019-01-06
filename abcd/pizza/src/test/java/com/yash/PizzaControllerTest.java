package com.yash;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.yash.controller.PizzaController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value=PizzaController.class , secure=false)
public class PizzaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
    @Test
    public void testYO() throws Exception
    {	      

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pizza/yo").accept(
				MediaType.APPLICATION_JSON);
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	
		String expected = "{\"status\":\"s\",\"filePath\":\"a\",\"message\":\"m\"}";


		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testPost() throws Exception
    {
    	 MockMultipartFile firstFile = new MockMultipartFile("file", "input.txt", "text/plain", "Hii".getBytes());
    	 MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    	 MvcResult result =   mockMvc.perform(MockMvcRequestBuilders.multipart("/pizza/orders")
                         .file(firstFile))
                     .andExpect(status().isOk()).andReturn();
    	 
    	 System.err.println(result.getResponse().getContentAsString());
    }
    

}
