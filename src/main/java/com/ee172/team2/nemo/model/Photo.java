package com.ee172.team2.nemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weddingPhoto")
public class Photo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoId;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Column(name = "guest_name", length = 10)
    private String guestName;

    @Column(name = "guestText", length = 20)
    private String guestText;
    
    @Column(name = "weddingid", length = 255)
    private String weddingId;

    // Constructors, getters and setters
}
