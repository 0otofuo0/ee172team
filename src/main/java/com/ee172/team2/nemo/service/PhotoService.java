package com.ee172.team2.nemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ee172.team2.nemo.model.Photo;
import com.ee172.team2.nemo.repository.PhotoRepository;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // 保存照片信息到數據庫
    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    // 獲取所有照片信息
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    // 根據ID獲取單個照片信息
    public Photo getPhotoById(Integer id) {
        return photoRepository.findById(id).orElse(null);
    }

    // 根據ID刪除照片
    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }
    
    public Photo addNewPhoto(Photo newPhoto) {
        // 在這裡可以添加任何必要的邏輯，例如數據驗證或轉換
        // 然後保存新照片到數據庫
        return photoRepository.save(newPhoto);
    }

  
}
