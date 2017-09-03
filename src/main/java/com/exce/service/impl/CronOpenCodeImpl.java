package com.exce.service.impl;

import com.exce.dto.OpenCode;
import com.exce.dto.OpenCodeRawData;
import com.exce.model.OpenCodeBjpk10;
import com.exce.model.OpenCodeCqssc;
import com.exce.model.OpenCodeMlaft;
import com.exce.repository.OpenCodeBjpk10Repository;
import com.exce.repository.OpenCodeCqsscRepository;
import com.exce.repository.OpenCodeMlaftRepository;
import com.exce.service.CronOpenCode;
import com.exce.util.RestTemplateSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CronOpenCodeImpl implements CronOpenCode {

    private static Logger log = LoggerFactory.getLogger(CronOpenCodeImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${goldluck.setting.openCode.host}")
    private String host;
    @Value("${goldluck.setting.openCode.token}")
    private  String token;
    @Value("${goldluck.setting.openCode.autoUpdate}")
    private Boolean autoUpdate;
    @Autowired
    private OpenCodeBjpk10Repository openCodeBjpk10Repository;
    @Autowired
    private OpenCodeMlaftRepository openCodeMlaftRepository;
    @Autowired
    private OpenCodeCqsscRepository openCodeCqsscRepository;

    private RestTemplate restTemplate = RestTemplateSingleton.getInstance();

    @Override
    @Scheduled(fixedRate = 5000)
    public void getBjpk10() {
        if (!autoUpdate) {
            log.debug("不自動讀取開獎源");
            return;
        }
        String response = null;
        try {
            log.debug("Start Cron Bjpk10()");
            String url = host + "/newly.do?token={token}&code=bjpk10&format=json";
            restTemplate = new RestTemplate();
            response = restTemplate.getForObject(url, String.class, token);
            OpenCode openCode = objectMapper.readValue(response, OpenCode.class);
            if (CollectionUtils.isNotEmpty(openCode.getData())) {
                List<OpenCodeBjpk10> openCodeBjpk10s = Lists.newArrayList(
                        openCodeBjpk10Repository.findByExpectIn(openCode.getData().parallelStream().map(OpenCodeRawData::getExpect).collect(Collectors.toList()))
                );
                if (openCodeBjpk10s.size() == openCode.getRows()) {
                    log.debug("There is no new record need to insert!");
                } else {
                    openCode.getData().stream().forEach(openCodeRawData -> {
                        boolean exist = openCodeBjpk10s.parallelStream().anyMatch(openCodeBjpk10 -> openCodeBjpk10.getExpect().equals(openCodeRawData.getExpect()));
                        if (!exist) {
                            OpenCodeBjpk10 newRecord = new OpenCodeBjpk10();
                            newRecord.setExpect(openCodeRawData.getExpect());
                            newRecord.setOpenCode(openCodeRawData.getOpencode());
                            newRecord.setOpenTime(openCodeRawData.getOpentime());
                            newRecord.setOpenTimestamp(openCodeRawData.getOpentimestamp());
                            openCodeBjpk10Repository.save(newRecord);
                        }
                    });
                    //TODO 兌獎
                    //List<String> eee = Lists.newArrayList();
                    //eee.parallelStream().stream()
                }
            }
            log.debug("Finish Cron Bjpk10()");
        } catch (Exception e) {
            log.warn("Fail Cron Bjpk10()");
            log.warn("JSON: {}", response);
            log.warn(e.getMessage());
        }
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void getMlaft() {
        if (!autoUpdate) {
            log.debug("不自動讀取開獎源");
            return;
        }
        String response = null;
        try {
            log.debug("Start Cron Mlaft()");
            String url = host + "/newly.do?token={token}&code=mlaft&format=json";
            response = restTemplate.getForObject(url, String.class, token);
            OpenCode openCode = objectMapper.readValue(response, OpenCode.class);
            if (CollectionUtils.isNotEmpty(openCode.getData())) {
                List<OpenCodeMlaft> openCodeMlafts = Lists.newArrayList(
                        openCodeMlaftRepository.findByExpectIn(openCode.getData().parallelStream().map(OpenCodeRawData::getExpect).collect(Collectors.toList()))
                );
                if (openCodeMlafts.size() == openCode.getRows()) {
                    log.debug("There is no new record need to insert!");
                } else {
                    openCode.getData().stream().forEach(openCodeRawData -> {
                        boolean exist = openCodeMlafts.parallelStream().anyMatch(openCodeMlaft -> openCodeMlaft.getExpect().equals(openCodeRawData.getExpect()));
                        if (!exist) {
                            OpenCodeMlaft newRecord = new OpenCodeMlaft();
                            newRecord.setExpect(openCodeRawData.getExpect());
                            newRecord.setOpenCode(openCodeRawData.getOpencode());
                            newRecord.setOpenTime(openCodeRawData.getOpentime());
                            newRecord.setOpenTimestamp(openCodeRawData.getOpentimestamp());
                            openCodeMlaftRepository.save(newRecord);
                        }
                    });
                }
            }
            log.debug("Finish Cron Mlaft()");
        } catch (Exception e) {
            log.warn("Fail Cron Mlaft()");
            log.warn("JSON: {}", response);
            log.warn(e.getMessage());
        }
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void getCqssc() {
        if (!autoUpdate) {
            log.debug("不自動讀取開獎源");
            return;
        }
        String response = null;
        try {
            log.debug("Start Cron Cqssc()");
            String url = host + "/newly.do?token={token}&code=cqssc&format=json";
            response = restTemplate.getForObject(url, String.class, token);
            OpenCode openCode = objectMapper.readValue(response, OpenCode.class);
            if (CollectionUtils.isNotEmpty(openCode.getData())) {
                List<OpenCodeCqssc> openCodeCqsscs = Lists.newArrayList(
                        openCodeCqsscRepository.findByExpectIn(openCode.getData().parallelStream().map(OpenCodeRawData::getExpect).collect(Collectors.toList()))
                );
                if (openCodeCqsscs.size() == openCode.getRows()) {
                    log.debug("There is no new record need to insert!");
                } else {
                    openCode.getData().stream().forEach(openCodeRawData -> {
                        boolean exist = openCodeCqsscs.parallelStream().anyMatch(openCodeCqssc -> openCodeCqssc.getExpect().equals(openCodeRawData.getExpect()));
                        if (!exist) {
                            OpenCodeCqssc newRecord = new OpenCodeCqssc();
                            newRecord.setExpect(openCodeRawData.getExpect());
                            newRecord.setOpenCode(openCodeRawData.getOpencode());
                            newRecord.setOpenTime(openCodeRawData.getOpentime());
                            newRecord.setOpenTimestamp(openCodeRawData.getOpentimestamp());
                            openCodeCqsscRepository.save(newRecord);
                        }
                    });
                }
            }
            log.debug("Finish Cron Cqssc()");
        } catch (Exception e) {
            log.warn("Fail Cron Cqssc()");
            log.warn("JSON: {}", response);
            log.warn(e.getMessage());
        }
    }
}
