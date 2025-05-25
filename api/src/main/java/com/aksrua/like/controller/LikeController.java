package com.aksrua.like.controller;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.repository.CardRepository;
import com.aksrua.like.data.entity.Like;
import com.aksrua.like.dto.request.SendLikeRequestDto;
import com.aksrua.like.dto.response.SendLikeResponseDto;
import com.aksrua.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class LikeController {
	/**
	 * likecontroller가 필요할까 ? cardcontroller에 종속시켜도 되지 않을까 ?
	 */
	private final LikeService likeService;
	private final CardRepository cardRepository;

	@PostMapping("/likes")
	public ResponseEntity<SendLikeResponseDto> sendLike(@RequestBody SendLikeRequestDto requestDto) {
		Card senderCard = cardRepository.findByUserId(requestDto.getSenderCardId());
		Card receiverCard = cardRepository.findByUserId(requestDto.getReceiverCardId());

		Like like = Like.builder()
				.senderCard(senderCard)
				.receiverCard(receiverCard)
				.build();

		Like sendedLike = likeService.likeSend(like);

		return ResponseEntity.status(HttpStatus.CREATED).body(SendLikeResponseDto.fromEntity(sendedLike));
	}

	@DeleteMapping("/likes/{likeId}")
	public ResponseEntity<Void> removeLike(@PathVariable final Long likeId) {
		likeService.removeLike(likeId);
		return ResponseEntity.noContent().build();
	}
}

