package pe.edu.cibertec.patitas_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            System.out.println("Intentando iniciar sesión con: " + loginRequestDTO.tipoDocumento() + " - " + loginRequestDTO.numeroDocumento());

            String[] datosUsuario = authService.validarUsuario(loginRequestDTO);
            if (datosUsuario == null) {
                System.out.println("Usuario no encontrado.");
                return new LoginResponseDTO("01", "Error: Usuario no encontrado", "", "");
            }
            System.out.println("Inicio de sesión exitoso para usuario: " + datosUsuario[0]);
            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]);

        } catch (Exception e) {
            System.out.println("Error en el proceso de login: " + e.getMessage());
            return new LoginResponseDTO("99", "Error: Ocurrió un problema", "", "");
        }
    }

    @PostMapping("/logout")
    public LogoutResponseDTO logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        System.out.println("Procesando logout para: " + logoutRequestDTO.tipoDocumento() + " - " + logoutRequestDTO.numeroDocumento());

        try {
            authService.registrarLogout(logoutRequestDTO);
            System.out.println("Logout registrado correctamente.");
            return new LogoutResponseDTO("00", "Logout realizado correctamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el logout: " + e.getMessage());
            return new LogoutResponseDTO("99", "Error durante el logout");
        }
    }

}
