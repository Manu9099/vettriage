package com.clinica.triage_mascotas.controller;



import com.clinica.triage_mascotas.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/paciente/{id}")
    public ResponseEntity<byte[]> reportePaciente(@PathVariable Long id) {
        try {
            byte[] pdf = pdfService.generarReportePaciente(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.attachment().filename("reporte-paciente-" + id + ".pdf").build()
            );
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}