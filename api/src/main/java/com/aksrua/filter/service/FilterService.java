package com.aksrua.filter.service;

import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.repository.FilterRepository;
import com.aksrua.filter.dto.request.UpdateFilterRequestDto;
import com.aksrua.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterService {

	private final FilterRepository filterRepository;

	public void createFilter(Filter filter) {
		filterRepository.save(filter);
	}

	public Filter getFilterDetail(Long userId) {
		return filterRepository.findByUserId(userId)
				.orElseThrow(IllegalArgumentException::new);
	}

	/**
	 * @param user
	 * @desc
	 * update logic 찾아보기
	 * created at 값이 null로 변경된다.
	 * update 전체 쿼리가 들어간다 왜그러지 ?
	 */
	public void updateFilter(User user, UpdateFilterRequestDto requestDto) {
		Filter findFilter = filterRepository.findByUserId(user.getId())
				.orElseThrow(IllegalArgumentException::new);

		findFilter = Filter.builder()
				.id(requestDto.getUserId())
				.minAge(requestDto.getMinAge())
				.maxAge(requestDto.getMaxAge())
				.minHeight(requestDto.getMinHeight())
				.maxHeight(requestDto.getMaxHeight())
				.bodyType(requestDto.getBodyType())
				.religion(requestDto.getReligion())
				.build();

		filterRepository.save(findFilter);
	}
}
