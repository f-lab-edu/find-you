package com.aksrua.event;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentEvent {

	private Long senderCardId;

	private Long receiverCardId;

	private String content;

	private LocalDateTime sentTime;
}
