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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {

    @Override
    public EntityModel<Note> toModel(Note note) {
        return EntityModel.of(note,
                linkTo(methodOn(NoteController.class).one(note.getId())).withSelfRel(),
                linkTo(methodOn(NoteController.class).all()).withRel("notes"));
    }
}
