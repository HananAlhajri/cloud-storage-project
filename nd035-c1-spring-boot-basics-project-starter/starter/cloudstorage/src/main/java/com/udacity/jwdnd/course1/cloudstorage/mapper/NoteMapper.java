package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    Note getNotes();

    @Insert("INSERT INTO NOTES (title, description) VALUES (#{title}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void createNote(Note note);

    @Update("UPDATE NOTES SET title = #{title} WHERE description = #{description}")
    Note updateUser(String title, String description);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteUser(Long noteId);
}
