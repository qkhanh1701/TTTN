package com.quockhanh.commercemanager.repository;

import com.quockhanh.commercemanager.model.ImageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageDetailRepository extends JpaRepository<ImageDetail, Long> {
    List<ImageDetail> findImageDetailByImageid(Long imageId);

    @Transactional
    @Modifying
    @Query(value = "delete from ImageDetail i where i.imageid = :id")
    void deleteAllByImageid(Long id);
}
