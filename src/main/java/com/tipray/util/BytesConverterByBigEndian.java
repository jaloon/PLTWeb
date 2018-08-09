package com.tipray.util;

import java.nio.ByteOrder;

/**
 * 字节数组byte[]和整型,浮点型数据的转换——Java代码
 * 
 * <p>
 * Big-endian：将高序字节存储在起始地址（高位编址）
 * <p>
 * 本文中byte[]的顺序按照“大端顺序”，这句话的意思是说对于整数0x11223344 <br>
 * byte[0]保存0x11，byte[1]保存0x22，byte[2]保存0x33，byte[3]保存0x44
 * 
 * @author chenlong
 *
 */
public class BytesConverterByBigEndian {
	/**
	 * char转换为byte[2]数组
	 * 
	 * @param c
	 *            字符
	 * @return byte[2]数组
	 */
	public static byte[] getByteArray(char c) {
		byte[] b = new byte[2];
		b[0] = (byte) ((c & 0xff00) >> 8);
		b[1] = (byte) (c & 0x00ff);
		return b;
	}

	/**
	 * 从byte数组的index处的连续两个字节获得一个char
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 字符
	 */
	public static char getChar(byte[] arr, int index) {
		return (char) (0xff00 & arr[index] << 8 | (0xff & arr[index + 1]));
	}

	/**
	 * short转换为byte[2]数组
	 * 
	 * @param s
	 *            短整型数
	 * @return byte[2]数组
	 */
	public static byte[] getByteArray(short s) {
		byte[] b = new byte[2];
		b[0] = (byte) ((s & 0xff00) >> 8);
		b[1] = (byte) (s & 0x00ff);
		return b;
	}

	/**
	 * 从byte数组的index处的连续两个字节获得一个short
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 短整型数
	 */
	public static short getShort(byte[] arr, int index) {
		return (short) (0xff00 & arr[index] << 8 | (0xff & arr[index + 1]));
	}

	/**
	 * int转换为byte[4]数组
	 * 
	 * @param i
	 *            整形数
	 * @return byte[4]数组
	 */
	public static byte[] getByteArray(int i) {
		byte[] b = new byte[4];
		b[0] = (byte) ((i & 0xff000000) >> 24);
		b[1] = (byte) ((i & 0x00ff0000) >> 16);
		b[2] = (byte) ((i & 0x0000ff00) >> 8);
		b[3] = (byte) (i & 0x000000ff);
		return b;
	}

	/**
	 * 从byte数组的index处的连续4个字节获得一个int
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 整形数
	 */
	public static int getInt(byte[] arr, int index) {
		return (0xff000000 & (arr[index + 0] << 24)) | (0x00ff0000 & (arr[index + 1] << 16))
				| (0x0000ff00 & (arr[index + 2] << 8)) | (0x000000ff & arr[index + 3]);
	}

	/**
	 * float转换为byte[4]数组
	 * 
	 * @param 浮点数
	 * @return byte[4]数组
	 */
	public static byte[] getByteArray(float f) {
		// 将float里面的二进制串解释为int整数
		int intbits = Float.floatToIntBits(f);
		return getByteArray(intbits);
	}

	/**
	 * 从byte数组的index处的连续4个字节获得一个float
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 浮点数
	 */
	public static float getFloat(byte[] arr, int index) {
		return Float.intBitsToFloat(getInt(arr, index));
	}

	/**
	 * long转换为byte[8]数组
	 * 
	 * @param l
	 *            长整型数
	 * @return byte[8]数组
	 */
	public static byte[] getByteArray(long l) {
		byte b[] = new byte[8];
		b[0] = (byte) (0xff & (l >> 56));
		b[1] = (byte) (0xff & (l >> 48));
		b[2] = (byte) (0xff & (l >> 40));
		b[3] = (byte) (0xff & (l >> 32));
		b[4] = (byte) (0xff & (l >> 24));
		b[5] = (byte) (0xff & (l >> 16));
		b[6] = (byte) (0xff & (l >> 8));
		b[7] = (byte) (0xff & l);
		return b;
	}

	/**
	 * 从byte数组的index处的连续8个字节获得一个long
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 长整型数
	 */
	public static long getLong(byte[] arr, int index) {
		return (0xff00000000000000L & ((long) arr[index + 0] << 56))
				| (0x00ff000000000000L & ((long) arr[index + 1] << 48))
				| (0x0000ff0000000000L & ((long) arr[index + 2] << 40))
				| (0x000000ff00000000L & ((long) arr[index + 3] << 32))
				| (0x00000000ff000000L & ((long) arr[index + 4] << 24))
				| (0x0000000000ff0000L & ((long) arr[index + 5] << 16))
				| (0x000000000000ff00L & ((long) arr[index + 6] << 8)) | (0x00000000000000ffL & (long) arr[index + 7]);
	}

	/**
	 * double转换为byte[8]数组
	 * 
	 * @param d
	 *            双精度数
	 * @return byte[8]数组
	 */
	public static byte[] getByteArray(double d) {
		long longbits = Double.doubleToLongBits(d);
		return getByteArray(longbits);
	}

	/**
	 * 从byte数组的index处的连续8个字节获得一个double
	 * 
	 * @param arr
	 *            byte数组
	 * @param index
	 *            数组下标索引
	 * @return 双精度数
	 */
	public static double getDouble(byte[] arr, int index) {
		return Double.longBitsToDouble(getLong(arr, index));
	}

	public static void main(String[] args) {
		System.out.println(ByteOrder.nativeOrder());
		if (args.length < 1) {
			System.out.println("enter 'char' test method about char");
			System.out.println("enter 'short' test method about short");
			System.out.println("enter 'int' test method about int");
			System.out.println("enter 'float' test method about float");
			System.out.println("enter 'long' test method about long");
			System.out.println("enter 'double' test method about double");
			return;
		}
		if ("char".equals(args[0])) {
			char c = '\u0000';
			while (c < '\uffff') {
				System.out.println(getChar(getByteArray(c), 0));
				c++;
			}
		} else if ("short".equals(args[0])) {
			short s = Short.MIN_VALUE;
			while (s < Short.MAX_VALUE) {
				System.out.println(getShort(getByteArray(s), 0));
				s++;
			}
		} else if ("int".equals(args[0])) {
			int i = Integer.MIN_VALUE;
			while (i < Integer.MAX_VALUE) {
				System.out.println(getInt(getByteArray(i), 0));
				i++;
			}
		} else if ("float".equals(args[0])) {
			float f = Float.MIN_VALUE;
			while (f < Float.MAX_VALUE) {
				System.out.println(getFloat(getByteArray(f), 0));
				f += 1.1111f;
			}
		} else if ("long".equals(args[0])) {
			long l = Long.MIN_VALUE;
			while (l < Long.MAX_VALUE) {
				System.out.println(getLong(getByteArray(l), 0));
				l++;
			}
		} else if ("double".equals(args[0])) {
			double d = Double.MIN_VALUE;
			while (d < Double.MAX_VALUE) {
				System.out.println(getDouble(getByteArray(d), 0));
				d += 1.111D;
			}
		}
	}
}
