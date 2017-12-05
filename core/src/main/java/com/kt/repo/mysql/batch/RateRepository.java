package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
public interface RateRepository extends JpaRepository<Rate, String>, RateRepositoryCustom {
    List<Rate> findByPid(String pid);

    List<Rate> findByPidOrderByCountryAsc(String pid);

    Rate findFirstByPidAndCode(String pid, String code);

    void deleteByPid(String pid);
}
