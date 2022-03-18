package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.QnaNoticeRequestDto;
import com.ssafy.escapesvr.dto.QnaNoticeResponseDto;
import com.ssafy.escapesvr.entity.QnaNotice;
import com.ssafy.escapesvr.repository.QnaNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class QnaNoticeServiceImpl implements QnaNoticeService{

    private final QnaNoticeRepository qnaNoticeRepository;


    //게시글 작성
    @Override
    public void insertQnaNotice(QnaNoticeRequestDto qnaNoticeRequestDto) throws Exception {

        QnaNotice qnaNotice = new QnaNotice();
        qnaNotice.setId(qnaNoticeRequestDto.getId());
        qnaNotice.setTitle(qnaNoticeRequestDto.getTitle());
        qnaNotice.setContent(qnaNoticeRequestDto.getContent());
        qnaNotice.setIsNotice(qnaNoticeRequestDto.getIsNotice());
        qnaNotice.setIsSecret(qnaNoticeRequestDto.getIsSecret());
        qnaNotice.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qnaNotice.setUserId(qnaNoticeRequestDto.getUserId());

        qnaNoticeRepository.save(qnaNotice);

    }

    //게시글 수정
    @Override
    public void updateQnaNotice(QnaNoticeRequestDto qnaNoticeRequestDto) {

        QnaNotice qnaNotice = qnaNoticeRepository.getById(qnaNoticeRequestDto.getId());

        qnaNotice.setTitle(qnaNoticeRequestDto.getTitle());
        qnaNotice.setContent(qnaNoticeRequestDto.getContent());
        qnaNotice.setIsNotice(qnaNoticeRequestDto.getIsNotice());
        qnaNotice.setIsSecret(qnaNoticeRequestDto.getIsSecret());
        qnaNotice.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qnaNotice.setUserId(qnaNoticeRequestDto.getUserId());

        qnaNoticeRepository.save(qnaNotice);

    }

    //게시글 삭제
    @Override
    public void deleteQnaNotice(Long qnaId) {
        qnaNoticeRepository.deleteById(qnaId);
    }


    //게시글 조회
    @Override
    public List<QnaNoticeResponseDto> getQnaNoticeList() {
        //공지글이 앞으로 오도록 내림차순 정렬. + 시간순 정렬.
        List<QnaNotice> qnaNotices = qnaNoticeRepository.findAll(Sort.by(Sort.Direction.DESC, "isNotice","createdAt"));
        return qnaNotices.stream().map(QnaNoticeResponseDto::new).collect(Collectors.toList());
    }

}
