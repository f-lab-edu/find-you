package com.aksrua.card.controller;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.dto.request.CardRequestDto;
import com.aksrua.card.dto.request.CreateCardRequestDto;
import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.dto.response.CreateCardResponseDto;
import com.aksrua.card.service.CardService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CardController {

	private final CardService cardService;
	private final UserRepository userRepository;

	@PostMapping("/cards")
	public ResponseEntity<CreateCardResponseDto> createCard(@RequestBody CreateCardRequestDto requestDto) {//TODO: 인증방식 도입
		User joinedUser = userRepository.findById(requestDto.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("회원조회가 되지 않습니다."));

		Card card = Card.builder()
				.user(joinedUser)
				.gender(requestDto.getGender())
				.nickname(requestDto.getNickname())
				.age(requestDto.getAge())
				.height(requestDto.getHeight())
				.bodyType(requestDto.getBodyType())
				.job(requestDto.getJob())
				.address(requestDto.getAddress())
				.introduction(requestDto.getIntroduction())
				.religion(requestDto.getReligion())
				.build();

		Card createdCard = cardService.createCard(card, joinedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(CreateCardResponseDto.fromEntity(createdCard));
	}

	@GetMapping("/cards")
	public ResponseEntity<List<?>> getCardsList(@RequestParam Long userId) {
//	public ResponseEntity<List<CardResponseDto>> getCardsList(@RequestParam Long userId) {

		/*
		List<CardResponseDto> responseDtoList = cardService.getCardList()
				.stream()
				.map(CardResponseDto::fromEntity)
				.collect(Collectors.toList());

		 */
		List<Card> findCardList = cardService.getCardList(userId);

		return ResponseEntity.status(HttpStatus.OK).body(findCardList);
	}

	@GetMapping("/cards/liked/sent")
	public ResponseEntity<List<CardResponseDto>> getSentLikedCards(@RequestBody CardRequestDto requestDto) {
		/**
		 * getCardsList() 와 같이 카드목록을 가지고 오는 DTO인데 DTO재활용 해도 괜찮을까 ?
		 *
		 * TODO: 인증 체계 활용(Access token)
		 * */
		List<CardResponseDto> responseDtoList = cardService.getSentLikedCards(requestDto.getCardId())
				.stream()
				.map(CardResponseDto::fromEntity)
				.toList();

		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}

	/**
	 * TODO: 인증 체계 활용(Access token)
	 * @param requestDto
	 * @return
	 */
	@GetMapping("/cards/liked/received")
	public ResponseEntity<List<CardResponseDto>> getCardLikedByOthers(@RequestBody CardRequestDto requestDto) {
		List<CardResponseDto> responseDtoList = cardService.getCardLikedByOthers(requestDto.getCardId())
				.stream()
				.map(CardResponseDto::fromEntity)
				.toList();

		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}
}
