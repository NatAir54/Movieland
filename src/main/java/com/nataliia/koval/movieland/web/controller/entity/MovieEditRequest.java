package com.nataliia.koval.movieland.web.controller.entity;

import java.util.List;

public record MovieEditRequest(String nameRussian, String nameNative, String picturePath, List<Integer> countries, List<Integer> genres) {
}
