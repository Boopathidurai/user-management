package com.usermanagement.controller;

import com.usermanagement.dto.*;
import com.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Operation(
            summary = "Get uniqueEmail",
            description = "Check the uniqueEmail in user table"
    )
    @GetMapping("/uniqueEmail")
    public boolean isUniqueEmail(@RequestParam("email") String email){

        return userService.isUniqueEmail(email);
    }

    @Operation(
            summary = "Get All Countries",
            description = "Retrieves a list of all available countries"
    )
    @GetMapping("/countries")
    public List<CountryDto> getAllCountries(){

        return userService.getCountries();
    }

    @Operation(
            summary = "Get All States By Country ID",
            description = "Retrieves a list of all available states"
    )
    @GetMapping("/states/{id}")
    public List<StateDto> getAllStates(@PathVariable("id") Integer countryId){
        return userService.getStates(countryId);
    }

    @Operation(
            summary = "Get All Cities by State ID",
            description = "Retrieves a list of all available cities"
    )
    @GetMapping("/cities/{id}")
    public List<CityDto> getAllCities(@PathVariable("id") Integer stateId){

        return userService.getCities(stateId);
    }

    @Operation(
            summary = "Register the user details",
            description = "Signup the users"
    )
    @PostMapping("/signup")
    public boolean registration(@RequestBody UserDto userDto){

        return userService.register(userDto);
    }

    @Operation(
            summary = "Get login the users",
            description = "Login the users"
    )
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
