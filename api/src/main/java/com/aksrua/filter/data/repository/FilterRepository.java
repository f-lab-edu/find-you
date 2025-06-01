package com.aksrua.filter.data.repository;

import com.aksrua.filter.data.entity.Filter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {

	Optional<Filter> findByUserId(Long userId);
}
