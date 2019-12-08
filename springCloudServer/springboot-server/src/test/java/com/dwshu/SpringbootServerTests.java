package com.dwshu;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwshu.dao.EsUserRepository;
import com.dwshu.pojo.EsUser;
import com.dwshu.pojo.User;
import com.dwshu.service.UserServer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootServerApplication.class)
public class SpringbootServerTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserServer userServer;

    @Autowired
    private EsUserRepository esUserRepository;

    //测试创建索引库
    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(EsUser.class);
    }

    //往索引库里添加数据
    @Test
    public void createType() {
        List<User> users = userServer.findAllByOrderById();
        try {
            for (User user : users) {
                EsUser esUser = new EsUser(user);
                esUserRepository.save(esUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Autowired
    private RedisTemplate redisTemplate;

    //获取redis数据库里的数据
    @Test
    public void getRedisData() {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userServer.findAllByOrderById();

        try {
            if (userList != null && !userList.isEmpty()) {
                for (User user : userList) {
                    Object userInfo = redisTemplate.opsForValue().get(String.valueOf(user.getId()));

                    //会存在map里value值为null的元素，取不到
                    //map=JSONObject.parseObject(JSONObject.toJSONString(userInfo),Map.class);

                    map = JSONObject.parseObject(JSONObject.toJSONString(userInfo, SerializerFeature.WriteMapNullValue), Map.class);

                    log.info("用户信息:id=" + map.get("id") + " ,username=" + map.get("username") +
                            " ,password=" + map.get("password") + " ,telephone=" + map.get("telephone") +
                            " ,state=" + map.get("state"));
                }
            }

        } catch (Exception e) {
            log.info("程序出错了,错误信息：" + e.getMessage());
        }


    }


}