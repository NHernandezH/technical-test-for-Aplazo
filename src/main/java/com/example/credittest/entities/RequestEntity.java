package com.example.credittest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request")
@Getter @Setter
public class RequestEntity {

    public RequestEntity(Date requestDate, String requestBody, String requestURI,ResponseEntity response) {
        this.requestDate = requestDate;
        this.requestBody = requestBody;
        this.requestUri = requestUri;
        this.response = response;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Date requestDate;
    @Lob
    @Column
    private String requestBody;
    @Column
    private String requestUri;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private ResponseEntity response;
}
