package com.aksrua.like.dto.response;

import com.aksrua.like.data.entity.Like;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendLikeResponseDto {

	private String receiverNickname;

	public static SendLikeResponseDto fromEntity(Like like) {
		return SendLikeResponseDto.builder()
				.receiverNickname(like.getReceiverCard().getNickname())
				.build();
	}
}
