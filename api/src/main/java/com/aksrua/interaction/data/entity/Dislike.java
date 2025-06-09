package com.aksrua.interaction.data.entity;

import com.aksrua.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dislike extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender_card_id")
	private Long senderCardId;

	@Column(name = "receiver_card_id")
	private Long receiverCardId;

	private LocalDateTime registeredAt;

	@Builder
	public Dislike(Long senderCardId, Long receiverCardId, LocalDateTime registeredAt) {
		this.senderCardId = senderCardId;
		this.receiverCardId = receiverCardId;
		this.registeredAt = registeredAt;
	}
}
