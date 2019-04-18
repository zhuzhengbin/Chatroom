package com.scu.login;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private BufferedReader console;
	private DataOutputStream dos;
	private boolean isRunning;


	public Send() {	// �չ�����
	}

	public Send(Socket client) {
		this.client = client;
		this.isRunning = true;
		try {
			console = new BufferedReader(new InputStreamReader(System.in));
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while(isRunning) {
			try {
				System.out.println("�������˺�:");
				String id = console.readLine();
				System.out.println("����������:");
				String pwd = console.readLine();
				String msg = id+"&"+pwd;
				dos.writeUTF(msg);
				dos.flush();
				//			close(client);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
