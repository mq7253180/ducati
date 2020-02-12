package com.quincy.core.test.net;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class IOAbstract {
	protected boolean ssl;
	protected static String SSL_VERSION_SSL_3 = "SSLv3";
	protected static String SSL_VERSION_TLS_1 = "TLSv1";
	protected static String SSL_KEYSTORE_PATH = "E:/java/keystores/quincy.keystore";
	protected static String SSL_KEYSTORE_PWD = "1qazxsw2";

	protected String getExceptioinStackTrace(Throwable e) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer msg = new StringBuffer("***********");
        msg.append(df.format(new Date()));
        msg.append("***********\n");
        msg.append(e.toString());
        StackTraceElement[] elements = e.getStackTrace();
        for(int j=0;j<elements.length;j++) {
            msg.append("\n\tat ");
            msg.append(elements[j].toString());
        }
        msg.append("\n==========================");
        return msg.toString();
    }

}
