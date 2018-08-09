package com.tipray.core.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.tipray.constant.SystemPropertyConst;

public interface CrcDll extends Library {
	CrcDll instanceDll = (CrcDll) Native.loadLibrary(SystemPropertyConst.CRC_DLL_PATH, CrcDll.class);

	public byte GetCRC(byte[] data, int dataLen);
}
