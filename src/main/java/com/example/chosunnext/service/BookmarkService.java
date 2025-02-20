package com.example.chosunnext.service;

import com.example.chosunnext.dao.BookmarkDao;
import com.example.chosunnext.dto.BookmarkDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {

    final BookmarkDao bookmarkDao;

    public boolean saveBookmark(BookmarkDto bookmarkDto) {
        boolean isBookmarked = bookmarkDao.isBookmarked(bookmarkDto.getNewsId(), bookmarkDto.getUserId());

        if (isBookmarked) {
            bookmarkDao.deleteBookmark(bookmarkDto.getNewsId(), bookmarkDto.getUserId());
            return false; // 기존 북마크 삭제
        } else {
            bookmarkDao.saveBookmark(bookmarkDto.getNewsId(), bookmarkDto.getUserId());
            return true; // 새 북마크 저장
        }
    }

    public boolean isBookmarked(int newsId, String userId) {
        return bookmarkDao.isBookmarked(newsId, userId);
    }

    public boolean deleteBookmark(BookmarkDto bookmarkDto) {
        return bookmarkDao.deleteBookmark(bookmarkDto.getNewsId(), bookmarkDto.getUserId());
    }
}

