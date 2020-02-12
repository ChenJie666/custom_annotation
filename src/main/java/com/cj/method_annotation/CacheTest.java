package com.cj.method_annotation;

        import org.junit.Test;

public class CacheTest {

    @Test
    public void cacheTest(){
        UserController userController = new UserController();

        Object user = CacheUtil.invokeMethod(userController, "getUserById", 1);
        System.out.println(user);

        Object user1 = CacheUtil.invokeMethod(userController, "getUserById", 1);
        System.out.println(user1);
    }

}
