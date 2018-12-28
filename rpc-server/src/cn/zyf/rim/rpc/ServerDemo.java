package cn.zyf.rim.rpc;

public class ServerDemo {
	public static void main(String[] args) {
		
        IHello iHello=new HelloImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.publisher(iHello,8888);
    }
}
