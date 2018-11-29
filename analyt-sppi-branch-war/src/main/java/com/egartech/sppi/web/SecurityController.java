package com.egartech.sppi.web;

import com.egartech.sppi.model.NewPassDTO;
import com.egartech.sppi.model.SelfRegistrationDTO;
import com.egartech.sppi.model.User;
import com.egartech.sppi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "setup-new-password", method = RequestMethod.POST)
    public ResponseEntity<?> setupNewPassword(RequestEntity<NewPassDTO> newPass) {
		
		User user = userRepository.findByUsernameAndFirstPasswordAndActivated(
				newPass.getBody().getUsername(), 
				newPass.getBody().getPassword(), 
				false);
		System.out.println(user);
		if (user!=null) {
			System.out.println("here "+newPass.getBody().getNewpassword());
			user.setActivated(true);
			user.setPassword(passwordEncoder.encode(newPass.getBody().getNewpassword()));
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
			
	}

	@RequestMapping(value = "commit-self-registration", method = RequestMethod.POST)
	public ResponseEntity<?> commitSelfRegistration(RequestEntity<SelfRegistrationDTO> registrationData) {

	    if (userRepository.findByUsername(registrationData.getBody().getUsername()).size() != 0) {
	        return new ResponseEntity<>(HttpStatus.MULTIPLE_CHOICES);
        } else {
            User newUser = new User(registrationData.getBody().getUsername(),
                    registrationData.getBody().getUserFIO(),
                    registrationData.getBody().getUserPersonnelNum(),
                    passwordEncoder.encode(registrationData.getBody().getPassword()));

            userRepository.save(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
	}
}
