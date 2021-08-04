package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.DTO.ImageDetailsDTO;
import com.quockhanh.commercemanager.model.ImageDetail;

public class ImageDetailsConverter {

    public ImageDetailsDTO toDto(ImageDetail imageDetail) {
        ImageDetailsDTO dto = new ImageDetailsDTO();
        dto.setImageId(imageDetail.getImageid());
        dto.setImage(imageDetail.getImage());
        return dto;
    }

    public ImageDetail toEntity(ImageDetailsDTO dto) {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setImageid(dto.getImageId());
        imageDetail.setImage(dto.getImage());
        return imageDetail;
    }
}
