package org.example.ejercicio08reservaciones.features.archivo.conroller;

import org.example.ejercicio08reservaciones.core.domain.Archivo;
import org.example.ejercicio08reservaciones.features.archivo.dto.RespuestaDTO;
import org.example.ejercicio08reservaciones.features.archivo.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/apiArchivos/v1/archivo")
public class ArchivoController {
    @Autowired
    private ArchivoService service;

    @PostMapping("/subirArchivo")
    public ResponseEntity<RespuestaDTO> subirArchivo(@RequestParam MultipartFile archivo) throws IOException {
        service.guardarArchivoEnBaseDeDatos(archivo);
        RespuestaDTO respuesta = new RespuestaDTO();
        respuesta.setMensaje("Archivo subido correctamente");
        return ResponseEntity.ok().body(respuesta);
    }

    @GetMapping("/descargarArchivo/{id}")
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable Long id) throws FileNotFoundException {
        Archivo archivo = service.descargarArchivo(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, archivo.getTipoArchivo())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attacgment; filename=\""
                        + archivo.getNombreArchivo() + "\""
                ).body(archivo.getDatosArchivo());
    }
}
