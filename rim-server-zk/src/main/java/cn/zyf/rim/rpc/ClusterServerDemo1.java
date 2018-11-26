package cn.zyf.rim.rpc;

import java.io.IOException;

import cn.zyf.rim.rpc.zk.IRegisterCenter;
import cn.zyf.rim.rpc.zk.RegisterCenterImpl;

public class ClusterServerDemo1 {

	public static void main(String[] args) throws IOException {
        IHello iHello=new HelloImpl1();
        IRegisterCenter registerCenter=new RegisterCenterImpl();
        RpcServer rpcServer=new RpcServer(registerCenter,"127.0.0.1:8080");
        rpcServer.bind(iHello);
        rpcServer.publisher();
        System.in.read();
    }

}
