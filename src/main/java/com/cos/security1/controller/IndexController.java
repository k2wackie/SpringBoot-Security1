package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller // view를 리턴
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// localhost:8080/
	// localhost:8080
	@GetMapping({ "", "/" })
	public String index() {
		// mustache 사용, 기본폴더 src/main/resuources/
		// 뷰리졸버 설정:templates (prefix), .mustache (suffix), 생략가능
		return "index"; // src/main/resources/templates/index.mustache
	}

	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	// 스프링시큐리티 해당주소를 낚아챔 -> SecurityConfig파일 생성 후 작동 안 함
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);

		user.setRole("ROLE_USER");

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);

		userRepository.save(user); // 회원가입 잘 됨. 하지만 해쉬가 적용 안 됨

		return "redirect:/loginForm";
	}

}
