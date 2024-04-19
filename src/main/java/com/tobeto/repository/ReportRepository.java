package com.tobeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

}
