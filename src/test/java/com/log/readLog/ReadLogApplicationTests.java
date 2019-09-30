package com.log.readLog;

import com.log.readLog.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.log.readLog.Repo.EventRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadLogApplicationTests {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void checkLogEntryToInMemory() {

	}

}
