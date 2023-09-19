package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();
    @Select("SELECT * FROM NOTES WHERE userId = #{userId} ")
    List<Note> getAllNotesForUser(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
//    WHERE userId = #{userId}
    int createNote(Note note);

    @Update("UPDATE NOTES SET (noteTitle, noteDescription) VALUES (#{noteTitle}, #{noteDescription}) WHERE noteId = #{noteId}")
    Note updateNote(Integer noteId, String noteTitle, String noteDescription);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
}
