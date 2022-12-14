package com.example.aroundproject.service.impl;

import com.example.aroundproject.data.dao.ShortUrlDAO;
import com.example.aroundproject.data.dto.NaverUriDto;
import com.example.aroundproject.data.dto.ShortUrlResponseDto;
import com.example.aroundproject.data.entity.ShortUrlEntity;
import com.example.aroundproject.service.ShortUrlService;
import java.net.URI;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private final ShortUrlDAO shortUrlDAO;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO) {
        this.shortUrlDAO = shortUrlDAO;
    }

    @Override
    public ShortUrlResponseDto getShortUrl(String clientId, String clientSecret,
        String originalUrl) {

        LOGGER.info("[getShortUrl] request data : {}", originalUrl);
        ShortUrlEntity getShortUrlEntity = shortUrlDAO.getShortUrl(originalUrl);

        String orgUrl;
        String shortUrl;

//      Entity에 값이 없으면 네이버에 요청 + generate ( generate와 코드가 겹치기 떄문에 메서드를 따로 뺴는게 좋다
        if (getShortUrlEntity == null) {
            LOGGER.info("[getShortUrl] No Entity in Database.");
            ResponseEntity<NaverUriDto> responseEntity = requestShortUrl(clientId, clientSecret,
                originalUrl);

            orgUrl = responseEntity.getBody().getResult().getOrgUrl();
            shortUrl = responseEntity.getBody().getResult().getUrl();

        } else {
            orgUrl = getShortUrlEntity.getOrgUrl();
            shortUrl = getShortUrlEntity.getUrl();
        }

        ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(orgUrl, shortUrl);

        LOGGER.info("[getShortUrl] Response DTO : {}", shortUrlResponseDto);
        return shortUrlResponseDto;
    }

    @Override
    public ShortUrlResponseDto generateShortUrl(String clientId, String clientSecret,
        String originalUrl) {

        LOGGER.info("[generateShortUrl] request data : {}", originalUrl);

        ResponseEntity<NaverUriDto> responseEntity = requestShortUrl(clientId, clientSecret,
            originalUrl);

        // NaverUriDto으로부터 꺼내서 shortUrlEntity에 넣는다
        String orgUrl = responseEntity.getBody().getResult().getOrgUrl();
        String shortUrl = responseEntity.getBody().getResult().getUrl();
        String hash = responseEntity.getBody().getResult().getHash();

        ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
        shortUrlEntity.setOrgUrl(orgUrl);
        shortUrlEntity.setUrl(shortUrl);
        shortUrlEntity.setHash(hash);

        shortUrlDAO.saveShortUrl(shortUrlEntity);

        //이건 왜한거지
        ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(orgUrl, shortUrl);
        LOGGER.info("[generateShortUrl] Response DTO : {}", shortUrlResponseDto);
        return shortUrlResponseDto;
    }

    @Override
    public ShortUrlResponseDto updateShortUrl(String clientId, String clientSecret,
        String originalUrl) {
        return null;
    }

    @Override
    public void deleteShortUrl(String url) {
        if (url.contains("me2.do")) {
            LOGGER.info("[deleteShortUrl] Request Url is 'ShortUrl'.");
            deleteByShortUrl(url);
        } else {
            LOGGER.info("[deleteShortUrl] Request Url is 'OriginalUrl'.");
            deleteByOriginalUrl(url);
        }
    }

    private void deleteByShortUrl(String url) {
        LOGGER.info("[deleteByShortUrl] delete record");
        shortUrlDAO.deleteByShortUrl(url);
    }

    private void deleteByOriginalUrl(String url) {
        LOGGER.info("[deleteByOriginalUrl] delete record");
        shortUrlDAO.deleteByOriginalUrl(url);
    }


    // 요청 메서드
    private ResponseEntity<NaverUriDto> requestShortUrl(String clientId, String clientSecret,
        String originalUrl) {
        LOGGER.info("[requestShortUrl] client ID : ***, client Secret : ***, original URL : {}", originalUrl);

        URI uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path("/v1/util/shorturl")
            .queryParam("url", originalUrl)
            .encode()
            .build()
            .toUri();

        LOGGER.info("[requestShortUrl] set HTTP Request Header");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        //body말고 headers에만 값 전달
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.info("[requestShortUrl] request by restTemplate");
        ResponseEntity<NaverUriDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
            entity, NaverUriDto.class);

        LOGGER.info("[requestShortUrl] request has been successfully complete.");

        return responseEntity;
    }

}