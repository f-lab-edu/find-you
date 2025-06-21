package com.aksrua.common.payload;

import com.aksrua.common.EventPayload;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeEventPayload implements EventPayload {

	private Long likeId;

	private Long senderCardId;

	private Long receiverCardId;

	private String likeStatus;

	private LocalDateTime registeredAt;

	private LocalDateTime checkedAt;

	private LocalDateTime matchedAt;

	private LocalDateTime createdAt;
}
