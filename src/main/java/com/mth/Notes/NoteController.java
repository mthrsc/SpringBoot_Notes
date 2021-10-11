/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mth.Notes;

/**
 *
 * @author mth
 */
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    private final NoteRepository repository;
    private final NoteModelAssembler assembler;

    NoteController(NoteRepository repository, NoteModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/notes")
    CollectionModel<EntityModel<Note>> all() {

        List<EntityModel<Note>> notes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notes, linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @PostMapping("/notes")
    ResponseEntity<?> newNote(@RequestBody Note newNote) {
        EntityModel<Note> entityModel = assembler.toModel(repository.save(newNote));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }

    @GetMapping("/notes/{id}")
    EntityModel<Note> one(@PathVariable Long id) {

        Note note = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        return assembler.toModel(note);
    }

    @PutMapping("/notes/{id}")
    ResponseEntity<?> replaceNote(@RequestBody Note newNote, @PathVariable Long id) {
        Note updatedNote = repository.findById(id) //
                .map(note -> {
                    note.setNote(newNote.getNote());
                    note.setStatus(newNote.getStatus());
                    return repository.save(note);
                }) //
                .orElseGet(() -> {
                    newNote.setId(id);
                    return repository.save(newNote);
                });

        EntityModel<Note> entityModel = assembler.toModel(updatedNote);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/notes/{id}")
    ResponseEntity<?> deleteNote(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
