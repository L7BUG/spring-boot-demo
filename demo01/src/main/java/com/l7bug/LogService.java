package com.l7bug;

import com.l7bug.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogService {
    public boolean saveLog(LogModel logModel) {
        log.info("记录日志,{}", logModel.toString());
        return true;
    }
}
