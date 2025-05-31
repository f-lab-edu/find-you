package com.aksrua.card.data.repository;

import com.aksrua.card.data.repository.dto.response.CardListResponseDto;
import java.util.List;

public interface CardRepositoryCustom {

	List<CardListResponseDto> findCardsByUserFilter(Long userId);
}
