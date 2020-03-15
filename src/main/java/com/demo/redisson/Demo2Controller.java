package com.demo.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo2Controller {

    @Autowired
    private Redisson redisson;

    @RequestMapping("/deduct-stock")
    public String deductStock() {

        String lock = "LOCK";

        RLock rLock = redisson.getLock(lock);

        try {
            rLock.lock();
            //todo 业务主体
            System.out.println("执行..............");
        } finally {
            rLock.unlock();
        }
        return "suc";
    }
}
