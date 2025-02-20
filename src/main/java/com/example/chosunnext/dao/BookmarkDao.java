package com.example.chosunnext.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookmarkDao {


    boolean isBookmarked(int newsId, String userId);

    void saveBookmark(int newsId, String userId);

    boolean deleteBookmark(int newsId, String userId);
}
