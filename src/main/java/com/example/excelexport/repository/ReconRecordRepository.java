package com.example.excelexport.repository;

import com.example.excelexport.entity.ReconRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReconRecordRepository extends JpaRepository<ReconRecord, Long> {
}
