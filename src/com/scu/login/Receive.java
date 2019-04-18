package com.scu.login;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ��װ����ӷ��������շ�����Ϣ��Ȼ����пͻ��˲��ֵĴ�����ӡ��ʾ�ȣ�
 * @author zhuzhengbin
 *
 */
public class Receive implements Runnable{
	private Socket client;	// һ���ͻ������������TCP����
	private DataInputStream dis;
	
	public Receive() {
	}
	
	public Receive(Socket client) {
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String msg = dis.readUTF();
			System.out.println(msg);
//			close(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void close(Closeable... targets) {
		for(Closeable target:targets) {
			if(target != null) {
				try {
					target.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
