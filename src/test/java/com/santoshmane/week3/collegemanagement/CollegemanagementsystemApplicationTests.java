package com.santoshmane.week3.collegemanagement;

import com.santoshmane.week3.collegemanagement.entities.Role;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import com.santoshmane.week3.collegemanagement.services.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class CollegemanagementsystemApplicationTests {

	@Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {
//		UserEntity userEntity = new UserEntity(4L,"prem@gmail.com","123","Prem" ,Set.of(Role.ADMIN));
//
//		String token = jwtService.generateToken(userEntity);
//
//		System.out.println(token);
//
//		Long id = jwtService.getUserIdFromToken(token);
//
//		System.out.println(id);
	}

}
