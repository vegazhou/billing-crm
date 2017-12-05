package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.batch.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2015/10/27.
 */
public interface BatchRepository extends JpaRepository<Batch, String> {

    Batch findFirstByStatusOrderByCreateTimeAsc(String status);


}
