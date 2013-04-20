package net.tobyjackson.hermes.service;

import net.tobyjackson.hermes.HermesService;

/**
 * Trivial implementation of the service which
 * simply uses Strings as requests/responses.
 * 
 * Simple one-to-one relationship, doesn't do ANYTHING clever.
 * 
 * @author Toby Jackson <toby@tobyjackson.net>
 * @date Apr 20, 2013
 *
 */
public class SimpleStringService implements HermesService<String, String> {
	String message;
	public String process(String request){ return message;}
	public void setResponse(String message){ this.message = message; }

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
