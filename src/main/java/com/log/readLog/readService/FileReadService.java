package com.log.readLog.readService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.log.readLog.EventDAO.EventDAO;
import com.log.readLog.model.Event;
import com.log.readLog.model.FileModel;

@Service
public class FileReadService {
	
	@Value("${logfile.path}")
	private String filePath;
	
	@Autowired
	private EventDAO eventDAO;
	
	private static final Logger log = LoggerFactory.getLogger(FileReadService.class);

	@PostConstruct
	public void init() throws IOException {
		readFile();
	}

	private void readFile() throws IOException {
		
		Map<String, List<FileModel>> logMap =  new HashMap<String, List<FileModel>>();
		log.info("Started Iterating log file");
		LineIterator it = FileUtils.lineIterator(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		try {
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        // Convert the string to File Object
				FileModel model = gson.fromJson(line, FileModel.class);
				
				if(logMap.containsKey(model.getId())){
					// If entry exists then calculate the duration 
					log.info("Start : Calculate duration for id - " + model.getId());
					
					calculateDurationAndSave(logMap, model);
					
					log.info("End : Calculate duration for id - " + model.getId());
				}else {
					// New Entries to the Map
					List<FileModel> fileList = new ArrayList<FileModel>();
					fileList.add(model);
					logMap.put(model.getId(), fileList);
				}
		    }
		}catch(Exception e){
			log.error("Error Reading file", e);
		}finally {
		    LineIterator.closeQuietly(it);
		}
	}

	private void calculateDurationAndSave(Map<String, List<FileModel>> logMap, FileModel model) {
		Long timeTaken = 0l;
		boolean timeCalculated = false;
		
		if(model.getState().equalsIgnoreCase("FINISHED")) {
			timeTaken = model.getTimestamp() - logMap.get(model.getId()).get(0).getTimestamp();
			log.debug("Duration for id - " + model.getId() + " is - " +timeTaken);
			timeCalculated = true; 
		}else if(logMap.get(model.getId()).get(0).getState().equalsIgnoreCase("FINISHED")) {
			timeTaken = logMap.get(model.getId()).get(0).getTimestamp() - model.getTimestamp();
			log.debug("Duration for id - " + model.getId() + " is - " +timeTaken);
			timeCalculated = true; 
		}
		if(timeCalculated) {
			Event event = new Event(model.getId(), timeTaken, model.getType(), model.getHost(), timeTaken >= 4 ? true :false);
			
			// Save Event;
			eventDAO.saveEvent(event);
			//Delete the Map Entry once processed
			logMap.remove(model.getId());
			
			//Check
//			Event eventGet = eventDAO.getEventById(event.getEventId());
//			System.out.println("------------"+eventGet.getEventId() +"---"+ eventGet.getDuration() +"---"+ eventGet.isAlert());
		}
	}
}
