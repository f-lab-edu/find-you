package com.aksrua.card.controller;

import static com.aksrua.filter.dto.response.FilterResponseDto.fromEntity;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.card.data.repository.dto.response.CardListResponseDto;
import com.aksrua.card.dto.request.CardRequestDto;
import com.aksrua.card.dto.request.CreateCardRequestDto;
import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.dto.response.CreateCardResponseDto;
import com.aksrua.card.service.CardService;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.dto.request.UpdateFilterRequestDto;
import com.aksrua.filter.dto.response.FilterResponseDto;
import com.aksrua.filter.service.FilterService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private final FilterService filterService;
	private final UserService userService;

	//EntityNotFoundException
	@PostMapping("/cards")
	public ResponseEntity<CreateCardResponseDto> createCardAndSetFilter(@RequestBody CreateCardRequestDto requestDto) {//TODO: 인증방식 도입
		User joinedUser = userService.getUserDetail(requestDto.getUserId());

		Card card = Card.builder()
				.user(joinedUser)
				.nickname(requestDto.getNickname())
				.age(requestDto.getAge())
				.height(requestDto.getHeight())
				.bodyType(requestDto.getBodyType())
				.job(requestDto.getJob())
				.address(requestDto.getAddress())
				.introduction(requestDto.getIntroduction())
				.religion(requestDto.getReligion())
				.build();

		Filter filter = Filter.builder()
				.user(joinedUser)
				.minAge(20)
				.maxAge(40)
				.minHeight(150)
				.maxHeight(200)
				.bodyType(BodyType.ANY)
				.religion(Religion.ANY)
				.build();

		Card createdCard = cardService.createCardAndSetFilter(card, filter);
		return ResponseEntity.status(HttpStatus.CREATED).body(CreateCardResponseDto.fromEntity(createdCard));
	}

	@GetMapping("/cards")
	public ResponseEntity<List<CardListResponseDto>> getCardsList(@RequestParam Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardList(userId));
	}

	@GetMapping("/cards/{userId}/filter")
	public ResponseEntity<FilterResponseDto> getFilterDetail(@PathVariable Long userId) {
		FilterResponseDto filterDetail = fromEntity(filterService.getFilterDetail(userId));
		return ResponseEntity.status(HttpStatus.OK).body(filterDetail);
	}

	@PatchMapping("/cards/{userId}/filter")
	public void updateFilter(@PathVariable Long userId, @RequestBody UpdateFilterRequestDto requestDto) {
		User findUser = userService.getUserDetail(requestDto.getUserId());
		filterService.updateFilter(findUser, requestDto);
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
