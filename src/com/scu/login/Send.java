package com.scu.login;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ��װ���ӿͻ��˶�ȡ��Ϣ��Ȼ����Ϣ���͵�������
 * 1��ʹ�ö��̣߳����ڶ���ͻ���ͬʱ����������Ӳ�������Ϣ
 * @author zhuzhengbin
 *
 */
public class Send implements Runnable {
	// ������TCP����
	private Socket client = null;
	private BufferedOutputStream bos = null;
	
	public Send() {
	}
	
	public Send(Socket client) {
		this.client = client;
		try {
			bos = new BufferedOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
	}
	
	
	
}
