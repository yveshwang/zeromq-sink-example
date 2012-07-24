package sink;

import org.zeromq.ZMQ;

public class WorkerRepo {
	public static void main(String[] args) throws Exception {
		//PurgeServer gets purges from Sink and publishes it out to the appropriate group
		
		//  Prepare our context and publisher
		ZMQ.Context context = ZMQ.context(2); //# of IO threads
	
		ZMQ.Socket publisher = context.socket(ZMQ.PUB);
		publisher.bind("tcp://*:5559");
		publisher.bind("ipc://purge");
		
		ZMQ.Socket receiver = context.socket(ZMQ.PULL);
		receiver.bind("tcp://*:5557");
		
		System.out.println("waiting for work instruction from Sink");
		byte[] bytes = receiver.recv(0);
		System.out.println("work received. ");
		System.out.println(ZeromqUtil.ZMQByteToString(bytes));
		System.out.println("sending to subscribers.");
		
		String purgemsg = ZeromqUtil.getMessagePrefixString()+":"+ZeromqUtil.ZMQByteToString(bytes);
		//might not get the first message
		publisher.send(ZeromqUtil.stringToZMQByte(purgemsg), ZMQ.NOBLOCK);
		System.out.println("sent.");
		
		System.out.println("Press Enter to kill WorkerRepo.");
		System.in.read();		//finish up
		
		receiver.close();
		publisher.close();
		context.term();
		Thread.sleep(1000); // Give 0MQ time to deliver
		
		
	}
}
