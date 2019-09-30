package eurekaClient.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class UserController {
	@Value("${server.port}")
    private String port;
    
    @GetMapping("/client/port")
    public String getPort() {
    	return this.port;
    }
    
    @GetMapping("/client/login")
    public JSONObject login() {
    	JSONObject result = new JSONObject();
    	JSONObject data = new JSONObject();
    	data.put("token", this.port);
    	result.put("data", data);
    	return result;
    }
}
