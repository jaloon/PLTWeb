package com.tipray.test;

import com.mchange.lang.ByteUtils;
import com.tipray.util.BytesUtil;
import com.tipray.util.CRCUtil;
import com.tipray.util.RC4Util;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBufferTest {
	@Test
	public void test() {
		ByteBuffer keyBuf = ByteBuffer.allocate(16);
		keyBuf.put("pltone.com/".getBytes(StandardCharsets.ISO_8859_1));
		// int i = 0x01020304; //16909060
		int i = 0x00000100;
		keyBuf.putInt(i);
		byte[] b1 = keyBuf.array();
		byte crc = CRCUtil.getCRC(b1,15);
		byte crc1 = CRCUtil.getCRC(Arrays.copyOf(b1, 15));
		System.out.println(crc == crc1);
	}

	@Test
	public void test1() {
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
		System.out.println(BytesUtil.bytesToHex(RC4Util.createBinaryKey(), true));
	}
	
	@Test
	public void test2() {
		// 958176F764C495B44520D51FD5675731
		// 958176f764c495b44520d51fd5675731
		byte[] decrypt = ByteUtils.fromHexAscii("1e5a7a49863b490146f2a279e34a3b71");
		byte[] key = RC4Util.getKeyByDeviceId(16777217);
		System.out.println(BytesUtil.bytesToHex(key, true));
		String rc4Hex = RC4Util.rc4ToHexString(decrypt, key);
		System.out.println(rc4Hex);
		System.out.println(ByteUtils.toHexAscii((byte) 127));
		
		String data = "8efaa0457ce0bb489f69e7dec82c77910cf14e01b04d81e535a091f975de5fde04b5d1b53e12b2ed72223be6a50abc080a961e7570893bc73ec7da8c40075d6e728a9deb100ad1874b3c2994";
		byte[] dataBytes = ByteUtils.fromHexAscii(data);
		String str = "{\"rc4\":\"958176F764C495B44520D51FD5675731\",\"port\":12345,\"ip\":\"192.126.3.223\"}";
		dataBytes = str.getBytes();
		System.out.println(RC4Util.rc4ToHexString(dataBytes, key));
	}
	
	@Test
	public void test3() throws Exception {
		// 192.126.3.223, 12345, 958176F764C495B44520D51FD5675731
		// 192.126.3.223, 12345, 958176F764C495B44520D51FD5675731
//		byte[] decrypt = ByteUtils.fromHexAscii("8efaa0457ce0bb489f69e7dec82c57910cf16e01b04da1e535a091f955de5ffe24b5d1b53e12b2ed72223be6a50abc080a961e7570893bc73ec7da8c40075d6e728a9deb100ad1874b3c2994");
//		byte[] decrypt = ByteUtils.fromHexAscii("8efaa0457ce0bb489f69e7dec82c77910cf14e01b04d81e535a091f975de5fde04b5d1b53e12b2ed72223be6a50abc080a961e7570893bc73ec7da8c40075d6e728a9deb100ad1874b3c2994");
//		byte[] decrypt = ByteUtils.fromHexAscii("8efaa0457ce0bb489f69e7dec82c77910cf14e01b04d81e535a091f975de5fde04b5d1b53e12b2ed72223be6a50abc080a961e7570893bc73ec7da8c40075d6e728a9deb100ad1874b3c2994");
//		byte[] decrypt = ByteUtils.fromHexAscii("8efaa0457ce0bb489f69e7dec82c77910cf14e01b04d81e535a091f975de5fde04b5d1b53e12b2ed72223be0af0aea1012971d64689e678a25c3da8c5304576869978db44e1bc597483639c7fd20dad584f23b0ae5b4db");
//		byte[] decrypt = ByteUtils.fromHexAscii("c7eb99c380e878f94f477566bf0dad25ba");
//		byte[] decrypt = ByteUtils.fromHexAscii("76b3ebf8cb94fe260d9206d212db9d47e09ee2a1aaa07188739f58a8e08e45f64e345daa0e36109db78841f539874d2a3166ef46558fe67570d500c53b7c70019bd5c3ad15dc626022959319671a7a2e6b8431f1878e");
//		byte[] decrypt = ByteUtils.fromHexAscii("029960037343da09f46659d997375e354ba65853f5d92c7710764d7e47e558e31ff7e718cc7dcd8e3a869aec1b3743197097dc0fb08b7accc3d9259b7eb64825148bb80da0ff6886a92013c4a816e37647a3841030cb");
//		byte[] decrypt = ByteUtils.fromHexAscii("029960037343da09f46659d997375e354ba65853f5d92c7710764d7e47e558e31ff7e718cc7dcd8e3a869aec1b3743197097dc0fb08b6cd7c18f3dda6fdf3f417185a056e1f170ecd74a75c8a302ea6142a387162a988983a19b6a4c6c25a1216b8d7480af7b61ae77ce5cffacea5111a225dfdf9857e7947526b1900e3fb70db44a7a0865cdd4194e4721ee0882bf58b60a40bd40c8fcebe8dfa893e5d63c581b97b66bcf");
//		byte[] decrypt = ByteUtils.fromHexAscii("02997b10655bc21af4614fd99237363153a02954eec04c334b310b6c39e15b966ff7fd0d8b29ca9d228881af467456151490db19df9d3396f39933947fbf342614e1de51e6ea6793ab2803c6bb56be2a52b7870c30d0caddb1916a063a31f27c66d632cfea370e9c56f117abceba0c4ee57acbcb9410bec26726a1c0482f");
		byte[] decrypt = ByteUtils.fromHexAscii("8efabb566af8a35b976bf1ddca3400900deb3f00bd5aeff371fad1bd13d15da870b6cba07946b5fe6a2c20a3f249ff1c76901a7207882ed01583cc83500620696dfdebe8080eca824a3e29c5ee64898995e63814f4f0d20c3cb87554ad398a7801c203b9331e153dd7bdce68c1a0588d83e2de9c1101935d8a21c61fa6c6d8af08ba40c8");
//		byte[] decrypt = ByteUtils.fromHexAscii("c7eb99c380e878f94f477566bf0dad25ba");
//		byte[] decrypt = ByteUtils.fromHexAscii("8efabb566af8a35b976bf1ddca3400900deb3f00bd5aeff371fad1bd13d15da870b6cba07946b5fe6a2c20a3f249ff1c76901a7207882ed01583cc83500620696dfdebe8080eca824a3e29c5ee64898995e63814f4f0d20c3cb87554ad398a7801c203b9331e153dd7bdce68c1a0588d83e2de9c1101935d8a21c61fa6c6d8af08ba40c8");
//		byte[] key = RC4Util.getKeyByDeviceId(16777217);
		byte[] key = RC4Util.getKeyByDeviceId(16777217);
		byte[] data = RC4Util.rc4(decrypt, key);
		System.out.println(new String(data, "utf-8"));
		System.out.println(ByteUtils.toHexAscii(data));
//		System.out.println((byte) 128);
//		System.out.println(ByteUtils.toUnsigned((byte) 128));
//		System.out.println(ByteUtils.toHexAscii((byte) 128));
//		System.out.println(ByteUtils.toHexAscii((byte) -128));
	}
}
