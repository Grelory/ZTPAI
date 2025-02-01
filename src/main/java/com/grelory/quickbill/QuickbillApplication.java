package com.grelory.quickbill;

import com.grelory.quickbill.entity.User;
import com.grelory.quickbill.repositories.UsersRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class QuickbillApplication {

	public static void main(String[] args) {
		final var context = SpringApplication.run(QuickbillApplication.class, args);
		final var beanFactory = context.getBeanFactory();
		final var passwordEncoder = beanFactory.getBean(PasswordEncoder.class);
		beanFactory.getBean(UsersRepository.class)
				.saveAll(initialUsers(passwordEncoder));
	}

	public static List<User> initialUsers(PasswordEncoder passwordEncoder) {
		final var users = new ArrayList<User>();
		final var admin = new User();
		admin.setUserEmail("admin@example.com");
		admin.setUserPassword(passwordEncoder.encode("admin"));
		admin.setRole("ADMIN");
		users.add(admin);
		final var user = new User();
		user.setUserEmail("user@example.com");
		user.setUserPassword(passwordEncoder.encode("user"));
		user.setRole("USER");
		users.add(user);
		return users;
	}

}
