package cn.zyf.rim.rpc.zk;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import cn.zyf.rim.rpc.zk.loadbalance.LoadBalance;
import cn.zyf.rim.rpc.zk.loadbalance.RandomLoadBalance;

public class ServiceDiscoveryImpl implements IServiceDiscovery {

	List<String> repos=new ArrayList<>();

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address) {

        curatorFramework=CuratorFrameworkFactory.builder().
                connectString(address).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,
                        10)).build();
        curatorFramework.start();
    }

    @Override
    public String discover(String serviceName) {
        String path=ZkConfig.ZK_REGISTER_PATH+"/"+serviceName;
        try {
            repos=curatorFramework.getChildren().forPath(path);

        } catch (Exception e) {
            throw new RuntimeException("获取子节点异常："+e);
        }
        //动态发现服务节点的变化
        registerWatcher(path);

        //负载均衡机制
        LoadBalance loadBanalce=new RandomLoadBalance();

        return loadBanalce.selectHost(repos); //返回调用的服务地址
    }

    private void registerWatcher(final String path){
        PathChildrenCache childrenCache=new PathChildrenCache
                (curatorFramework,path,true);

        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos=curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.close();
        } catch (Exception e) {
           throw new RuntimeException("注册PatchChild Watcher 异常"+e);
        }
    }
}
