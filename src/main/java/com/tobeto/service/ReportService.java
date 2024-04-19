package com.tobeto.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Item;
import com.tobeto.entity.Report;
import com.tobeto.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository repository;

	public Report createReport(String desc, Date date, Item item) {

		Report report = new Report();
		// report.setReportedDate();
		report.setActive(true);
		report.setDescription(desc);
		report.setItem(item);
		// report.setReportedDate(date);
		return repository.save(report);
	}

	public Optional<Report> getReport(int id) {
		return repository.findById(id);
	}

	public List<Report> getAllReports() {
		return repository.findAll();
	}

	public void closeReport(int id) {
		Optional<Report> report = getReport(id);
		report.get().setActive(false);
		// report.get().setClosedDate(date);
		repository.save(report.get());
	}
}
