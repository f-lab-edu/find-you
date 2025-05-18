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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		/**
		QnA: user id를 갖고있는 클라이언트에서 request dto에 user id 를 포함해서 보내준다
		controller에서 해당 user id로 조회한 user entity를 card entity 내부에 포함시켜
		service 계층으로 이동하는게 적절한 설계인가 ?

		1) 토큰방식으로 유저 아이디를 감추는 방식
		2) x uid, x 유저 토큰,
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
		List<CardResponseDto> responseDtoList = cardService.getCardList()
				.stream()
				.map(CardResponseDto::fromEntity)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}

	@GetMapping("/cards/liked/sent")
	public ResponseEntity<List<CardResponseDto>> getSentLikedCards(@RequestBody CardRequestDto requestDto) {
		/**
		 * getCardsList() 와 같이 카드목록을 가지고 오는 DTO인데 DTO재활용 해도 괜찮을까 ?
		 * */
		List<CardResponseDto> responseDtoList = cardService.getSentLikedCards(requestDto.getCardId())
				.stream()
				.map(CardResponseDto::fromEntity)
				.toList();

		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}

	@GetMapping("/cards/liked/received")
	public ResponseEntity<List<CardResponseDto>> getCardLikedByOthers(@RequestBody CardRequestDto requestDto) {
		List<CardResponseDto> responseDtoList = cardService.getCardLikedByOthers(requestDto.getCardId())
				.stream()
				.map(CardResponseDto::fromEntity)
				.toList();

		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}
}
