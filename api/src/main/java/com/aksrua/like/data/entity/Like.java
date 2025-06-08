package com.aksrua.like.data.entity;

import com.aksrua.card.data.entity.Card;
import com.aksrua.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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

	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_card_id")
	private Card senderCard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_card_id")
	private Card receiverCard;
	*/
	@Column(name = "sender_card_id")
	private Long senderCardId;

	@Column(name = "receiver_card_id")
	private Long receiverCardId;

	private LocalDateTime registeredAt;

	private Status status;

	@Builder
	public Like(Long senderCardId, Long receiverCardId, LocalDateTime registeredAt, Status status) {
		this.senderCardId = senderCardId;
		this.receiverCardId = receiverCardId;
		this.registeredAt = registeredAt;
		this.status = status;
	}
}
