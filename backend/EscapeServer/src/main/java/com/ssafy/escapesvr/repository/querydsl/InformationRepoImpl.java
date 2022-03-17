package com.ssafy.escapesvr.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import com.ssafy.escapesvr.entity.QStore;
import com.ssafy.escapesvr.entity.QTheme;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class InformationRepoImpl implements InformationRepo{

    private static final QTheme qTheme=QTheme.theme;
    private static final QStore qStore=QStore.store;

    private final JPAQueryFactory queryFactory;


    public InformationRepoImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ThemeResponseDto> findByConditions(String largeRegion, String smallRegion, String gerne, Integer maxNumber, Integer maxLevel, Integer maxTime, Integer isSingleplay, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ThemeResponseDto.class,qTheme.id,qStore.largeRegion,qStore.smallRegion,qTheme.genre,qTheme.name,qTheme.maxNumber,qTheme.level,qTheme.time))
                .from(qTheme)
                .join(qTheme.store,qStore)
                .where(qTheme.maxNumber.loe(maxNumber).and(qTheme.level.loe(maxLevel).and(qTheme.time.loe(maxTime))),likelargeRegion(largeRegion),likesmallRegion(smallRegion),likegerne(gerne),eqisSingle(isSingleplay))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression likelargeRegion(String largeRegion){
        if(StringUtils.isNullOrEmpty(largeRegion)){
            return null;
        }
        return qStore.largeRegion.like(largeRegion);
    }

    private BooleanExpression likesmallRegion(String smallRegion){
        if(StringUtils.isNullOrEmpty(smallRegion)){
            return null;
        }
        return qStore.smallRegion.like(smallRegion);
    }

    private BooleanExpression likegerne(String genre){
        if(StringUtils.isNullOrEmpty(genre)){
            return null;
        }
        return qTheme.genre.like(genre);
    }

    private BooleanExpression eqisSingle(Integer isSingleplay){
        if(isSingleplay==null){
            return null;
        }
        return qTheme.isSingleplay.eq(isSingleplay);
    }




}
