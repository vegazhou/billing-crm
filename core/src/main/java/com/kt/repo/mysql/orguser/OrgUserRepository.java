package com.kt.repo.mysql.orguser;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.entity.mysql.user.OrgUser;

import java.util.List;

/**
 * The Interface OrgUserRepository.
 */
public interface OrgUserRepository extends JpaRepository<OrgUser, String>, OrgUserRepositoryCustom {

	OrgUser findOneByUserName(String username);
}
