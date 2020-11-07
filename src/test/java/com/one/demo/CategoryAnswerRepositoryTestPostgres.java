package com.one.demo;

import io.micronaut.core.util.CollectionUtils;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryAnswerRepositoryTestPostgres extends AbstractPostgresContainerBaseTest {

	@Inject
	CategoryAnswerRepository categoryAnswerRepository;

	@Inject
	AnswerVoteRepository answerVoteRepository;

	@Test
	public void testCategoryAnswerSaveAndRetrieve() {
		List<CategoryAnswer> answers = Stream.of(1,2,3,4,5).map(n -> {
			CategoryAnswer answer = new CategoryAnswer();
			answer.setCategory("cat"+n);
			answer.setAnswer("myanswer"+n);
			return answer;
		}).collect(Collectors.toUnmodifiableList());

		Iterable<CategoryAnswer> savedAnswers = categoryAnswerRepository.saveAll(answers);
		assertEquals(5, CollectionUtils.iterableToList(savedAnswers).size());
		savedAnswers.forEach(ans -> assertNotNull(ans.getId()));
		assertEquals(5, categoryAnswerRepository.count());

		// FindAll uses INNER JOIN should return nothing
		List<CategoryAnswer> roundAnswers = categoryAnswerRepository.findAll();
		assertNotNull(roundAnswers);
		roundAnswers.forEach(ans -> assertNotNull(ans.getId()));
		assertEquals(0, roundAnswers.size());

		// FindAllOrderById uses LEFT JOIN should return 5
		roundAnswers = categoryAnswerRepository.findAllOrderById();
		assertNotNull(roundAnswers);
		roundAnswers.forEach(ans -> assertNotNull(ans.getId()));
		assertEquals(5, roundAnswers.size());

		// Create votes on answers - 2 for each
		savedAnswers.forEach(ans -> {
			AnswerVote vote1 = new AnswerVote();
			vote1.categoryAnswer = ans;
			vote1.vote = "accept";
			answerVoteRepository.save(vote1);
			AnswerVote vote2 = new AnswerVote();
			vote2.categoryAnswer = ans;
			vote2.vote = "reject";
			answerVoteRepository.save(vote2);
		});

		// Should have 10 votes
		assertEquals(10, answerVoteRepository.count());

		// FindAll should now return 5 votes with INNER JOIN
		assertEquals(5, categoryAnswerRepository.findAll().size());
	}


}
