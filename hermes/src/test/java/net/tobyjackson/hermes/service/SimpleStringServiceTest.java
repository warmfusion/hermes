package net.tobyjackson.hermes.service;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import net.tobyjackson.hermes.HermesService;

import org.junit.Test;

public class SimpleStringServiceTest {
	
	@Test
	public void shouldAcceptAndReturnAResponseMessage() {
			
		HermesService<String,String> service = new SimpleStringService();
		
		service.setResponse("Test");		
		assertThat(service.process(null), equalTo("Test"));
	}
	
	@Test
	public void shouldAcceptMultipleButReturnOnlyTheLastMessage() {
			
		HermesService<String,String> service = new SimpleStringService();
		
		service.setResponse("Test");
		service.setResponse("Test2");
		service.setResponse("Test3");
		assertThat(service.process(null), equalTo("Test3"));
	}
	

}
