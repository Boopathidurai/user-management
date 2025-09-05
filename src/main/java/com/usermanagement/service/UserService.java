package com.usermanagement.service;

import com.usermanagement.dto.*;

import java.util.List;

public interface UserService {

	public List<CountryDto> getCountries();
	public List<StateDto> getStates(Integer CountyId);
	public List<CityDto> getCities(Integer StateId);
	public boolean isUniqueEmail(String email);
	public boolean register(UserDto userDto);
	public UserDto login(String email, String pswd);
	public String resetPassword(ResetPwdDto resetPwdDto);
	public QuoteApiResponseDto getQuote(String userEmail);



}
