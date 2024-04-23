package com.tobeto.dto.report;

import lombok.Data;

@Data
public class ReportResponseDTO {

	private int id;
	private String userEmail;
	private String userName;
	private String userLastName;
	private String description;
	private String itemName;
	private boolean isActive;

}
