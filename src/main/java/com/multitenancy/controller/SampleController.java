package com.multitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.multitenancy.entity.User;
import com.multitenancy.service.SampleService;

@RestController
public class SampleController {

	@Autowired
	private SampleService sampleService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		User user = sampleService.findUserById(Long.valueOf(id));
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/create/user")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		sampleService.createUser(user);
		return ResponseEntity.ok("User is saved");
	}

}
