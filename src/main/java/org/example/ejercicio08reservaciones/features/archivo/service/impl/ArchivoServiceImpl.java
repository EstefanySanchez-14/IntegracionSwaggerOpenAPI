package org.example.ejercicio08reservaciones.features.archivo.service.impl;

import org.example.ejercicio08reservaciones.core.domain.Archivo;
import org.example.ejercicio08reservaciones.features.archivo.repository.ArchivoRepository;
import org.example.ejercicio08reservaciones.features.archivo.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class ArchivoServiceImpl implements ArchivoService {
    @Autowired
    private ArchivoRepository repository;
    @Override
    public Archivo guardarArchivoEnBaseDeDatos(MultipartFile datosArchivo) throws IOException {
        String nombreArchivo = StringUtils.cleanPath(datosArchivo.getOriginalFilename());
        Archivo archivo = Archivo.builder().
                nombreArchivo(nombreArchivo).
                tipoArchivo(datosArchivo.getContentType()).
                datosArchivo(datosArchivo.getBytes()).
                build();
        return repository.save(archivo);
    }

    @Override
    public Optional<Archivo> descargarArchivo(Long id) throws FileNotFoundException {
        Optional<Archivo> archivo = repository.findById(id);
        if (archivo.isPresent()) {
            return archivo;
        }
        throw new FileNotFoundException("Archivo no encontrado");
    }
}
