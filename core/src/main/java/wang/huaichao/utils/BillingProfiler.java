package wang.huaichao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * Created by Administrator on 9/28/2016.
 */
public class BillingProfiler {
    private static final Logger _log = LoggerFactory.getLogger(BillingProfiler.class);

    private long resetTime;
    private Logger log = _log;

    private long bootTime;
    private List<Long> timeHistory = new ArrayList<>();
    private List<String> messageHistory = new ArrayList<>();


    public void setLogger(Logger log) {
        this.log = log;
    }

    public void reset() {
        resetTime = System.currentTimeMillis();
    }

    public void stop(String message, Object... args) {
        log.debug(message, args);
        long diff = System.currentTimeMillis() - resetTime;
        log.debug("time used {}", diff);
        timeHistory.add(diff);
        messageHistory.add(message);
    }

    public void boot() {
        bootTime = System.currentTimeMillis();
    }

    public void terminate() {
        long runningTime = System.currentTimeMillis() - bootTime;

        long total = 0;

        for (Long aLong : timeHistory) {
            total += aLong;
        }


        for (int i = 0; i < timeHistory.size(); i++) {
            String a = String.format("%10d", timeHistory.get(i));
            String b = String.format("%5.2f%%", (timeHistory.get(i) * 100f / total));
            log.debug("{} {} {}", a, b, messageHistory.get(i));
        }

        log.debug(
                "running time: {}, logged time: {}, percentage: {}",
                runningTime,
                total,
                String.format("%5.2f%%", 100f * total / runningTime)
        );
    }
}
