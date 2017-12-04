package com.fable.insightview.platform.GuzhangrizManager.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangFile;
import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;
import com.fable.insightview.platform.GuzhangrizManager.mapper.GuzhangrizManagerMapper;
import com.fable.insightview.platform.GuzhangrizManager.service.GuzhangrizManagerService;
import com.fable.insightview.platform.page.Page;
@Service("guzhangrizManagerServiceImpl")
public class GuzhangrizManagerServiceImpl implements GuzhangrizManagerService {
	@Autowired
	private GuzhangrizManagerMapper guzhangrizManagerMapper;
	
	@Override
	public List<GuzhangrizManager> queryguzhangrizManagerList(Page page) {
		// TODO Auto-generated method stub
	 return guzhangrizManagerMapper.queryGuzhangrizManagerList(page);
	}
	public List<GuzhangFile> getTomcatLogs_Info(){
		String tomcatdir=System.getProperty("catalina.home");
		File file=new File(tomcatdir+File.separator+"logs");
		File[] files=file.listFiles();
		List<GuzhangFile> list=new ArrayList<GuzhangFile>();
		for(int i=0;i<files.length;i++){
			if(!files[i].isDirectory()){
				GuzhangFile aa=new GuzhangFile();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); 
				aa.setFileName(files[i].getName());
				aa.setWeiz(tomcatdir+File.separator+"logs"+File.separator);
				aa.setCreateTime(sdf.format(new Date(files[i].lastModified())));
				list.add(aa);
				//getTomcatLogsInfo(files[i],list.get(i));
			}
			
		}
		return list;
	}
/*	public void getTomcatLogsInfo(File dir, GuzhangFile bb){
		if(dir.isDirectory()){
			File[] files=dir.listFiles();
			ArrayList<GuzhangFile> list=new ArrayList<GuzhangFile>();
			for(int i=0;i<files.length;i++){
				GuzhangFile aa=new GuzhangFile();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); 
				aa.setFileName(files[i].getName());
				aa.setCreateTime(sdf.format(new Date(files[i].lastModified())));
				aa.setParentName(bb.getParentName());
				list.add(aa);
				getTomcatLogsInfo(files[i],aa);
			}
			bb.setList(list);
		}
		
	}*/
	@Override
	public List<GuzhangrizManager> queryBackupRecodes(GuzhangrizManager manager) {
		return guzhangrizManagerMapper.queryBackupRecodes(manager);
	}
	@Override
	public GuzhangrizManager queryBackupRecodeById(int id) {
		GuzhangrizManager manager = new GuzhangrizManager();
		manager.setId(id);
		List<GuzhangrizManager> datas = queryBackupRecodes(manager);
		if (!CollectionUtils.isEmpty(datas)){
			manager = datas.get(0);
		}
		return manager;
	}
	
	@Override
	public void updateBackupRecode(GuzhangrizManager manager) {
		guzhangrizManagerMapper.updateBackupRecode(manager);
	}

}
