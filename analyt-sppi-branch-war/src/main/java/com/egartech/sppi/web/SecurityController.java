package com.egartech.sppi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.egartech.sppi.model.NewPassDTO;
import com.egartech.sppi.model.User;
import com.egartech.sppi.repo.UserRepository;

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
	
}
