package net.tobyjackson.hermes;

import java.util.Map;
import java.util.TreeMap;

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
	private static final Map<Long, String> serviceStorage = new TreeMap<Long, String>();
	
	{
		System.out.println("Class");
	}
	static{
		System.out.println("Static");
		serviceStorage.put(0l, "Hello World!");
	}
	
	@RequestMapping(method = RequestMethod.GET) 
	public @ResponseBody Map<Long, String> getConfiguredServices() { 		
		return serviceStorage;
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.GET)	
	public @ResponseBody String getService(@PathVariable long serviceID) {		
		return serviceStorage.get(serviceID);
	}
	
	@RequestMapping(method = RequestMethod.POST) 	 
	public @ResponseBody long addService(@RequestBody String serviceDefinition) {		
		serviceStorage.put( (long)serviceStorage.size(), serviceDefinition);
		return serviceStorage.size()-1;
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.PUT)
	@ResponseBody
	public void putService(@PathVariable long serviceID, @RequestBody String serviceDefinition) {		
		serviceStorage.put(serviceID, serviceDefinition);
	}	

	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteService(@PathVariable long id) {
		serviceStorage.remove( id );
	}

}
