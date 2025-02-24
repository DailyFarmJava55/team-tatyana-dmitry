package telran.accounting.model;

import jakarta.persistence.Entity;

@Entity
public enum Role {
    ADMINISTRATOR,
    USER,
    FARMER
}