package pe.edu.cibertec.patitas_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.service.AuthService;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class authServiceIMP implements AuthService {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {

        String[] datosUsuario = null;
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {

            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");
                if (loginRequestDTO.tipoDocumento().equals(datos[0]) &&
                        loginRequestDTO.numeroDocumento().equals(datos[1]) &&
                        loginRequestDTO.password().equals(datos[2])) {

                    datosUsuario = new String[2];
                    datosUsuario[0] = datos[3]; // Recuperar nombre
                    datosUsuario[1] = datos[4]; // Recuperar email

                }

            }

        } catch (IOException e) {
            datosUsuario = null;
            throw new IOException(e);
        }

        return datosUsuario;
    }

    @Override
    public void registrarLogout(LogoutRequestDTO logoutRequestDTO) throws IOException {

        // Formato del registro de logout
        String registro = String.format("Tipo Documento: %s, Número: %s, Fecha y Hora: %s%n",
                logoutRequestDTO.tipoDocumento(),
                logoutRequestDTO.numeroDocumento(),
                LocalDateTime.now());

        // Rutas absolutas para evitar problemas con la creación del archivo
        String rutaArchivo = "src/main/resources/logout_registros.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            writer.write(registro);
            System.out.println("Logout registrado exitosamente: " + registro);  // Imprimir en consola
        } catch (IOException e) {
            System.out.println("Error al registrar logout: " + e.getMessage());
            throw new IOException(e);
        }
    }


}
