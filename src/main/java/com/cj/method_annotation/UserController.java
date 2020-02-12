package com.cj.method_annotation;

import com.cj.entities.User;

/**
 * 使用自定义注解 @Cache 对查询结果进行缓存
 */
public class UserController {

    private User[] users = new User[]{
            new User("张三")
            ,new User("李四")
            ,new User("王五")
    };

    @Cache(key = "user")
    public User getUserById(Integer id){
        System.out.println("根据id进行查询");
        return users[id];
    }

}
