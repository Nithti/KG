package com.warmer.web.service;

import com.warmer.web.entity.location;

import java.util.List;

public interface UserService {
    List<Long> getYjSize();

    List<List<String>> getLocation(String id);
    List<Float> getWaterLevel(String rescd);
}
