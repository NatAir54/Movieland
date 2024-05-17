package com.nataliia.koval.movieland.web.controller.entity;

import java.util.List;

public record MovieAddRequest(String nameRussian, String nameNative, String yearOfRelease, String description,
                              double price, String picturePath, List<Integer> countries, List<Integer> genres) {
}
