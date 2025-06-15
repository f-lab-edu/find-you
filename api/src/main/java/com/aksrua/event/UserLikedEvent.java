package com.aksrua.event;

import com.aksrua.interaction.data.entity.LikeStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikedEvent {

	private Long senderCardId;

	private Long receiverCardId;

	private LikeStatus likeStatus;

	private LocalDateTime registeredAt;

	private LocalDateTime checkedAt;

	private LocalDateTime matchedAt;
}
