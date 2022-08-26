package it.polimi.inginf.childcaretech.data.formData;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class FormSelection implements Serializable {
    private int value;
    private String label;

    public FormSelection(int id, String name, String surname, String cf){
        this.value = id;
        this.label = name + " " + surname + " ("+ cf + ")";
    }

    public FormSelection(int id, String name) {
        this.value = id;
        this.label = name;
    }
}
