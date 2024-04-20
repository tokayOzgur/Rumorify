package com.rumorify.ws.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String token;

    @Transient
    private String prefix = "Bearer";

    @Column(nullable = false)
    private OffsetDateTime expirationDate;

    @Column(nullable = false)
    private boolean isActive = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * Check if the token is expired
     * 
     * @return true if the current date/time is after the expiration date of the
     *         token
     */
    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(expirationDate);
    }

}
