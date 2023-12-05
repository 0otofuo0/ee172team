package com.ee172.team2.nemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ee172.team2.nemo.model.Photo;
import com.ee172.team2.nemo.service.PhotoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/photos")
public class PhotoController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private PhotoService photoService;

	@GetMapping("/")
	public String initPage(ModelMap model) {
		Photo photo = new Photo();
		model.addAttribute("weddingphoto", photo);

		List<Photo> images = photoService.getAllPhotos();
		model.addAttribute("images", images);
		return "nemo/weddingPhoto";
	}

	@PostMapping("/image/upload")
	public String createProduct(Model model, HttpServletRequest request,
			final @RequestParam("image") MultipartFile file) {
		try {
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
			}
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			Photo photo = new Photo();
			photo.setPhoto(imageData);
			photoService.savePhoto(photo);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
		}

		return "redirect:/photos/";
	}

	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Integer id, HttpServletResponse response, Photo photo)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		photo = photoService.getPhotoById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif,image/");
		response.getOutputStream().write(photo.getPhoto());
		response.getOutputStream().close();
	}

	// 處理添加照片的請求，接收照片數據並保存
	@PostMapping
	public Photo addPhoto(@RequestBody Photo photo) {
		return photoService.savePhoto(photo);
	}

	// 處理獲取所有照片的請求
	@GetMapping
	public List<Photo> getAllPhotos() {
		return photoService.getAllPhotos();
	}

	// 處理根據ID獲取單個照片的請求
	@GetMapping("/{id}")
	public Photo getPhotoById(@PathVariable Integer id) {
		return photoService.getPhotoById(id);
	}

	// 處理根據ID刪除照片的請求
	@DeleteMapping("/{id}")
	public void deletePhoto(@PathVariable Integer id) {
		photoService.deletePhoto(id);
	}

	@PostMapping("/addPhoto")
	public Photo addNewPhoto(@RequestBody Photo photoDetails) {
		Photo newPhoto = new Photo();

		newPhoto.setPhoto(photoDetails.getPhoto());
		newPhoto.setGuestName(photoDetails.getGuestName());
		newPhoto.setGuestText(photoDetails.getGuestText());

		return photoService.savePhoto(newPhoto);
	}

}
