package pe.edu.cibertec.patitas_backend.service;

import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;

import java.io.IOException;

public interface AuthService {
    String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;
    void registrarLogout(LogoutRequestDTO logoutRequestDTO) throws IOException;
}
