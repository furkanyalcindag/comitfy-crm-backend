package com.comitfy.healtie.commercial.repository;

import com.comitfy.healtie.commercial.entity.Highlights;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface HighlightsRepository extends BaseRepository<Highlights> {

    // Highlights findByActivatedAndUser(boolean isActive, User user);


    @Query("SELECT h FROM Highlights h" +
            " WHERE h.startDate <= ?1 AND h.endDate>=?1 and h.activated = true")
    Page<Highlights> getActivatedHighlightsOfDoctorsOnCurrentDate(Pageable pageable, LocalDateTime currentDate);

}
