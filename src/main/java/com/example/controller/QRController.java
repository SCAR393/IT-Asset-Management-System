package com.example.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@RestController
public class QRController {

    @GetMapping(
        value="/qr/{assetId}",
        produces=MediaType.IMAGE_PNG_VALUE)

    public ResponseEntity<byte[]> generateQR(
            @PathVariable Long assetId)
            throws Exception {

        String url =
                "http://localhost:8080/asset-details/"
                + assetId;

        QRCodeWriter writer =
                new QRCodeWriter();

        BitMatrix matrix =
                writer.encode(
                        url,
                        BarcodeFormat.QR_CODE,
                        300,
                        300);

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                out);

        return ResponseEntity.ok(
                out.toByteArray());
    }
}