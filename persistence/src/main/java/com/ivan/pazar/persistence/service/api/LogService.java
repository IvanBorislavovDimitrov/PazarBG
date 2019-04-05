package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.LogServiceModel;

public interface LogService {

    LogServiceModel save(LogServiceModel logServiceModel);

    void deleteLogsOlderThan5Days();
}
