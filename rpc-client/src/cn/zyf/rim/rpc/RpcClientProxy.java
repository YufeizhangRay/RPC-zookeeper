package cn.zyf.rim.rpc;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

	@SuppressWarnings("unchecked")
	public <T> T clientProxy(final Class<T> interfaceCls, final String host, final int port) {
		// 使用到了动态代理。
		return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[] { interfaceCls },
				new RemoteInvocationHandler(host, port));
	}

}
