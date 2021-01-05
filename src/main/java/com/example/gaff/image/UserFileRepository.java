package com.example.gaff.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFileRepository extends JpaRepository<UserFiles, Long> {
    List<UserFiles> findUserFilesByUserId(Long userId);

    @Modifying
    @Query("delete from UserFiles as f where f.user.id = ?1 and f.modifiedFilename in(?2)")
    void deleteUserFilesByUserIdAndFileName(Long id, List<String> removeImages);

}
