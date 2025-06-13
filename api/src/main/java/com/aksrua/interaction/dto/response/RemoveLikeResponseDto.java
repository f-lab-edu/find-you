package com.aksrua.interaction.dto.response;

import com.aksrua.interaction.data.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoveLikeResponseDto {
	Long id;
	String message;

	public RemoveLikeResponseDto fromEntity(Long likeId) {
		return RemoveLikeResponseDto.builder()
				.id(likeId)
				.message("삭제가 완료 되었습니다.")
				.build();
	}
}
