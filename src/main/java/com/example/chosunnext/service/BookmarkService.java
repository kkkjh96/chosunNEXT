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

    public void saveBookmark(BookmarkDto bookmarkDto) {
        bookmarkDao.insertBookMark(bookmarkDto);
    }
}
