package io.kpax.e2e.es.web.rest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.kpax.e2e.es.service.dto.UserDTO;

/**
 * Created by kpax on 30/11/2016.
 */
@RestController
@RequestMapping("/api")
public class SearchResource {
	private static final List<UserDTO> USERS = new ArrayList<>();

	static {
		USERS.add(new UserDTO("jpap", "John", "Papadopoulos", "jpap@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("kpav", "Konstantinos", "Pavlopoulos", "kpav@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("echar", "Eleni", "Charalampous", "echar@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("gchat", "Georgia", "Chatzipavlou", "gchat@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("avel", "Apostolos", "Velis", "avel@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("sliol", "Sofia", "Lioliou", "sliol@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("ipap", "Iordanis", "Papageorgiou", "ipap@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("jter", "John", "Terzis", "jter@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("mkar", "Maria", "Karatasou", "mkar@mail.com", true, "EL", new HashSet<>()));
		USERS.add(new UserDTO("gap", "George", "Apostolidis", "gap@mail.com", true, "EL", new HashSet<>()));
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> search(String query, String sort, String order) { //refactor service to use query params instead of path params
		List<UserDTO> results = new ArrayList<>();

		if (query != null && !query.trim().isEmpty()) {
			for (UserDTO user : USERS) {
				String regExp = "^" + query.trim().replace("*", ".*") + "$";

				Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
				Matcher firstnameMatcher = pattern.matcher(user.getFirstName());
				Matcher lastnameMatcher = pattern.matcher(user.getLastName());
                Matcher emailMatcher = pattern.matcher(user.getEmail().substring(0, user.getEmail().indexOf('.'))); //trim email to match in given regex pattern

				if (firstnameMatcher.matches() || lastnameMatcher.matches() || emailMatcher.matches()) {
					results.add(user);
				}
			}
		}

        if(!results.isEmpty()) { //sort if there are results
            switch(sort) {
                case "firstName":
                    if(order.equals("asc")) {
                        results.sort(Comparator.comparing(UserDTO::getFirstName));
                    }
                    else {
                        results.sort(Comparator.comparing(UserDTO::getFirstName, Collections.reverseOrder()));
                    }
                    break;
                case "email":
                    if(order.equals("asc")) {
                        results.sort(Comparator.comparing(UserDTO::getEmail));
                    }
                    else {
                        results.sort(Comparator.comparing(UserDTO::getEmail, Collections.reverseOrder()));
                    }
                    break;
                default: //default is lastName or from go button
                    if(order.equals("asc")) {
                        results.sort(Comparator.comparing(UserDTO::getLastName));
                    }
                    else {
                        results.sort(Comparator.comparing(UserDTO::getLastName, Collections.reverseOrder()));
                    }
            }
        }

		return new ResponseEntity<>(results, HttpStatus.OK);
	}
}
