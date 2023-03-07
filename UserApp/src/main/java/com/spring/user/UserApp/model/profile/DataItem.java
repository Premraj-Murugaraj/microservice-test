package com.spring.user.UserApp.model.profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataItem{
	private String lastName;
	private int id;
	private String avatar;
	private String firstName;
	private String email;
}
