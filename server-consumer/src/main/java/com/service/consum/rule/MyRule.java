package com.service.consum.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *@Description
 *@Author lixingjian
 *@DATE 2020/11/19 16:34
 *@Version 1.0
 *自定义Ribbon策略，伪随机策略
 **/
public class MyRule extends AbstractLoadBalancerRule {

    Random random;
    private int nowIndex = -1;//当前下标是谁，默认是-1（没有）
    private int lastIndex = -1;//上一次下标是谁，默认-1（没有）
    private int skipIndex = -1;//跳过的下标，上一次和这一次，如果都是这个下标，那么下一次就要跳过这个下标

    public MyRule() {
        random = new Random();
    }

    /**
     *规则1：当一个微服务下表连续被调用俩次，结果第三次如果还是他，那么就再随机一次
     */
    @SuppressWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while(server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();
                List<Server> allList = lb.getAllServers();
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }

                int index = random.nextInt(serverCount);
                System.out.println("当前下标" + index);
                if(index == skipIndex){
                    System.out.println("跳过");
                    index = random.nextInt(serverCount);
                    lastIndex = -1;
                    System.out.println("重新随机的下标" + index);
                }
                //俩次相同，重新随机之后，skip不需要跳过
                skipIndex = -1;
                nowIndex = index;
                if(nowIndex == lastIndex){
                    System.out.println("需要跳过的下标：" + nowIndex);
                    skipIndex = nowIndex;
                }
                server = (Server)upList.get(index);

                lastIndex = nowIndex;
                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
        }
    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
