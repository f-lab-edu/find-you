package com.aksrua.notification.controller;

import com.aksrua.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController {

	private final NotificationService notificationService;


}
