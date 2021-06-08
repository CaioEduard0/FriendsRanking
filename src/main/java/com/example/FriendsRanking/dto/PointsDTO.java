package com.example.FriendsRanking.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PointsDTO {
	
	@Min(value = 0, message = "{points.size}")
	@Max(value = 100, message = "{points.size}")
	@NotNull(message = "{points.not.null}")
	private int points;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
