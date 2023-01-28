package mina1316.me.client.config.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

// function that receives a class and handler for proxy and returns a proxy object
fun <T> createProxy(clazz: Class<T>, handler: InvocationHandler): T {
    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), handler) as T
}