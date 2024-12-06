package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.ResponseDTO;
import lk.sasax.GreenShadow.dto.UserDTO;
import lk.sasax.GreenShadow.entity.User;
import lk.sasax.GreenShadow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceIMPL {

    @Autowired
    private UserRepository ourUserRepo;
    @Autowired
    private JWTServiceIMPL jwtServiceIMPL;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseDTO signUp(ResponseDTO registrationRequest){
        ResponseDTO resp = new ResponseDTO();
        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            User ourUserResult = ourUserRepo.save(user);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseDTO signIn(ResponseDTO signinRequest){
        ResponseDTO response = new ResponseDTO();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtServiceIMPL.generateToken(user);
            var refreshToken = jwtServiceIMPL.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(201);
            response.setToken(jwt);
//            response.setRefreshToken(refreshToken);
            /*response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");*/
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ResponseDTO refreshToken(ResponseDTO refreshTokenReqiest){
        ResponseDTO response = new ResponseDTO();
        String ourEmail = jwtServiceIMPL.extractUsername(refreshTokenReqiest.getToken());
        User users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtServiceIMPL.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtServiceIMPL.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }



    public ResponseDTO getAllUsers() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<User> usersList = ourUserRepo.findAll();
            if (usersList.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No Users Found");
            } else {
                List<UserDTO> userDTOList = usersList.stream()
                        .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getPassword()))
                        .collect(Collectors.toList());

                response.setStatusCode(200);
                response.setUsersList(userDTOList);
                response.setMessage("Users Fetched Successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error fetching users: " + e.getMessage());
        }
        return response;
    }


    public void deleteUser(Integer Code) {
        ourUserRepo.deleteById(Code);
    }


}
