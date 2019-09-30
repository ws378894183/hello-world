
package configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @author: wangsi
 * @createTime: 2019年9月10日 上午10:26:08
 * @version: v1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
