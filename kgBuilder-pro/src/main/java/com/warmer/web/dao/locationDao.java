package com.warmer.web.dao;
import com.warmer.web.entity.Waterlevel;
import com.warmer.web.entity.location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface locationDao {
   List<location> selectById(String id);
   String selectStcd(String rescd);
   List<Waterlevel> selectLevel(String st_cd);
}
