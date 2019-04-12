package com.ivan.pazar.web.jobs;

import com.ivan.pazar.persistence.service.api.LogService;
import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteOldLogsJob {

    private final LogService logService;

    @Autowired
    public DeleteOldLogsJob(LogService logService) {
        this.logService = logService;
    }

    @Scheduled(fixedRate = WebConstants.ONE_DAY)
    @Async
    @Transactional
    public void deleteOldLogs() {
        logService.deleteLogsOlderThan5Days();
    }
}
