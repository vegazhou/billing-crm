package wang.huaichao.data.repo;

import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.MeetingReport;

/**
 * Created by Administrator on 8/9/2016.
 */
public interface MeetingReportRepository extends CrudRepository<MeetingReport, Long> {
    public MeetingReport findByConfId(long confId);
}
