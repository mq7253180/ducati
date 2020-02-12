package com.quincy.core.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class AAA {
	public void test() throws Exception {
		ClassLoader classLoader = new ClassLoader() {
			protected Class findClass(String name) throws ClassNotFoundException {
				System.out.println("---"+name);
				InputStream in = null;
				try {
					in = new FileInputStream("E:/gitlib/quincy/quincy-core/target/test-classes/com/quincy/core/test/BBB.class");
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					Class theClass = defineClass(name, bytes, 0, bytes.length);
					return theClass;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} finally {
					if(in!=null)
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		};
		Class clazz = classLoader.loadClass("com.quincy.core.test.BBB");
		Method method = clazz.getMethod("test");
		method.invoke(clazz.newInstance());
	}

	public static void main(String[] args) throws Exception {
		new AAA().test();
	}
}
