package com.expensereport.repository;

import com.expensereport.domain.Lineitem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lineitem entity.
 */
public interface LineitemRepository extends JpaRepository<Lineitem,Long> {

}
