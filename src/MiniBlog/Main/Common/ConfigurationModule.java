package MiniBlog.Main.Common;//package MiniBlog.Main.Common;
//package
import java.io.*;
import java.util.Map;
import java.util.Properties;
/**
配置单元的工厂类

@author baldyoung
@version V0.5
*/
public class ConfigurationModule{
	private Map<String, String> parameterMap;
	public ConfigurationModule(Map<String, String> map){
		this.parameterMap = map;
	}
	public String getConfigParameter(String key){
		System.currentTimeMillis();
		if(null == parameterMap) return null;
		return parameterMap.get(key);
	}
	
}
	