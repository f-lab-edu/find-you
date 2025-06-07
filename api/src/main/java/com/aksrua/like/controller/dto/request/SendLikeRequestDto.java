package com.aksrua.like.controller.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendLikeRequestDto {

	private Long senderCardId;

	private Long receiverCardId;
}
