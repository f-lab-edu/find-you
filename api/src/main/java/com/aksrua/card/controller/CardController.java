package com.aksrua.card.controller;

import static com.aksrua.filter.dto.response.FilterResponseDto.fromEntity;

import com.aksrua.auth.service.AuthTokenService;
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
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.dto.response.RemoveLikeResponseDto;
import com.aksrua.interaction.dto.response.SendLikeResponseDto;
import com.aksrua.interaction.dto.response.SentLikeCardsListResponseDto;
import com.aksrua.interaction.service.InteractionService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	private final AuthTokenService authTokenService;

	@PostMapping("/cards")
	public ApiResponse<CreateCardResponseDto> createCard(@Valid @RequestBody CreateCardRequestDto requestDto) {//TODO: 인증방식 도입
		User joinedUser = userService.getUserDetails(requestDto.getUserId());
		Card card = requestDto.toEntity(joinedUser); //TODO: 여기 어디선가 birth_date를 age로 변환하는데 그거 card entity에서 해주기
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
	 * TODO: 상대방 조회도 비즈니스적으로 필요한 부분이다. 아무나 조회를 못 하도록 해야하는데...
	 */
	@GetMapping("/cards/{userId}")
	public ApiResponse<CardResponseDto> getCardDetail(@PathVariable Long userId, HttpServletRequest request) {
													  /*@RequestHeader("X-Auth-Token") String authToken,*/
		Long authId = (Long) request.getAttribute("userId");
		return ApiResponse.ok(CardResponseDto.fromEntity(cardService.getCardDetails(userId, authId)));
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
		User findUser = userService.getUserDetails(userId);
		Filter filter = requestDto.toEntity(findUser);
		Filter savedFilter = filterService.createFilter(filter);
		return ApiResponse.of(HttpStatus.CREATED, CreateFilterResponseDto.fromEntity(savedFilter));
	}

	@PatchMapping("/cards/{userId}/filter")
	public ApiResponse<UpdateFilterResponseDto> updateFilter(@PathVariable Long userId, @RequestBody UpdateFilterRequestDto requestDto) {
//		User findUser = userService.getReferenceById(userId);
		User findUser = userService.getUserDetails(userId);
		Filter filter = requestDto.toEntity(findUser);
		Filter updateFilter = filterService.updateFilter(userId, filter);
		return ApiResponse.ok(UpdateFilterResponseDto.fromEntity(updateFilter));
	}

	@PostMapping("/cards/{userId}/likes/{cardId}")
	public ApiResponse<SendLikeResponseDto> sendLike(@PathVariable Long userId, @PathVariable Long cardId) {
		//TODO: 서로 좋아요가 되었을때 좋아요를 보냈던 기존 사용자에게도 좋아요 알림이 가도록 기능을 추가해야한다.
		Like like = Like.createSentLike(userId, cardId);
		Like sentLike = interactionService.sendLike(like);
		return ApiResponse.of(HttpStatus.CREATED, SendLikeResponseDto.fromEntity(sentLike));
	}

	@GetMapping("/cards/{userId}/likes/sent")
	public ApiResponse<List<SentLikeCardsListResponseDto>> getSentLikesList(@PathVariable Long userId) {
		List<SentLikeCardsListResponseDto> sentLikeCardsList = interactionService.getSentLikesList(userId)
				.stream()
				.map(SentLikeCardsListResponseDto::fromEntity)
				.collect(Collectors.toList());
		return ApiResponse.ok(sentLikeCardsList);
	}

	@DeleteMapping("/cards/{userId}/likes/{likeId}")
	public ApiResponse<RemoveLikeResponseDto> removeLike(@PathVariable Long userId, @PathVariable Long likeId) {
		interactionService.removeLike(userId, likeId);
		return ApiResponse.of(HttpStatus.NO_CONTENT, new RemoveLikeResponseDto(likeId, "삭제가 완료 되었습니다."));
	}

	@GetMapping("/cards/{userId}/likes/received")
	public void getReceivedLikesList(@PathVariable Long userId) {

	}

	@GetMapping("/cards/{userId}/likes/sent/{likeId}")
	public void getSentLikeDetails(@PathVariable Long userId, @PathVariable Long likeId) {

	}

	@GetMapping("/cards/{userId}/likes/received/{likeId}")
	public void getReceivedLikeDetails(@PathVariable Long userId, @PathVariable Long likeId) {
		//TODO: 받은 좋아요를 보면은 해당 LIKE의 상태를 변화 시켜줘야 한다.
	}

	@PostMapping("/cards/{userId}/dislikes/{cardId}")
	public void sendDislike(@PathVariable Long userId, @PathVariable Long cardId) {

	}
}
