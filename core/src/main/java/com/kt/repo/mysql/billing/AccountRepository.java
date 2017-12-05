package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillTemp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Garfield Chen on 2016/5/5.
 */
public interface AccountRepository extends JpaRepository<BillTemp, Long>, AccountRepositoryCustom  {

//   

}
