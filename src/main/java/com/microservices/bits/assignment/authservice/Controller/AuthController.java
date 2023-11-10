package com.microservices.bits.assignment.authservice.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bits.assignment.authservice.Entity.User;
import com.microservices.bits.assignment.authservice.userRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserRepo userRepo;


    @PostMapping("/auth")
    public ResponseEntity<?> doAtuh(@RequestBody String body) throws JsonProcessingException {
        Map<String,String> map = new HashMap<String,String>();

        ObjectMapper mapper = new ObjectMapper();

        map = mapper.readValue(body, HashMap.class);

        String mobileno = map.get("mobileno");
        String signature = map.get("signature");

        if(!userRepo.findById(mobileno).isEmpty()){

            User user = userRepo.findById(mobileno).get();
            String secretkey = user.getSecretkey();
            String serversig = hmacSha256(mobileno,secretkey);
            String urlstr = Base64.getUrlEncoder().encodeToString(serversig.getBytes());

            if(signature.equals(urlstr))
            return ResponseEntity.status(HttpStatus.OK).body("Auth successful");
            else
                return ResponseEntity.status(HttpStatus.OK).body("Auth Failed");

        }else{
            return ResponseEntity.status(HttpStatus.OK).body("User does not exsist");
        }

    }
    private String hmacSha256(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            //   Logger.getLogger(JWebToken.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

}
