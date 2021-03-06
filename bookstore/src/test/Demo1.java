package test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

import org.junit.Test;

public class Demo1 {

	@Test
	public void fun2() {
		System.out.println(2.0 - 1.1);// 0.8999999999999999
	}

	/**
	 * 1000鐨勯樁涔�
	 */
	@Test
	public void fun3() {
		BigInteger sum = BigInteger.valueOf(1);
		for (int i = 1; i <= 1000; i++) {
			BigInteger bi = BigInteger.valueOf(i);
			sum = sum.multiply(bi);
		}
		System.out.println(sum);
	}

	/**
	 * BigDecimal 鍙互澶勭悊浜岃繘琛岃繍绠楀鑷寸殑璇樊
	 */
	@Test
	public void fun4() {
		/*
		 * 1. 鍒涘缓BigDecimal瀵硅薄鏃讹紝蹇呴』浣跨敤String鏋勯�鍣紒
		 */
		BigDecimal d1 = new BigDecimal("2.0");
		BigDecimal d2 = new BigDecimal("1.1");
		BigDecimal d3 = d1.subtract(d2);

		System.out.println(d3);
	}

	/**
https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10001126856&p2_Order=123456&p3_Amt=10&p4_Cur=CNY&p5_Pid=&p6_Pcat=&p7_Pdesc=&p8_Url=http://localhost:8080/bookstore/OrderServlet?method=back&p9_SAF=&pa_MP=&pd_FrpId=ICBC-NET-B2C&pr_NeedResponse=1&hmac=7d8bf573417839e36a151bec660d50c4
	 */
	@Test
	public void fun5() {
		String hmac = PaymentUtil.buildHmac("Buy", "10001126856", "123456", "0.01", "CNY",
				"", "", "", "http://localhost:8080/bookstore/OrderServlet?method=back", 
				"", "", "ICBC-NET-B2C", "1", "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		System.out.println(hmac);
	}
}
