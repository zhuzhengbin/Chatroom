package com.scu.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ��Ŀ��飺
 * ʹ�����ݿ⡢���̡߳�socket���֪ʶʵ���û���Զ�̵�½�������ڷ�����жϵ�½�Ƿ�ɹ�
 * 
 * Ŀ�꣺
 * 1��ʵ�ִӿͻ��������˺����룬�ڷ��������ж��Ƿ���ȷ�����ؽ��
 * @author zhuzhengbin
 *
 */
public class Client {
	public static void main(String[] args) throws Exception{
		Socket client = new Socket("localhost",9999);
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("�������˺�");
		String id = console.readLine();
		System.out.println("����������");
		String pwd = console.readLine();
		String msg = id+"&"+pwd;
		System.out.println(msg);
		
		
		client.close();
	}
}
