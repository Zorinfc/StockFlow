package com.tobeto.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.report.ReportCreateDTO;
import com.tobeto.dto.report.ReportRequestDTO;
import com.tobeto.dto.report.ReportResponseDTO;
import com.tobeto.entity.Report;
import com.tobeto.service.ReportService;

@RestController
@RequestMapping("api/v1/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@PostMapping("/create")
	public ReportResponseDTO createReport(@RequestBody ReportCreateDTO dto) {
		Report report = reportService.createReport(dto);
		ReportResponseDTO resp = responseMapper.map(report, ReportResponseDTO.class);
		return resp;
	}

	@GetMapping
	public List<ReportResponseDTO> getReport() {
		List<ReportResponseDTO> response = reportService.getAllReports().stream()
				.map(s -> responseMapper.map(s, ReportResponseDTO.class)).toList();
		return response;
	}

	@PostMapping("/close")
	public void closeReport(@RequestBody ReportRequestDTO dto) {
		reportService.closeReport(dto.getId());
	}

	@PostMapping("/open")
	public void openReport(@RequestBody ReportRequestDTO dto) {
		reportService.openReport(dto.getId());
	}

	@PostMapping("/delete")
	public void deleteReport(@RequestBody ReportRequestDTO dto) {
		reportService.deleteReport(dto.getId());
	}

}
