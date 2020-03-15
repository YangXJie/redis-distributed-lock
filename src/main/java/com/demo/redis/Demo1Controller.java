package com.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式锁
 * <p>
 * 死锁问题：
 * 1、业务代码异常，锁未释放----->使用finally释放锁
 * 2、拿到锁后，服务宕机，锁未释放-------->设置锁超时时间
 * 3、设置超时时间时宕机，锁未释放--------->设置超时时间和加锁操作为原子操作
 * <p/>
 * <p>
 * 其他问题:
 * 锁超时，代码体执行时间大于超时时间，错误释放锁----->方法1：加大超时时间（宕机时等待时间会过长）
 *                                             方法2：加锁后开启定时任务，判断是否还在执行，若在执行则重新设置超时时间。
 * 锁超时的时候，锁由别的用户错误释放，导致分布式锁失效----->删除锁前判断删除锁人是否为对应加锁人
 * <p/>
 */
@RestController
public class Demo1Controller {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deduct-stock")
    public String deductStock() {

        String lock = "LOCK";
        String clientId = UUID.randomUUID().toString();

        Boolean result = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(lock, clientId, 60, TimeUnit.SECONDS);//setnx(key,value)

        if (result != null && !result) {
            return "error";
        }
        try {
            //todo 业务主体
            System.out.println("执行..............");
        } finally {
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lock))) {
                stringRedisTemplate.delete(lock);//执行结束或者业务代码异常时释放锁
            }
        }
        return "suc";
    }
}
