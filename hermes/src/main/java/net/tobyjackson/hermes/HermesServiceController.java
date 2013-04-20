package net.tobyjackson.hermes;

import java.util.Map;
import java.util.TreeMap;

import net.tobyjackson.hermes.service.SimpleStringService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@RequestMapping("/service")
public class HermesServiceController {

	// Stores computers
	private static final Map<Long, HermesService> serviceStorage = new TreeMap<Long, HermesService>();
	
	{
		System.out.println("Class");
	}
	static{
		System.out.println("Static");
		SimpleStringService s = new SimpleStringService();
		s.setResponse("MyFixedResponse");
		
		serviceStorage.put(0l, s);
	}
	
	@RequestMapping(method = RequestMethod.GET) 
	public @ResponseBody Map<Long, HermesService> getConfiguredServices() { 		
		return serviceStorage;
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.GET)	
	public @ResponseBody HermesService getService(@PathVariable long serviceID) {		
		return serviceStorage.get(serviceID);
	}
	
	@RequestMapping(method = RequestMethod.POST) 	 
	public @ResponseBody long addService(@RequestBody HermesService serviceDefinition) {		
		serviceStorage.put( (long)serviceStorage.size(), serviceDefinition);
		return serviceStorage.size()-1;
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.PUT)
	@ResponseBody
	public void putService(@PathVariable long serviceID, @RequestBody HermesService serviceDefinition) {		
		serviceStorage.put(serviceID, serviceDefinition);
	}	

	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteService(@PathVariable long id) {
		serviceStorage.remove( id );
	}

}
