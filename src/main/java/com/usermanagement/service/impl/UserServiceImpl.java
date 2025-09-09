package com.usermanagement.service.impl;

import com.usermanagement.dto.*;
import com.usermanagement.entity.CityMaster;
import com.usermanagement.entity.CountryMaster;
import com.usermanagement.entity.StateMaster;
import com.usermanagement.entity.UserMaster;
import com.usermanagement.repo.CityRepo;
import com.usermanagement.repo.CountryRepo;
import com.usermanagement.repo.StateRepo;
import com.usermanagement.repo.UserRepo;
import com.usermanagement.service.EmailService;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CountryRepo countryRepo;
    private final StateRepo stateRepo;
    private final CityRepo cityRepo;
    private final EmailService emailService;

    @Override
    public boolean isUniqueEmail(String email) {
        UserMaster master=userRepo.findByEmail(email);
        return master !=null;
    }

    @Override
    public boolean register(UserDto dto) {

        UserMaster u = new UserMaster();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPhNo(dto.getPhNo());
        u.setUpdatedPwd("No");
        u.setStatus(true);
        u.setPwd("Uwl#"+new Random().nextInt(100));

        CountryMaster country=countryRepo.findById(Long.valueOf(dto.getCountry())).orElseThrow();
        StateMaster state=stateRepo.findById(Long.valueOf(dto.getState())).orElseThrow();
        CityMaster city=cityRepo.findById(Long.valueOf(dto.getCity())).orElseThrow();

        u.setCountry(country);
        u.setState(state);
        u.setCity(city);

        UserMaster savedUser=userRepo.save(u);

        if(savedUser!=null){
            String subject="Activate Your Account and Set Your Password";
        String body = "Hello " + u.getName() + ",\n\n" +
                "Your account username is: " + u.getEmail() + "\n" +
                "Your account password is: " + u.getPwd() + "\n" +
                "To reset your password, please click the following link:\n" +
                "http://localhost:8082/api/user/resetPassword\n\n" +
                "If you didn't request this, please ignore this message.\n\n" +
                "Regards,\n AshokIT Team";

         return emailService.sendMail(u.getEmail(),subject,body);
        }

        return false;
    }

    @Override
    public UserDto login(String email, String pwd) {
        UserMaster user=userRepo.findByEmailAndPwd(email,pwd);
        UserDto dto=new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhNo(user.getPhNo());
        dto.setUpdatedPwd(user.getUpdatedPwd());

        CountryMaster country=countryRepo.findById(user.getCountry().getId()).orElseThrow();
        dto.setCountry(country.getName());

        StateMaster state=stateRepo.findById(user.getState().getId()).orElseThrow();
        dto.setState(state.getName());

        CityMaster city=cityRepo.findById(user.getCity().getId()).orElseThrow();
        dto.setCity(city.getName());

        return dto;
    }

    @Override
    public String resetPassword(ResetPwdDto resetPwdDto) {
        UserMaster u=userRepo.findByEmail(resetPwdDto.getEmail());

        if(u==null) {
            return "Password Reset Failed";
        }

        if(resetPwdDto.getOldPwd().equals(u.getPwd())){
            return "Old password is not matched";
        }

        if (!resetPwdDto.getNewPwd().equals(resetPwdDto.getConfirmPwd())){
            return "New password and confirm Password should be same";
        }

        u.setPwd(resetPwdDto.getConfirmPwd());
        u.setUpdatedPwd("yes");
        userRepo.save(u);

        return "Password Reset Successfully";
    }

    @Override
    public QuoteApiResponseDto getQuote(String email ) {
        UserMaster u=userRepo.findByEmail(email);
        if (u.getEmail().equalsIgnoreCase(email)) {

            String dummyQuouteUrl = "https://dummyjson.com/quotes/random";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<QuoteApiResponseDto> response = restTemplate.getForEntity(dummyQuouteUrl, QuoteApiResponseDto.class);
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<CountryDto> getCountries() {
        List<CountryMaster> country=countryRepo.findAll();
        List<CountryDto> listDto=new ArrayList<>();

        for(CountryMaster master:country){
            CountryDto countryDto=new CountryDto();
            countryDto.setId(master.getId());
            countryDto.setName(master.getName());
            listDto.add(countryDto);
        }
        return listDto;
    }

    @Override
    public List<StateDto> getStates(Integer countyId) {
        List<StateMaster> state=stateRepo.findByCountry_Id(Long.valueOf(countyId));
        List<StateDto> stateDtoList=new ArrayList<>();

        for(StateMaster master:state){
            StateDto stateDto=new StateDto();
            stateDto.setId(master.getId());
            stateDto.setName(master.getName());
            stateDtoList.add(stateDto);
        }
        return stateDtoList;
    }

    @Override
    public List<CityDto> getCities(Integer stateId) {
        return cityRepo.findByState_Id(stateId).stream()
                .map(cityMaster -> {
                    CityDto cityDto=new CityDto();
                    cityDto.setId(cityMaster.getId());
                    cityDto.setName(cityMaster.getName());
                    return cityDto;
                })
                .collect(Collectors.toList());
    }

}
