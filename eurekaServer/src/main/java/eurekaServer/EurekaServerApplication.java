package eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @author: wangsi
 * @createTime: 2019年9月10日 上午9:54:15
 * @version: v1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
