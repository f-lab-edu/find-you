package com.aksrua.card.controller;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.dto.request.CreateCardRequestDto;
import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.dto.response.CreateCardResponseDto;
import com.aksrua.card.service.CardService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CardController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CardService cardService;

	private final UserRepository userRepository;

	@PostMapping("/cards")
	public ResponseEntity<CreateCardResponseDto> createCard(@RequestBody CreateCardRequestDto requestDto) {//TODO: 인증인가 방식을 활용한 UserId
		/*
		QnA: user id를 갖고있는 클라이언트에서 request dto에 user id 를 포함해서 보내준다
		controller에서 해당 user id로 조회한 user entity를 card entity 내부에 포함시켜
		service 계층으로 이동하는게 적절한 설계인가 ?
		 */
		User joinedUser = userRepository.findById(requestDto.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("회원조회가 되지 않습니다."));

		Card card = Card.builder()
				.user(joinedUser)
				.gender(requestDto.getGender())
				.nickname(requestDto.getNickname())
				.age(requestDto.getAge())
				.job(requestDto.getJob())
				.address(requestDto.getAddress())
				.introduction(requestDto.getIntroduction())
				.religion(requestDto.getReligion())
				.build();

		Card createdCard = cardService.createCard(card);

		return ResponseEntity.status(HttpStatus.CREATED).body(CreateCardResponseDto.fromEntity(createdCard));
	}

	@GetMapping("/cards")
	public ResponseEntity<List<CardResponseDto>> getCardsList() {
		List<CardResponseDto> responseDtoList = new ArrayList<>();
		List<Card> returnCardList = cardService.getCardList();

		for (Card card : returnCardList) {
			responseDtoList.add(CardResponseDto.builder()
					.id(card.getId())
//					.userId(card.getUserId())
					.gender(card.getGender())
					.nickname(card.getNickname())
					.age(card.getAge())
					.job(card.getJob())
					.address(card.getAddress())
					.introduction(card.getIntroduction())
					.distanceKm(card.getDistanceKm())
					.imagesUrl(card.getImagesUrl())
					.hobbies(card.getHobbies())
					.religion(card.getReligion())
					.build());
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}
}
