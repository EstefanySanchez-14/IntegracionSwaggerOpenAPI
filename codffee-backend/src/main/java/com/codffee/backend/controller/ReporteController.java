package com.codffee.backend.controller;

import com.codffee.backend.entity.EstadoPedido;
import com.codffee.backend.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Generacion de reportes PDF para administradores")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/pedidos/pdf")
    @Operation(summary = "Generar reporte PDF de pedidos", description = "Descarga un PDF con el resumen general de pedidos. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF generado", content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<byte[]> generarReportePedidosPdf() {
        byte[] pdf = reporteService.generarReportePedidosPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-pedidos-codffee.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/pedidos/pdf/filtrado")
    @Operation(summary = "Generar reporte PDF filtrado", description = "Descarga un PDF de pedidos filtrado por rango de fechas y, opcionalmente, por estado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF generado", content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Parametros de fecha o estado invalidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<byte[]> generarReportePedidosFiltradoPdf(
            @Parameter(description = "Fecha inicial del reporte en formato ISO", example = "2026-06-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha final del reporte en formato ISO", example = "2026-06-30") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @Parameter(description = "Estado opcional del pedido", example = "ENTREGADO") @RequestParam(required = false) EstadoPedido estado
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