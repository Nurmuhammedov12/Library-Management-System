package com.LMS.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "teacher")
public class Teacher {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "please Enter Name")
    @NotNull(message = "Please enter Name")
    @Size(min = 2,max = 24, message = "Do u really have name like ${validatedValue} :) ")
    @Column(nullable = false,length = 25)
    private String name;

    @NotNull(message = "please enter Valid Lastname")
    @NotBlank(message = "this can't be missed")
    @Size(min = 2, max = 25, message = "We didn't find lastName like ${ValidatedValue}")
    @Column(unique = false,nullable = false,length = 25)
    private String lastName;

    @NotNull(message = "Enter ur email")
    @NotBlank(message = "Enter e-mail")
    @Email(message = "Enter valid email")
    @Column(unique = true,nullable = false)
    private String email;

    @NotNull(message = "please enter Valid phoneNumber")
    @NotBlank(message = "this can't be missed")
    @Size(min = 2, max = 25, message = "We didn't find  phoneNumber like ${ValidatedValue}")
    @Column(unique = false,nullable = false,length = 25)
    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    private LocalDateTime registerDate = LocalDateTime.now();




    @ManyToMany
    @JoinTable(
            name = "teachers_books",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

}
