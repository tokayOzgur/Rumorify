package com.rumorify.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    @Id
    private String token;

    @Transient
    private String prefix = "Bearer";

    @JsonIgnore
    @ManyToOne
    private User user;

    //TODO: ip address and user location
}
