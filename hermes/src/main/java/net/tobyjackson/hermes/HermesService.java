package net.tobyjackson.hermes;

/**
 * Represents a simple Service
 *
 * @author Toby Jackson <toby@tobyjackson.net>
 * @date Apr 20, 2013
 *
 * @param <Request> 
 * @param <Response>
 */
public interface HermesService<Request, Response> {

	
	public Response process(Request request);
	
	public void setResponse(Response message);
	
}
