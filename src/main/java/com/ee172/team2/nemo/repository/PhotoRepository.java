package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
