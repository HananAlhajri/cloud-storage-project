package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;

    public int createNote(Note note){
        return noteMapper.create(note);
    }

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotesForUser(userId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }


    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }
}
