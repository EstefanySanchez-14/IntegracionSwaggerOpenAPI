package com.codffee.backend.controller;

import com.codffee.backend.entity.EstadoPedido;
import com.codffee.backend.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/pedidos/pdf")
    public ResponseEntity<byte[]> generarReportePedidosPdf() {
        byte[] pdf = reporteService.generarReportePedidosPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-pedidos-codffee.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/pedidos/pdf/filtrado")
    public ResponseEntity<byte[]> generarReportePedidosFiltradoPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) EstadoPedido estado
    ) {
        byte[] pdf = reporteService.generarReportePedidosFiltradoPdf(
                fechaInicio.atStartOfDay(),
                fechaFin.atTime(23, 59, 59),
                estado
        );

        String nombreArchivo = "reporte-pedidos-filtrado-codffee.pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}