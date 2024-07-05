package com.gpt_hub.domain.pointtransfer.mapper;

import com.gpt_hub.domain.pointtransfer.dto.PointTransferResponse;
import com.gpt_hub.domain.pointtransfer.entity.PointTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointTransferMapper {

    PointTransferMapper INSTANCE = Mappers.getMapper(PointTransferMapper.class);

    PointTransferResponse toPointTransferResponse(PointTransfer pointTransfer);
}
