package com.ef.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedLogRepository extends JpaRepository<BlockedIp, Integer> {

}
