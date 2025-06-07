package com.aksrua.filter.service;

import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.repository.FilterRepository;
import com.aksrua.filter.dto.request.UpdateFilterRequestDto;
import com.aksrua.user.data.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterService {

	private final FilterRepository filterRepository;

	public Filter createFilter(Filter filter) {
		return filterRepository.save(filter);
	}

	public Filter getFilterDetail(Long userId) {
		return filterRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("필터가 존재하지 않습니다."));
	}

	/**
	 * @param
	 * @desc
	 * update logic 찾아보기
	 * created at 값이 null로 변경된다.
	 * update 전체 쿼리가 들어간다 왜그러지 ?
	 */
	@Transactional
	public Filter updateFilter(Long userId, Filter updateFilter) {
		Filter findFilter = filterRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("필터가 존재하지 않습니다."));

		findFilter.updateFilter(updateFilter);
		return filterRepository.save(findFilter);
	}
}
