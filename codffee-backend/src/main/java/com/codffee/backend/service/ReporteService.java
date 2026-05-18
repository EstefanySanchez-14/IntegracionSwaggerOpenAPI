package com.codffee.backend.service;

import com.codffee.backend.entity.DetallePedido;
import com.codffee.backend.entity.EstadoPedido;
import com.codffee.backend.entity.Pedido;
import com.codffee.backend.repository.DetallePedidoRepository;
import com.codffee.backend.repository.PedidoRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public ReporteService(PedidoRepository pedidoRepository, DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public byte[] generarReportePedidosPdf() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return construirPdfPedidos(
                pedidos,
                "Codffee - Reporte General de Pedidos",
                "Sin filtros aplicados"
        );
    }

    public byte[] generarReportePedidosFiltradoPdf(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            EstadoPedido estado
    ) {
        List<Pedido> pedidos;

        if (estado != null) {
            pedidos = pedidoRepository.findByFechaHoraBetweenAndEstado(fechaInicio, fechaFin, estado);
        } else {
            pedidos = pedidoRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
        }

        String filtros = "Fecha inicio: " + fechaInicio +
                " | Fecha fin: " + fechaFin +
                " | Estado: " + (estado != null ? estado : "TODOS");

        return construirPdfPedidos(
                pedidos,
                "Codffee - Reporte Filtrado de Pedidos",
                filtros
        );
    }

    private byte[] construirPdfPedidos(List<Pedido> pedidos, String tituloReporte, String filtros) {
        try (
                PDDocument documento = new PDDocument();
                ByteArrayOutputStream salida = new ByteArrayOutputStream()
        ) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

            PDType1Font fuenteTitulo = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fuenteNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font fuenteNegrita = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            float margenIzquierdo = 50;
            float y = 750;
            float saltoLinea = 18;

            escribirTexto(contenido, fuenteTitulo, 18, margenIzquierdo, y, tituloReporte);
            y -= 25;

            escribirTexto(contenido, fuenteNormal, 10, margenIzquierdo, y, "Fecha de generacion: " + LocalDateTime.now());
            y -= saltoLinea;

            escribirTexto(contenido, fuenteNormal, 10, margenIzquierdo, y, "Filtros: " + filtros);
            y -= 30;

            BigDecimal totalGeneral = BigDecimal.ZERO;

            if (pedidos.isEmpty()) {
                escribirTexto(contenido, fuenteNegrita, 12, margenIzquierdo, y, "No se encontraron pedidos con los filtros seleccionados.");
                y -= saltoLinea;
            }

            for (Pedido pedido : pedidos) {
                if (y < 120) {
                    contenido.close();

                    pagina = new PDPage();
                    documento.addPage(pagina);
                    contenido = new PDPageContentStream(documento, pagina);
                    y = 750;
                }

                escribirTexto(contenido, fuenteNegrita, 12, margenIzquierdo, y, "Pedido #" + pedido.getId());
                y -= saltoLinea;

                escribirTexto(contenido, fuenteNormal, 10, margenIzquierdo, y,
                        "Cliente: " + pedido.getUsuario().getNombre() +
                                " | Estado: " + pedido.getEstado() +
                                " | Pago: " + pedido.getMetodoPago());
                y -= saltoLinea;

                escribirTexto(contenido, fuenteNormal, 10, margenIzquierdo, y,
                        "Fecha: " + pedido.getFechaHora() +
                                " | Total: $" + pedido.getTotal());
                y -= saltoLinea;

                List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(pedido.getId());

                for (DetallePedido detalle : detalles) {
                    if (y < 120) {
                        contenido.close();

                        pagina = new PDPage();
                        documento.addPage(pagina);
                        contenido = new PDPageContentStream(documento, pagina);
                        y = 750;
                    }

                    escribirTexto(contenido, fuenteNormal, 9, margenIzquierdo + 20, y,
                            "- " + detalle.getProducto().getNombre() +
                                    " | Cantidad: " + detalle.getCantidad() +
                                    " | Subtotal: $" + detalle.getSubtotal());
                    y -= saltoLinea;
                }

                totalGeneral = totalGeneral.add(pedido.getTotal());
                y -= 10;
            }

            if (y < 100) {
                contenido.close();

                pagina = new PDPage();
                documento.addPage(pagina);
                contenido = new PDPageContentStream(documento, pagina);
                y = 750;
            }

            y -= 10;
            escribirTexto(contenido, fuenteTitulo, 14, margenIzquierdo, y, "Total general vendido: $" + totalGeneral);
            y -= 20;
            escribirTexto(contenido, fuenteNegrita, 12, margenIzquierdo, y, "Total de pedidos encontrados: " + pedidos.size());

            contenido.close();

            documento.save(salida);
            return salida.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el reporte PDF: " + e.getMessage());
        }
    }

    private void escribirTexto(
            PDPageContentStream contenido,
            PDType1Font fuente,
            int tamanio,
            float x,
            float y,
            String texto
    ) throws IOException {
        contenido.beginText();
        contenido.setFont(fuente, tamanio);
        contenido.newLineAtOffset(x, y);
        contenido.showText(limpiarTexto(texto));
        contenido.endText();
    }

    private String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("ñ", "n")
                .replace("Ñ", "N");
    }
}