package sink;

import org.zeromq.ZMQ;

public class Sink {
	public static void main(String[] args) throws Exception {
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket receiver = context.socket(ZMQ.PULL);
		receiver.bind("tcp://*:5558");
		
		// Socket to push message to PurgserServer
		ZMQ.Socket sender = context.socket(ZMQ.PUSH);
		sender.connect("tcp://localhost:5557");
		
		System.out.println("Press Enter to send work to repo.");
		System.in.read();		//finish up
		
		System.out.println("sending to server....");
		sender.send( ZeromqUtil.stringToZMQByte("purge this motherfucker!"), ZMQ.NOBLOCK);
		System.out.println("work sent.");
		System.out.println("now waiting for reply from workers.");
		byte[] bytes = receiver.recv(0); //0 is blocking
		System.out.println("ack received from Worker.");
		System.out.println(ZeromqUtil.ZMQByteToString(bytes));
		System.out.println("Press Enter to kill Sink.");
		System.in.read();		//finish up
		
		receiver.close();
		sender.close();
		context.term();
		Thread.sleep(1000); // Give 0MQ time to deliver
	}
}
