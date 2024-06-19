package com.rumorify.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

    @Id
    private String token;

    @Transient
    private String prefix = "Bearer";

    @Column(nullable = false)
    private Long expirationDate;

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Check if the token is expired
     * 
     * @return true if the current date/time is after the expiration date of the
     *         token
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expirationDate;
    }
}