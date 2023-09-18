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
// USE FOR FILE NAME CHECK
//    public boolean isNoteExist(Integer noteId){
//        return noteMapper.getAllNotes()
//                .stream().anyMatch(n -> n.getNoteId().equals(noteId));
//    }
    public int createNote(Note note){
        return noteMapper.createNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }
}
