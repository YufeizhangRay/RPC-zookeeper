package cn.zyf.rim.rpc;

public class ClientDemo {

	public static void main(String[] args) {
        RpcClientProxy rpcClientProxy=new RpcClientProxy();

        IHello hello=rpcClientProxy.clientProxy
                (IHello.class,"localhost",8888);
        System.out.println(hello.sayHello("Yufei Zhang"));
    }

}
