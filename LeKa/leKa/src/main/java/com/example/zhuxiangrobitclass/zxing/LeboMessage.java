package com.example.zhuxiangrobitclass.zxing;

import java.util.ArrayList;
import java.util.List;

public class LeboMessage {

	public static final byte FRAME_HEAD_1 = (byte) 0xFE;
	public static final byte FRAME_HEAD_2 = (byte) 0x68;
	public static final byte FRAME_TAIL = (byte) 0x16;
	public static final int EXTRA_LENGTH = 6;// 2+1+1+1+1;

	private static String hexString = "0123456789ABCDEF";

	public static byte[] getProgramMessage(int fCode, String sequence) {
		int length = sequence.length() / 2;
		int totalLength = length + EXTRA_LENGTH;
		byte[] message = new byte[totalLength];
		message[0] = FRAME_HEAD_1;
		message[1] = FRAME_HEAD_2;
		message[2] = (byte) fCode;
		message[3] = (byte) length;
		char[] hexs = sequence.toUpperCase().toCharArray();
		int n;
		for (int i = 4; i < totalLength - 2; i++) {
			n = (hexString.indexOf(hexs[2 * (i - 4)]) << 4);
			n += (hexString.indexOf(hexs[2 * (i - 4) + 1]));
			message[i] = (byte) (n & 0xff);
		}
		message[totalLength - 2] = (byte) checkSum(message, 0, totalLength - 2);
		message[totalLength - 1] = FRAME_TAIL;
		return message;
	}

	public static byte[] getProgramMessage(int fCode, List<String> sequence) {
		StringBuffer sb = new StringBuffer();
		for (String value : sequence) {
			sb.append(value);
		}
		return getProgramMessage(fCode, sb.toString());
	}

	private static int checkSum(byte[] data, int offset, int length) {
		int result = 0;
		for (int i = offset; i < length + offset; i++) {
			result += data[i];
		}
		return result % 256;
	}

	public static void main(String[] args) {
		ArrayList a = new ArrayList();
		a.add("0a03");
		a.add("0120");
		byte[] ttt = getProgramMessage(1,a);
		System.out.println("");
	}

}
