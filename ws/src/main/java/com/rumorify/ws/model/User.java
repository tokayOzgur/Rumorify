
package com.rumorify.ws.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tokay
 *
 */
@Data
@Builder
@Entity
@Table(name = "Kullanicilar", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "email" }),
		@UniqueConstraint(columnNames = { "username" }) })
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean active;
	private boolean isDeleted;
	private String activationToken;
	private String passwordResetToken;
	@Lob
	private String image;
	private String profileDescription;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Token> tokens;
}
