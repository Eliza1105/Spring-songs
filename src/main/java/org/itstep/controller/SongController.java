package org.itstep.controller;

import org.itstep.model.Song;
import org.itstep.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SongController {

    private final SongService songService;
    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }


    @GetMapping("/songs")
    public String showSongs(Model model) {
        List<Song> songs = songService.findAll();
        songs.stream().forEach(System.out::println);
        model.addAttribute("songs", songs);
        return "songs";
    }
    @GetMapping("/add")
    public String addSong(@ModelAttribute("song") Song song){
return "addSongs";
    }
    @PostMapping("/songs")
    public String createSong(@ModelAttribute("song") Song song,
                             @RequestParam("audioFile")MultipartFile audioFile,
                             @RequestParam("photoFile")MultipartFile photoFile) throws IOException {
        System.out.println(song);
        if (!audioFile.isEmpty()) {
            File dir = new File("src/main/resources/static/songs");
            audioFile.transferTo(new File(dir.getAbsolutePath()+"/"+audioFile.getOriginalFilename()));
            song.setAudioFilePath(audioFile.getOriginalFilename());
        }

        if (!photoFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/album_photo");
            photoFile.transferTo(new File(dir.getAbsolutePath()+"/"+photoFile.getOriginalFilename()));
            song.setPhotoFilePath(photoFile.getOriginalFilename());
        }

//System.out.println(song);
        // Сохранение сущности Song в базу данных

        songService.save
                (song);
        return "redirect:songs";
    }
} 