package com.example.credittest.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "response")
public class ResponseEntity {

    public ResponseEntity(String responseBody, Integer responseStatus) {
        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column
    private String responseBody;
    @Column
    private Integer responseStatus;
    @OneToOne(mappedBy = "response")
    private RequestEntity request;
}
