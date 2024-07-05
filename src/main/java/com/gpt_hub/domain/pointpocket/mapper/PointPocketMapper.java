package com.gpt_hub.domain.pointpocket.mapper;

import com.gpt_hub.domain.pointpocket.dto.PointPocketResponse;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointPocketMapper {

    PointPocketMapper INSTANCE = Mappers.getMapper(PointPocketMapper.class);

    PointPocketResponse toPointPocketResponse(PointPocket pointPocket);
}
