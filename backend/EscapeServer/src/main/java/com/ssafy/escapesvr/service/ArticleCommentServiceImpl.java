package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.ArticleCommentRequestDto;
import com.ssafy.escapesvr.dto.ArticleCommentResponseDto;
import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.ArticleComment;
import com.ssafy.escapesvr.repository.ArticleCommentRepository;
import com.ssafy.escapesvr.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;


    //댓글 조회
    @Override
    public List<ArticleCommentResponseDto> getArticleCommentList(Long articleId) {
        Article article = articleRepository.getById(articleId);
        List<ArticleComment> comments = articleCommentRepository.findByArticle(article);
        return comments.stream().map(ArticleCommentResponseDto::new).collect(Collectors.toList());
    }

    //댓글 작성
    @Override
    public void insertArticleComment(ArticleCommentRequestDto articleCommentRequestDto) {
        Article article = articleRepository.getById(articleCommentRequestDto.getArticleId());

        ArticleComment articleComment = new ArticleComment();
        articleComment.setContent(articleCommentRequestDto.getContent());
        articleComment.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        articleComment.setArticle(article);
        articleComment.setUserId(articleCommentRequestDto.getUserId());
        articleCommentRepository.save(articleComment);
    }

    //댓글 수정
    @Override
    public void updateArticleComment(ArticleCommentRequestDto articleCommentRequestDto) {
        ArticleComment articleComment = articleCommentRepository.getById(articleCommentRequestDto.getId());
        articleComment.setContent(articleCommentRequestDto.getContent());
        articleComment.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        articleCommentRepository.save(articleComment);

    }

    //댓글 삭제
    @Override
    public void deleteArticleComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }







}
