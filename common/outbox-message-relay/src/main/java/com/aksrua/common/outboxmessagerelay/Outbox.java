package com.aksrua.common.outboxmessagerelay;

import com.aksrua.common.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbox")
@Entity
public class Outbox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long outboxId;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	private String payload;

	private LocalDateTime createdAt;

	public static Outbox create(EventType eventType, String payload) {
		Outbox outbox = new Outbox();
		outbox.eventType = eventType;
		outbox.payload = payload;
		outbox.createdAt = LocalDateTime.now();
		return outbox;
	}
}
