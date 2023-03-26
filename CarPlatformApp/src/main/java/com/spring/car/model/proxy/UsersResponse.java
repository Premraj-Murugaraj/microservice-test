package com.spring.car.model.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
@Getter
public class UsersResponse{
	@JsonProperty("per_page")
	private int perPage;
	private int total;
	private List<DataItem> data;
	private int page;
	@JsonProperty("total_pages")
	private int totalPages;
	private Support support;
}