package com.tekarch.TafUserService.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsersDTO {
    private Long id;
	private String username;
	private String email;
	private String phone_number;
	private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
