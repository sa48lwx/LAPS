package com.example.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.portal.model.Leavedata;

@Repository
public interface LeavedataRepository extends JpaRepository<Leavedata, Long>{

}
