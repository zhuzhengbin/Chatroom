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
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
//		client.close();
	}
}
