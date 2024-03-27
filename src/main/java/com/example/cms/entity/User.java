package com.example.cms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
//	@Column(name = "username")
//	@NotNull
	private String userName;
//	@NotBlank(message = "Invalid Email")
//	@NotNull(message="Invlaid Email")
//	@Column(unique = true , name = "useremail")
//	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",message = "Invlaid Email format")
//	@Schema(required = true)
	private String userEmail;
//	@NotBlank(message = "Invalid Password")
//	@NotNull(message="Invlaid Password")
//	@Size(min=8,max=20,message = "Password must be beyween 8 and 20 charecter")
//	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
//	message = "password must contain at least one letter,one number,one special charecter")
	private String password;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	private boolean deleted;
}
