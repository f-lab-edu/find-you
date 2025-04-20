package com.aksrua.card.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@AutoConfigureMockMvc
@WebMvcTest(CardController.class)
class CardControllerTest {

	@Test
	public void test_test() {
		assertEquals(1, 1);
	}
}