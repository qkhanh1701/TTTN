package com.quockhanh.commercemanager.services;

import com.quockhanh.commercemanager.model.ImageDetail;

import java.util.List;

public interface ImageDetailsService {

    public ImageDetail save(ImageDetail imageDetail);
    public void saveListImageDetails(List<ImageDetail> imageDetails);
    public void delete(Long imageId);
}
