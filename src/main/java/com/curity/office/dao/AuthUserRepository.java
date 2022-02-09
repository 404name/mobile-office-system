package com.curity.office.dao;

import com.curity.office.model.AuthUser;
import com.curity.office.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 直接继承 JpaRepository 就有了增删改查等能力，该功能由 spring-data-jpa 提供
 * 泛型说明：Article 实体类对象  Long 主键数据类型
 *
 * @author liyuepeng
 * @date 2021/1/6 18:31
 */

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUserId(Long userId);
}
