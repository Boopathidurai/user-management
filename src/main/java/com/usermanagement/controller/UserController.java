package com.usermanagement.controller;

import com.usermanagement.dto.*;
import com.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/uniqueEmail")
    public boolean isUniqueEmail(@RequestParam("email") String email){
        return userService.isUniqueEmail(email);
    }
    @GetMapping("/countries")
    public List<CountryDto> getAllCountries(){
       return userService.getCountries();
    }
    @GetMapping("/states/{id}")
    public List<StateDto> getAllStates(@PathVariable("id") Integer countryId){
        return userService.getStates(countryId);
    }

    @GetMapping("/cities/{id}")
    public List<CityDto> getAllCities(@PathVariable("id") Integer stateId){
        return userService.getCities(stateId);
    }

    @PostMapping("/signup")
    public boolean registration(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }

    @GetMapping("/login")
    public UserDto login(@RequestParam("email")String email,
                        @RequestParam("pwd")String pwd){

        return userService.login(email,pwd);
    }

    @PutMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPwdDto resetPwdDto){

        return userService.resetPassword(resetPwdDto);
    }

    @GetMapping("/get_dashboard")
    public QuoteApiResponseDto getDashboard(@RequestParam("email") String email){
        return userService.getQuote(email);
    }

}
