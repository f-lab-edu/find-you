package com.aksrua.card.data.repository;


import com.aksrua.card.data.repository.dto.response.CardResponseDto;
import java.util.List;

public interface CardRepositoryCustom {

	List<CardResponseDto> findCardsByUserFilter(Long userId);
}
