
package MiniBlog.Main.Common.Utils;
import java.io.*; 
import java.util.*;



public class FileDataOption implements Runnable{
	static public enum ActionType{
		COVER, APPEND
	}
	static private volatile Integer Number = 0;
	private InputStream dataSource;
	private String pathName;
	private ActionType actionType;
	public FileDataOption(InputStream tDataSource, String tPathName, String tFileType, ActionType tActioinType){
		this.dataSource = tDataSource;
		this.pathName = adjustPathNameSeparator(tPathName+tFileType);
		this.actionType = tActioinType;
	}
	public FileDataOption(InputStream tDataSource, String tPathName, String tFileType){
		this(tDataSource, tPathName, tFileType, ActionType.COVER);
	}
	public FileDataOption(InputStream tDataSource, String tFileType){
		this(tDataSource, getRandomUniqueIdentificationWithSync(), tFileType);
	}
	public FileDataOption(InputStream tDataSource){
		this(tDataSource, getRandomUniqueIdentificationWithSync(), "");
	}
	public String getPathName(){
		return this.pathName;
	}
	public InputStream getDataSource(){
		return this.dataSource;
	}
	public ActionType getActionType(){
		return this.actionType;
	}
	public boolean saveData() throws IOException, FileNotFoundException{
		boolean result = false;
		if(null == this.dataSource || null == this.pathName)
			result = false;
		else{
			File file = new File(this.pathName);
			if(!file.exists()){
				file.createNewFile();
			}
			if(file.isDirectory() || !file.canWrite()){
				result = false;
			}else{
				FileOutputStream output = new FileOutputStream(this.pathName, (this.actionType != ActionType.APPEND ? false : true) );
				result = copyData(this.dataSource, output);
			}
		}
		return result;
	}
	public void run(){
		try{
			saveData();
		}catch(Exception e){
		}
	}

	//static method
	//**************** 将给定的inputstream集合生成FileDataOption集合
	static public List<FileDataOption> getListByInputStreams(List<InputStream> list, String path, String fileType){
		List<FileDataOption> result = new LinkedList<>();
		if(null != list){
			for(InputStream input : list){
				result.add(new FileDataOption(input, getRandomUniqueIdentificationWithSync(path), fileType));
			}
		}
		return result;
	}

	//**************** 获取随机标识
	/**
	 static Method -- 获取一个随机的标识字符串（通过返回时间戳的形式）
	 非线程安全的，因为在获取时间戳的过程有低概率出现多个线程获取的相同时间戳的情况。
	 @param headStr 在返回的标识串前加上指定的字符串。
	 @return 返回一个在头部加上了指定字符串的标识串。
	 */
	static public String getRandomUniqueIdentification(String headStr){
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(headStr);
		strBuilder.append(System.currentTimeMillis());
		synchronized(FileDataOption.Number){
			strBuilder.append(FileDataOption.Number);
			if(100000 < FileDataOption.Number)
				FileDataOption.Number = 0;
			else FileDataOption.Number++;
		}
		return strBuilder.toString();
	}
	/**
	 static Method -- 获取一个随机的标识字符串（通过返回时间戳的形式）
	 非线程安全的，因为在获取时间戳的过程有低概率出现多个线程获取的相同时间戳的情况。
	 @return 返回一个随机的标识串。
	 */
	static public String getRandomUniqueIdentification(){
		return getRandomUniqueIdentification("");
	}
	/**
	 static Method -- 获取一个随机的标识字符串（通过返回时间戳的形式）
	 线程安全的，因为在获取时间戳的过程有低概率出现多个线程获取的相同时间戳的情况。
	 @param headStr 在返回的标识串前加上指定的字符串。
	 @return 返回一个在头部加上了指定字符串的标识串。
	 */
	static public synchronized String getRandomUniqueIdentificationWithSync(String headStr){
		return getRandomUniqueIdentification(headStr);
	}
	/**
	 static Method -- 获取一个随机的标识字符串（通过返回时间戳的形式）
	 线程安全的，因为在获取时间戳的过程有低概率出现多个线程获取的相同时间戳的情况。
	 @return 返回一个随机的标识串。
	 */
	static public synchronized String getRandomUniqueIdentificationWithSync(){
		return getRandomUniqueIdentification("");
	}
	//**************** 数据拷贝调用
	/**
	 static Method -- 数据拷贝调用，从给定的数据源获取数据，并写入到指定的数据存储源。
	 @param input 数据来源
	 @param output 数据存储源
	 @return 数据拷贝成功：true；拷贝失败：false；
	 @exception IOException 数据读取或写入异常
	 */
	static public boolean copyData(InputStream input, OutputStream output) throws IOException{
		boolean result = true;
		if(null == input || null == output)
			result = false;
		else{
			try(InputStream src = input; OutputStream dest = output){
				byte data[] = new byte[2048];
				int length;
				while((length = src.read(data)) != -1){
					dest.write(data, 0, length);
				}
			}
		}
		return result;
	}
	//**************** 路径分隔符校正调用
	/**
	 static Method -- 因为不同的操作系统上，其所采用的路径分隔符不一样，该调用用于自动校正pathName参数中的路径分隔符。
	 */
	static public String adjustPathNameSeparator(String pathName){
		String separator = File.separator;
		String result = null;
		if("/".equals(separator))
			result = pathName.replace("\\", "/");
		else
			result = pathName.replace("/", "\\");
		return result;
	}

}
