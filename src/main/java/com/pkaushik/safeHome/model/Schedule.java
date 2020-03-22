package com.pkaushik.safeHome.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Walker walker;
	

	public Schedule(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
