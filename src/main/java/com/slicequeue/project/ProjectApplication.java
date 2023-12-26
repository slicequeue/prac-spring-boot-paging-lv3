package com.slicequeue.project;

import com.slicequeue.project.common.type.WeightUnit;
import com.slicequeue.project.statistic.entity.WeightStatistic;
import com.slicequeue.project.statistic.repository.WeightStatisticRepository;
import com.slicequeue.project.user.entity.User;
import com.slicequeue.project.user.repository.UserRepository;
import com.slicequeue.project.weight.entity.WeightImageRecord;
import com.slicequeue.project.weight.entity.WeightRecord;
import com.slicequeue.project.weight.entity.WeightRecordComment;
import com.slicequeue.project.weight.repository.WeightImageRecordRepository;
import com.slicequeue.project.weight.repository.WeightRecordCommentRepository;
import com.slicequeue.project.weight.repository.WeightRecordRepository;
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
		private final UserRepository userRepository;
		private final WeightRecordRepository weightRecordRepository;
		private final WeightRecordCommentRepository weightRecordCommentRepository;
		private final WeightImageRecordRepository weightImageRecordRepository;
		private final WeightStatisticRepository weightStatisticRepository;

		public void dbInit() {
			// 사용자 1, 2 생성
			User user1 = User.builder()
					.name("사용자1")
					.birthDate(LocalDate.of(1990, 2, 12))
					.build();
			User user2 = User.builder()
					.name("사용자2")
					.birthDate(LocalDate.of(1991, 12, 14))
					.build();
			userRepository.saveAll(List.of(user1, user2));

			// 사용자1 몸무게 기록 & 코멘트 & 이미지 생성
			for (int i = 0; i < 25; i++) {
				String recordMemo = String.format("사용자1 %s 몸무게 기록", i + 1);
				WeightRecord weightRecord = WeightRecord.builder()
						.weight(70.f)
						.unit(WeightUnit.KG)
						.memo(recordMemo)
						.userId(user1.getId())
						.build();
				weightRecordRepository.save(weightRecord);
				for (int j = 0; j < 2; j++) {
					WeightRecordComment recordComment = WeightRecordComment.builder()
							.userId(user2.getId())
							.comment(String.format(recordMemo + "의 코멘트%s", j + 1))
							.weightRecord(weightRecord)
							.build();
					weightRecordCommentRepository.save(recordComment);
				}

				for (int j = 0; j < 3; j++) {
					WeightImageRecord imageRecord = WeightImageRecord.builder()
							.imageUrl(String.format(recordMemo + "의 이미지 URL %s", j + 1))
							.weightRecord(weightRecord)
							.build();
					weightImageRecordRepository.save(imageRecord);
				}

				// 통계 기록 생성
				WeightStatistic weightStatistic = WeightStatistic.builder()
						.userId(user1.getId())
						.totalMaxWeight(70.f)
						.totalMinWeight(70.f)
						.totalAvgWeight(70.f)
						.weightRecordId(weightRecord.getId())
						.build();
				weightStatisticRepository.save(weightStatistic);

			}
		}

	}

}
