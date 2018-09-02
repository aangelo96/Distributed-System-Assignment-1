package main;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.net.ServerSocketFactory;

public class Server {

	// Declare the port number
	public static int port = 3000;
	
	// Identifies the user number connected
	private static int counter = 0;
	
	public static void main(String[] args) {
		
		// ---Handle arguments error---
		
		// Handling Argument Length Issue
		if (args.length>2) {
			System.out.println("Argument too long !!!, Expected Argument Length = 2");
			System.exit(0);
		} else if (args.length<2) {
			System.out.println("Argument too short !!!, Expected Argument Length = 2");
			System.exit(0);
		}
		
		// Check if file is available
		File file = new File(args[1]);
		try {
			Scanner inputStream = new Scanner(file);	
			while(inputStream.hasNext()){
                // read single line, put in string
                String data = inputStream.next();
                System.out.println(data + "***");

            }
            // after loop, close scanner
            inputStream.close();
		} catch(FileNotFoundException e) {
			System.out.println("Could not found Dictionary File !!!");
			System.exit(0);
		}
		
		// Check if the port number is correct
		port = Integer.parseInt(args[0]);
		ServerSocket listeningSocket = null;
		Socket clientSocket = null;
		
		try {
			//Create a server socket listening on port 4444
			listeningSocket = new ServerSocket(port);
			int i = 0; //counter to keep track of the number of clients
			
			
			//Listen for incoming connections for ever 
			while (true) {
				System.out.println("Server listening on port "+port+" for a connection");
				//Accept an incoming client connection request 
				clientSocket = listeningSocket.accept(); //This method will block until a connection request is received
				i++;
				System.out.println("Client conection number " + i + " accepted:");
				System.out.println("Remote Port: " + clientSocket.getPort());
				System.out.println("Remote Hostname: " + clientSocket.getInetAddress().getHostName());
				System.out.println("Local Port: " + clientSocket.getLocalPort());
				
				// Start a new thread for a connection
				new Thread(new ServeClient(clientSocket,i)).start();
				//Thread t = new Thread(() -> serveClient(clientSocket,i));
				//t.start();
				
				
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(listeningSocket != null) {
				try {
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*private static void serveClient(Socket client, int i){
		//Get the input/output streams for reading/writing data from/to the socket
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));

//		InputStream in = clientSocket.getInputStream();
//		ObjectInputStream iStream = new ObjectInputStream(in);
		
//		DataPacket datapack = null;
//		try {
//			datapack = (DataPacket)iStream.readObject();
			
//		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		datapack.setMeaning("LOL");
		
		
		//Read the message from the client and reply
		//Notice that no other connection can be accepted and processed until the last line of 
		//code of this loop is executed, incoming connections have to wait until the current
		//one is processed unless...we use threads!
		String clientMsg = null;
		try {
		while((clientMsg = in.readLine()) != null) {
			System.out.println("Message from client " + i + ": " + clientMsg);
			out.write("Server Ack " + clientMsg + "\n");
			out.flush();
			System.out.println("Response sent");
		}}
		catch(SocketException e) {
			System.out.println("closed...");
			
		}
		client.close();
	}
		*/
}
