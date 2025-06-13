package com.aksrua.interaction.dto.response;

import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendLikeResponseDto {

	private Long likeId;

	private Long senderCardId;

	private Long receiverCardId;

	private LikeStatus likeStatus;

	public static SendLikeResponseDto fromEntity(Like like) {
		return SendLikeResponseDto.builder()
				.likeId(like.getId())
				.senderCardId(like.getSenderCardId())
				.receiverCardId(like.getReceiverCardId())
				.likeStatus(like.getLikeStatus())
				.build();
	}
}
