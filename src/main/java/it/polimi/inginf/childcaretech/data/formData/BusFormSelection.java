package it.polimi.inginf.childcaretech.data.formData;

import lombok.Getter;

@Getter
public class BusFormSelection{
    private String value;
    private String label;

    public BusFormSelection(String licensePlate) {
        this.value = licensePlate;
        this.label = licensePlate;
    }
}
