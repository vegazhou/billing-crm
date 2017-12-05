package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.WebExRequest;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Garfield Chen on 2016/6/14.
 */
public interface RequestRepository extends JpaRepository<WebExRequest, Long>, RequestRepositoryCustom {
}
