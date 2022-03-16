package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.entity.ThemeReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeReviewRepo extends JpaRepository<ThemeReview,Integer> {
    List<ThemeReview> findByTheme(Theme theme,Pageable pageable);

}
