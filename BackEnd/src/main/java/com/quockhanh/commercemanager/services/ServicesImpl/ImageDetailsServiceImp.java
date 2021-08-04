package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.model.ImageDetail;
import com.quockhanh.commercemanager.repository.ImageDetailRepository;
import com.quockhanh.commercemanager.services.ImageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageDetailsServiceImp implements ImageDetailsService {
    @Autowired
    ImageDetailRepository imageDetailRepository;

    @Override
    public ImageDetail save(ImageDetail imageDetail) {
        return imageDetailRepository.save(imageDetail);
    }

    @Override
    public void saveListImageDetails(List<ImageDetail> imageDetails) {
        imageDetails.stream().forEach(s -> {
            imageDetailRepository.save(s);
        });
    }

    @Override
    public void delete(Long imageId) {
        imageDetailRepository.deleteAllByImageid(imageId);
    }
}
