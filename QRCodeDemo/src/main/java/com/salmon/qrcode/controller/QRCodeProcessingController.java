package com.salmon.qrcode.controller;

import com.google.zxing.WriterException;
import com.salmon.qrcode.service.QRCodeGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 二维码生成接口
 *
 * @author Salmon
 * @since 2024-05-25
 */
@RestController
public class QRCodeProcessingController {

    private final static Logger logger = LoggerFactory.getLogger(QRCodeProcessingController.class);

    @Resource
    private QRCodeGenerationService qrCodeGenerationService;

    @PostMapping("/generateQRCode")
    public ResponseEntity<String> generateQRCode(@RequestParam String content) {
        logger.info("receive request...");
        try {
            String qrCodeUrl = qrCodeGenerationService.saveQRCodeImageToTempFile(content);
            return new ResponseEntity<>(qrCodeUrl, HttpStatus.OK);
        } catch (IOException | WriterException e) {
            logger.error("error:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
