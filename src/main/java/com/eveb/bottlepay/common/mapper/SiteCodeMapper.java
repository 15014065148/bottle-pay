package com.eveb.bottlepay.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.SiteCode;

@Mapper
@Component
public interface SiteCodeMapper {

    SiteCode getSiteCodeInfo();

    void insetSiteUrlInfo(@Param("siteId") Integer siteId, @Param("siteCode") String siteCode, @Param("siteUrl") String siteUrl);
    
    List<Map<String, String>> getSiteUrlList(@Param("siteCodes") List<String> siteCodes);

    void updateSiteCodeInfo(@Param("id") Integer id, @Param("templateName") String templateName);
}
