package com.nd.bamborest.demo;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
    @RequestMapping(value = "/greeting/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object get(@PathVariable("name") String name) {
       
    	ReturnMessage message = new ReturnMessage();
	        
    	message.setName("greeting!"+name);
    	message.setWebinfo("Tomcat path:"+System.getProperty("catalina.home"));
    	message.setDbinfo("SystemTime:"+PersistService.initDataSource());
    	message.setMessage("");
        return message;
    }
}
