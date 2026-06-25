package org.example.ejercicio08reservaciones.features.archivo.service;

import org.example.ejercicio08reservaciones.core.domain.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface ArchivoService{
    //Almacenar archivos en la base de datos
    Archivo guardarArchivoEnBaseDeDatos(MultipartFile datosArchivo) throws IOException;

    Optional<Archivo> descargarArchivo(Long id) throws FileNotFoundException;
}
