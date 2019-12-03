package redisCache.user.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import redisCache.user.User;
import redisCache.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @Cacheable(value = {"valueName", "valueName2" }, key = "'keyName1'")
    public User findUser() {
        System.out.println("执行方法...");
        return new User("id1", "张三", "深圳", "1234567", 18);
    }
}
