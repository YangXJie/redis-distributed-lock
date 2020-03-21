# redis-distributed-lock

使用redis实现分布式锁
1、自己实现
仍然存在的问题：通过加大锁超时时间（60s）来预防代码执行时锁超时，这会导致此情况触发时服务不可用时间过长。

2、使用redisson
