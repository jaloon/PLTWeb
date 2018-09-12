package com.tipray.core.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.tipray.constant.SystemPropertyConst;

public interface Rc4Dll extends Library {
	Rc4Dll instanceDll = (Rc4Dll) Native.loadLibrary(SystemPropertyConst.RC4_DLL_PATH, Rc4Dll.class);

	void RC4(byte[] data, int dataLen, byte[] key, int keyLen);
}