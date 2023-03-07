package com.spring.user.UserApp.model.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile{
	private int perPage;
	private int total;
	private List<DataItem> data;
	private int page;
	private int totalPages;
	private Support support;
}