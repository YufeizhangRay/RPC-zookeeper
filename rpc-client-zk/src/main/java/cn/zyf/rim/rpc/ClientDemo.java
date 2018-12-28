package cn.zyf.rim.rpc;

import cn.zyf.rim.rpc.zk.IServiceDiscovery;
import cn.zyf.rim.rpc.zk.ServiceDiscoveryImpl;
import cn.zyf.rim.rpc.zk.ZkConfig;

public class ClientDemo {

	public static void main(String[] args) throws InterruptedException {
        IServiceDiscovery serviceDiscovery=new ServiceDiscoveryImpl(ZkConfig.CONNNECTION_STR);

        RpcClientProxy rpcClientProxy=new RpcClientProxy(serviceDiscovery);

        for(int i=0;i<10;i++) {
            IHello hello = rpcClientProxy.clientProxy(IHello.class, null);
            System.out.println(hello.sayHello("Ray"));
            Thread.sleep(1000);
        }
    }


}
