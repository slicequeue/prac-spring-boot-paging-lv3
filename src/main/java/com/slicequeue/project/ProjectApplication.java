package com.slicequeue.project;

import com.slicequeue.project.user.entity.User;
import com.slicequeue.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication {

	private final InitService initService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@PostConstruct
	public void init() {
		initService.dbInit();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {
		private final EntityManager em;
		private final UserRepository userRepository;

		public void dbInit() {
			// 사용자 1, 2 생성
			User user1 = User.builder()
					.name("사용자1")
					.birthDate(LocalDate.of(1990, 2, 12))
					.build();
			User user2 = User.builder()
					.name("사용자2")
					.birthDate(LocalDate.of(1990, 2, 12))
					.build();
			userRepository.saveAll(List.of(user1, user2));

		}

	}

}
