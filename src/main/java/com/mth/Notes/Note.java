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
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Note {

    private @Id
    @GeneratedValue
    Long id;
    private String note;
    private String status;

    public Note() {
    }

    public Note(String note, String status) {
        this.note = note;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }

        Note note = (Note) o;
        boolean b = false;
        if (Objects.equals(this.id, note.id) && Objects.equals(this.note, note.note) && Objects.equals(this.status, note.status)) {
            b = true;
        }

        return b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.note, this.status);
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", note=" + note + ", status=" + status + '}';
    }

}
