package com.tipray.init;

/**
 * 初始化
 * @author chends
 *
 */
public abstract class Initialization {
	/**初始化新库*/
	public final static String MODE_CREATE = "create";
	/**更新已存在的数据库*/
	public final static String MODE_UPDATE = "update";
	/**
	 * 执行初始化一个新的数据库
	 * @throws Exception
	 */
	public abstract void init() throws Exception;
	/**
	 * 执行更新一个已存在的数据库
	 * @throws Exception
	 */
	public abstract void update() throws Exception;
	
	public void execute(String mode) throws Exception{
		if(MODE_CREATE.equals(mode)){
			init();
		}else if(MODE_UPDATE.equals(mode)){
			update();
		}
	}
}
