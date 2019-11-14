package MiniBlog.Main.Common;

import java.util.concurrent.*;
import java.util.List;

public class WorkingPoolModule{
	
	private ExecutorService service;
	private String name;
	
	public WorkingPoolModule(ExecutorService tService, String tName){
		this.service = tService;
		this.name = tName;
	}
	public WorkingPoolModule(ExecutorService tService){
		this(tService, "");
	}
	public WorkingPoolModule(String tName){
		this(Executors.newCachedThreadPool(), tName);
	}
	public WorkingPoolModule(){
		this("");
	}
	private void shutdown(){
		if(null != this.service) 
			this.service.shutdown();
		this.service = null;
	}
	public boolean submit(Runnable r){
		boolean result = false;
		try{
			if(null != this.service && !this.service.isShutdown()){
				this.service.submit(r);
				result = true;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}
	public int submit(List<FileDataOption> rList){
		int result = 0;
		for(Runnable r : rList){
			if(this.submit(r)) result++;
		}
		return result;
	}

	
	
	
}