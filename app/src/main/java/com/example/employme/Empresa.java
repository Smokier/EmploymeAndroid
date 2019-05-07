package com.example.employme;

public class Empresa {

    private String nom_emp;
    private String usu_emp;
    private String id_emp;
    private String email_emp;
    private String psw_emp;
    private String sitio_pemp;
    private String numtel_pemp;
    private String foto_emp;

    public Empresa ()
    {

    }

    public Empresa(String nom_emp, String usu_emp, String email_emp, String foto_emp,String id_emp) {
        this.nom_emp = nom_emp;
        this.usu_emp = usu_emp;
        this.email_emp = email_emp;
        this.foto_emp = foto_emp;
        this.id_emp=id_emp;
    }

    public String getNom_emp() {
        return nom_emp;
    }

    public void setNom_emp(String nom_emp) {
        this.nom_emp = nom_emp;
    }

    public String getEmail_emp() {
        return email_emp;
    }

    public void setEmail_emp(String email_emp) {
        this.email_emp = email_emp;
    }

    public String getPsw_emp() {
        return psw_emp;
    }

    public void setPsw_emp(String psw_emp) {
        this.psw_emp = psw_emp;
    }

    public String getSitio_pemp() {
        return sitio_pemp;
    }

    public void setSitio_pemp(String sitio_pemp) {
        this.sitio_pemp = sitio_pemp;
    }

    public String getNumtel_pemp() {
        return numtel_pemp;
    }

    public void setNumtel_pemp(String numtel_pemp) {
        this.numtel_pemp = numtel_pemp;
    }

    public String getUsu_emp() {
        return usu_emp;
    }

    public void setUsu_emp(String usu_emp) {
        this.usu_emp = usu_emp;
    }

    public String getFoto_emp() {
        return foto_emp;
    }

    public void setFoto_emp(String foto_emp) {
        this.foto_emp = foto_emp;
    }

    public String getId_emp() {
        return id_emp;
    }

    public void setId_emp(String id_emp) {
        this.id_emp = id_emp;
    }
}
