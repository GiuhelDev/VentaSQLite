/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author giuse
 */
public class usuarios {
    
    private int idusuario;
    private String usuario;
    private String password;
    private String correouser;
    private String tipouser;

    public usuarios() {
    }

    public usuarios(int idusuario, String usuario, String password, String correouser, String tipouser) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.password = password;
        this.correouser = correouser;
        this.tipouser = tipouser;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreouser() {
        return correouser;
    }

    public void setCorreouser(String correouser) {
        this.correouser = correouser;
    }

    public String getTipouser() {
        return tipouser;
    }

    public void setTipouser(String tipouser) {
        this.tipouser = tipouser;
    }
    
    
    
}
