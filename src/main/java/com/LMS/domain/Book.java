package com.LMS.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "book")
public class Book {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "We can't save this book while title empty")
    @NotNull(message = "Enter Title please")
    @Size(min = 2, max = 255, message = "title Limit")
    @Column(nullable = false, unique = true)
    private String title;

    @NotBlank(message = "We can't save this book while author name empty")
    @NotNull(message = "Enter Author Name please")
    @Size(min = 2, max = 255, message = "AuthorName Limit")
    @Column(nullable = false, unique = false)
    private String author;

    @NotBlank(message = "We can't save this book while publication date empty")
    @NotNull(message = "Enter publication date please")
    @Size(min = 2, max = 255, message = "title Limit")
    @Column(nullable = false, unique = false)
    private String publicationDate;


    @ManyToMany(mappedBy = "books", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Teacher> teachers = new ArrayList<>();


}
