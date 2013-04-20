package net.tobyjackson.hermes;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static final List<HermesService> serviceStorage = new LinkedList<HermesService>();

	
	static{
		SimpleStringService s = new SimpleStringService();
		s.setResponse("MyFixedResponse");
		serviceStorage.add(s);
	}

	@RequestMapping(method = RequestMethod.GET) 
	public @ResponseBody List<HermesService> getConfiguredServices() { 		
		return serviceStorage;
	}

	@RequestMapping(value="{id}",method = RequestMethod.GET)	
	public @ResponseBody HermesService getService(@PathVariable("id") int serviceID) {		
		return serviceStorage.get(serviceID);
	}
	
//	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteService(@PathVariable int id) {
//		serviceStorage.remove( id );
//	}
//	
//	@RequestMapping(value="{service}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteService(@RequestBody final SimpleStringService service){
//		serviceStorage.remove( service );
//	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody 
	public void startService(@RequestBody final SimpleStringService service){

		Thread strap = new Thread(){
			public void run() {
				System.err.println( "Starting new service..." );
		
				// Configure the bootstrap.
				EventLoopGroup bossGroup = new NioEventLoopGroup();
				EventLoopGroup workerGroup = new NioEventLoopGroup();
				try {
					ServerBootstrap b = new ServerBootstrap();
					b.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)			
					 .childHandler(new ChannelInitializer<SocketChannel>() {
						 @Override
						 public void initChannel(SocketChannel channel) throws Exception {
							 channel.pipeline().addLast(service);
						 }
					 })		
					 .childOption(ChannelOption.AUTO_READ, true)
					 .bind(8081).sync().channel().closeFuture().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					bossGroup.shutdown();
					workerGroup.shutdown();
				}
			}
		};
		
		strap.start();
		serviceStorage.add(service);
	}
}
