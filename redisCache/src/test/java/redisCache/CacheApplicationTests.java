package redisCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redisCache.user.User;
import redisCache.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void findUserTest() {
        for (int i = 0; i < 3; i++) {
            System.out.println("第" + i + "次");
            final User user = this.userService.findUser();
            System.out.println(user);
        }
    }
}
