package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController {
    HashMap<Long, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film updateFilm) {
        Film oldFilm = films.get(updateFilm.getId());
        oldFilm.setDescription(updateFilm.getDescription());
        oldFilm.setName(updateFilm.getName());
        oldFilm.setReleaseDate(updateFilm.getReleaseDate());
        oldFilm.setDuration(updateFilm.getDuration());

        return oldFilm;
    }

    @GetMapping
    public Collection<Film> listFilms() {
        return films.values();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
