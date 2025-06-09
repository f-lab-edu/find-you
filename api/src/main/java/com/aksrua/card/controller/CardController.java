package com.aksrua.card.controller;

import static com.aksrua.filter.dto.response.FilterResponseDto.fromEntity;

import com.aksrua.card.controller.dto.response.CardListResponseDto;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.controller.dto.request.CreateCardRequestDto;
import com.aksrua.card.controller.dto.response.CardResponseDto;
import com.aksrua.card.controller.dto.response.CreateCardResponseDto;
import com.aksrua.card.service.CardService;
import com.aksrua.common.dto.ApiResponse;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.dto.request.CreateFilterRequestDto;
import com.aksrua.filter.dto.request.UpdateFilterRequestDto;
import com.aksrua.filter.dto.response.CreateFilterResponseDto;
import com.aksrua.filter.dto.response.FilterResponseDto;
import com.aksrua.filter.dto.response.UpdateFilterResponseDto;
import com.aksrua.filter.service.FilterService;
import com.aksrua.interaction.service.InteractionService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private final UserService userService;
	private final FilterService filterService;
	private final InteractionService interactionService;

	@PostMapping("/cards")
	public ApiResponse<CreateCardResponseDto> createCard(@Valid @RequestBody CreateCardRequestDto requestDto) {//TODO: 인증방식 도입
		User joinedUser = userService.getUserDetail(requestDto.getUserId());
		Card card = requestDto.toEntity(joinedUser);
		Card createdCard = cardService.createCard(card);
		return ApiResponse.of(HttpStatus.CREATED, CreateCardResponseDto.fromEntity(createdCard));
	}

	@GetMapping("/cards")
	public ApiResponse<List<CardListResponseDto>> getCardsList(@RequestParam Long userId) {
		List<CardListResponseDto> responseList = cardService.getCardList(userId).stream()
				.map(CardListResponseDto::fromEntity)
				.toList();
		return ApiResponse.ok(responseList);
	}

	/**
	 * @desc: 조회도 서비스적으로 비용이 필요한 부분이다. 아무나 조회를 못 하도록 해야하는데...
	 */
	@GetMapping("/cards/{userId}")
	public ApiResponse<CardResponseDto> getCardDetail(@PathVariable Long userId) {
		return ApiResponse.ok(CardResponseDto.fromEntity(cardService.getCardDetails(userId)));
	}

	@GetMapping("/cards/{userId}/filter")
	public ApiResponse<FilterResponseDto> getFilterDetail(@PathVariable Long userId) {
		FilterResponseDto filterDetail = fromEntity(filterService.getFilterDetail(userId));
		return ApiResponse.ok(filterDetail);
	}

	@PostMapping("/cards/{userId}/filter")
	public ApiResponse<CreateFilterResponseDto> createFilter(@PathVariable Long userId, @RequestBody CreateFilterRequestDto requestDto) {
		//TODO: user 조회를 성능적으로 보완한 jpa getReferenceById 권장사항
//		User findUser = userService.getReferenceById(userId);
		User findUser = userService.getUserDetail(userId);
		Filter filter = requestDto.toEntity(findUser);
		Filter savedFilter = filterService.createFilter(filter);
		return ApiResponse.of(HttpStatus.CREATED, CreateFilterResponseDto.fromEntity(savedFilter));
	}

	@PatchMapping("/cards/{userId}/filter")
	public ApiResponse<UpdateFilterResponseDto> updateFilter(@PathVariable Long userId, @RequestBody UpdateFilterRequestDto requestDto) {
//		User findUser = userService.getReferenceById(userId);
		User findUser = userService.getUserDetail(userId);
		Filter filter = requestDto.toEntity(findUser);
		Filter updateFilter = filterService.updateFilter(userId, filter);
		return ApiResponse.ok(UpdateFilterResponseDto.fromEntity(updateFilter));
	}

	@PostMapping("/cards/{userId}/likes/{cardId}")
	public void sendLike(@PathVariable Long userId, @PathVariable Long cardId) {

	}

	@DeleteMapping("/cards/{userId}/likes/{cardId}")
	public void removeLike(@PathVariable Long userId, @PathVariable Long cardId) {

	}
}
