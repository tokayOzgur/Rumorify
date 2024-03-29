
package com.rumorify.ws.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	private String activationToken;
	@Lob
	private String image;
	private String profileDescription;
}
