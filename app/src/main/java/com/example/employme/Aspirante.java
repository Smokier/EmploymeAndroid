package com.example.employme;

public class Aspirante {

    private String nom_asp;
    private String id_asp;
    private String email_asp;
    private String psw_asp;
    private String fn_asp;
    private String usu_asp;
    private String sex_asp;
    private String numtel_asp;
    private String foto_asp;
    private String status;
    private String vyt_pasp;

    public Aspirante ()
    {

    }

    public Aspirante(String nom_asp, String email_asp, String fn_asp, String foto_asp,String id_asp) {
        this.nom_asp = nom_asp;
        this.email_asp = email_asp;
        this.fn_asp = fn_asp;
        this.foto_asp = foto_asp;
        this.id_asp=id_asp;
    }






    public String getNom_asp() {
        return nom_asp;
    }

    public void setNom_asp(String nom_asp) {
        this.nom_asp = nom_asp;
    }

    public String getEmail_asp() {
        return email_asp;
    }

    public void setEmail_asp(String email_asp) {
        this.email_asp = email_asp;
    }

    public String getPsw_asp() {
        return psw_asp;
    }

    public void setPsw_asp(String psw_asp) {
        this.psw_asp = psw_asp;
    }

    public String getFn_asp() {
        return fn_asp;
    }

    public void setFn_asp(String fn_asp) {
        this.fn_asp = fn_asp;
    }

    public String getUsu_asp() {
        return usu_asp;
    }

    public void setUsu_asp(String usu_asp) {
        this.usu_asp = usu_asp;
    }

    public String getSex_asp() {
        return sex_asp;
    }

    public void setSex_asp(String sex_asp) {
        this.sex_asp = sex_asp;
    }

    public String getNumtel_asp() {
        return numtel_asp;
    }

    public void setNumtel_asp(String numtel_asp) {
        this.numtel_asp = numtel_asp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto_asp() {
        return foto_asp;
    }

    public void setFoto_asp(String foto_asp) {
        this.foto_asp = foto_asp;
    }

    public String getId_asp() {
        return id_asp;
    }

    public void setId_asp(String id_asp) {
        this.id_asp = id_asp;
    }

    public String getVyt_pasp() {
        return vyt_pasp;
    }

    public void setVyt_pasp(String vyt_pasp) {
        this.vyt_pasp = vyt_pasp;
    }
}
