package cn.zyf.rim.rpc;

import cn.zyf.rim.rpc.anno.RpcAnnotation;

@RpcAnnotation(IHello.class)
public class HelloImpl1 implements IHello {

	@Override
	public String sayHello(String msg) {
		return "I'm 8080 Node , "+msg;
	}

}
