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
        Integer bookmarked = bookmarkDto.getBookmarked();

        //1이면 북마크 0은 북마크 해제
        if (bookmarked != null && bookmarked == 1) {
            bookmarkDto.setBookmarkId(0);
            bookmarkDao.updateBookMark(bookmarkDto);
            return false;
        } else {
            bookmarkDto.setBookmarked(1);
            bookmarkDao.insertBookMark(bookmarkDto);
            return true;
        }
    }




}
