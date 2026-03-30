package org.example.week02;

import org.junit.jupiter.api.Test;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Week02ApplicationTests {

    @Test
    void contextLoads() {
        // 测试生成token
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",2);
        claims.put("number","3125004118");

        String token = JwtUtil.createToken(claims);
        System.out.println(token);


    }
    @Test
    void testAp(){
        ApplicationHome ap=new ApplicationHome(this.getClass());
        String path_name =ap.getDir().getParentFile().getParentFile()
                .getAbsolutePath();
        System.out.println(path_name);
    }
    

}
