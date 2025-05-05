package com.KCube.calibrationsystem.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

	  private String token;
	    public AuthResponse(String token) { this.token = token; }
	    public String getToken() { return token; }

}
