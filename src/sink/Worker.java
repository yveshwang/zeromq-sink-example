package sink;

import org.zeromq.ZMQ;

public class Worker {
	public static void main(String[] args) throws Exception {
		//repo gets work instructions from Sink and publishes it out to the appropriate group
		
		//  Prepare our context and publisher
		ZMQ.Context context = ZMQ.context(1); //# of IO threads
	
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect("tcp://localhost:5559");
		
		ZMQ.Socket ack = context.socket(ZMQ.PUSH);
		ack.connect("tcp://localhost:5558");
		
		//subscribe to a specific prefix.
		subscriber.subscribe(ZeromqUtil.getMessagePrefixBytes());
		
		System.out.println("waiting for instruction from WorkerRepo");
		byte[] bytes = subscriber.recv(0);
		System.out.println("work received from repo. Now doing the hard labour...");
		System.out.println(ZeromqUtil.ZMQByteToString(bytes));
		Thread.sleep(100); //sleep for 100 ms.
		System.out.println("DONE! sending ACK to Sink.");
		ack.send(ZeromqUtil.stringToZMQByte("SCV good to go. Yessssir!"), ZMQ.NOBLOCK);
		System.out.println("sent.");
		
		System.out.println("Press Enter to kill Worker.");
		System.in.read();		//finish up
		
		ack.close();
		subscriber.close();
		context.term();
		Thread.sleep(1000); // Give 0MQ time to deliver
		
	}
}
