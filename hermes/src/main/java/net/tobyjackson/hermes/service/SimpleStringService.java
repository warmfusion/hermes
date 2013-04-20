package net.tobyjackson.hermes.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;
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
public class SimpleStringService extends ChannelInboundByteHandlerAdapter implements HermesService<String, String>  {
	String message;
	
	public SimpleStringService() {}
	
	public SimpleStringService(String message){
		setMessage(message);
	}
	
	public String process(String request){ return message;}
	public void setResponse(String message){ this.message = message; }

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * prints a stack trace and closes the connection
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	/**
	 * Ignores any incoming data, and simply outputs the value returned by {@link #getMessage()}
	 */
	@Override
	protected void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
	   ByteBuf  out = ctx.nextOutboundByteBuffer();
	   out.writeBytes(getMessage().getBytes());
	   ctx.flush();
	   ctx.close();
	}
}
