package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dto.report.ReportCreateDTO;
import com.tobeto.entity.Report;
import com.tobeto.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository repository;
	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;

	public Report createReport(ReportCreateDTO dto) {

		Report report = new Report();
		report.setActive(true);
		report.setDescription(dto.getDescription());
		report.setItem(itemService.getItemByName(dto.getItemName()).get());
		report.setUser(userService.getUserByEmail(dto.getUserEmail()).get());
		return repository.save(report);
	}

	public Optional<Report> getReport(int id) {
		return repository.findById(id);
	}

	public List<Report> getAllReports() {
		return repository.findAll();
	}

	public void deleteReport(int id) {
		Optional<Report> report = getReport(id);
		repository.delete(report.get());
	}

	public void closeReport(int id) {
		Optional<Report> report = getReport(id);
		report.get().setActive(false);
		repository.save(report.get());
	}

	public void openReport(int id) {
		Optional<Report> report = getReport(id);
		report.get().setActive(true);
		repository.save(report.get());
	}
}
