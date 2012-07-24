package sink;

public class ZeromqUtil {
	
	public static byte[] stringToZMQByte(String s) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(s);
		buffer.append(" ");
		byte[] bytes = buffer.toString().getBytes();
		//set the last byte to 0 to deliminate the packet
		bytes[bytes.length -1] = 0;
		return bytes;
	}
	
	public static String ZMQByteToString(byte[] bytes) {
		return new String(bytes,0,bytes.length-1);
	}
	
	public static byte[] zeroZMQByte() {
		return "0\u0000".getBytes();
	}
	
	public static String getMessagePrefixString() {
		return "varnish_internal";
	}
	public static byte[] getMessagePrefixBytes() {
		return getMessagePrefixString().getBytes();
	}
}
