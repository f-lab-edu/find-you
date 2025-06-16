package com.aksrua.interaction.data.entity;

import com.aksrua.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
@Entity
public class Like extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender_card_id")
	private Long senderCardId;

	@Column(name = "receiver_card_id")
	private Long receiverCardId;

	@Column(nullable = false)
	private LikeStatus likeStatus;

	// ENUM 중 [SENT]로 보내지는 시점
	private LocalDateTime registeredAt;

	// ENUM 중 [CHECKED]로 보내지는 시점
	private LocalDateTime checkedAt;

	// ENUM 중 [MATCHED]로 보내지는 시점
	private LocalDateTime matchedAt;

	public Like(Long senderCardId, Long receiverCardId, LikeStatus likeStatus, LocalDateTime registeredAt,
				LocalDateTime checkedAt, LocalDateTime matchedAt) {
		this.senderCardId = senderCardId;
		this.receiverCardId = receiverCardId;
		this.likeStatus = likeStatus;
		this.registeredAt = registeredAt;
		this.checkedAt = checkedAt;
		this.matchedAt = matchedAt;
	}

	public static Like createSentLike(Long senderCardId, Long receiverCardId) {
		return Like.builder()
				.senderCardId(senderCardId)
				.receiverCardId(receiverCardId)
				.registeredAt(LocalDateTime.now())
				.likeStatus(LikeStatus.SENT).build();
	}

	public void changeLikeStatusToMatched () {
		this.likeStatus = LikeStatus.MATCHED;
		this.matchedAt = LocalDateTime.now();
	}

	public void validateRemovalPermission(Long userId) {
		if (!isOwnedBy(userId)) {
			throw new IllegalArgumentException("다른 사용자의 좋아요는 삭제할 수 없습니다.");
		}

		if (isMatched()) {
			throw new IllegalArgumentException("이미 매칭이 된 상대입니다. 대화방에서 나가기를 눌러주세요.");
		}
	}

	private boolean isMatched() {
		return LikeStatus.MATCHED.equals(this.likeStatus);
	}

	private boolean isOwnedBy(Long userId) {
		return this.senderCardId.equals(userId);
	}
}
