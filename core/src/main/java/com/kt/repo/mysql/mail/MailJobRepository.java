package com.kt.repo.mysql.mail;


import com.kt.entity.mysql.mail.MailJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2015/10/15.
 */
public interface MailJobRepository extends JpaRepository<MailJob, String> {

    Page<MailJob> findByStatus(String status, Pageable pageable);

}