package com.fanap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String mainTitle;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @Version
    private int version;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", mainTitle='" + mainTitle + '\'' +
                '}';
    }
}
