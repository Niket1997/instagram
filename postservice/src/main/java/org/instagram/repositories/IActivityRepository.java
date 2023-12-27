package org.instagram.repositories;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.instagram.entities.Activity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

public interface IActivityRepository extends CrudRepository<Activity, Long> {
    // using pessimistic locking to avoid concurrency issues
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Activity findByParentId(Long parentId);
}
