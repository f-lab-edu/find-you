package com.aksrua.filter.service;

import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FilterService {

	private final FilterRepository filterRepository;

	public void createFilter(Filter filter) {
		filterRepository.save(filter);
	}
}
