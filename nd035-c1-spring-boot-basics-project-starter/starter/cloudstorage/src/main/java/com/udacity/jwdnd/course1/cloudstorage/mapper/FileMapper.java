package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES " +
            "(#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.userId}, #{file.fileData})")
    Integer save(@Param("file") Files file);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<Files> findFilesByUserId(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteById( Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files findById( Integer fileId);
}
