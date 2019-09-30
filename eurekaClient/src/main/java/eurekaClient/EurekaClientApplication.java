
package eurekaClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: wangsi
 * @createTime: 2019年9月10日 上午10:43:52
 * @version: v1.0
 */

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
   
}
