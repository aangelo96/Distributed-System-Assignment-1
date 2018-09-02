package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

public class ServeClient implements Runnable {

	private Socket client;
	private int num;
	
	public ServeClient(Socket client, int i) {
		this.client = client;
		this.num = i;
	}
	
	
	public void run() {
		//Get the input/output streams for reading/writing data from/to the socket
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			
			//Read the message from the client and reply
			//Notice that no other connection can be accepted and processed until the last line of 
			//code of this loop is executed, incoming connections have to wait until the current
			//one is processed unless...we use threads!
				
			String clientMsg = null;
			try {
				while((clientMsg = in.readLine()) != null) {
					System.out.println("Message from client " + num + ": " + clientMsg);
					out.write("Server Ack " + clientMsg + "\n");
					out.flush();
					System.out.println("Response sent");
				}}
			catch(SocketException e) {
				System.out.println("closed...");
			}
				
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	}
}
