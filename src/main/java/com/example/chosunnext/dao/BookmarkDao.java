package com.example.chosunnext.dao;

import com.example.chosunnext.dto.BookmarkDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookmarkDao {
    int insertBookMark(BookmarkDto bookmarkDto);
}
