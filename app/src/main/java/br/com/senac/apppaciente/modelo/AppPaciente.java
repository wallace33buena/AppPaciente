package br.com.senac.apppaciente.modelo;

public class AppPaciente {
    private int id;
    private String nomepaciente;
    private String horariopaciente;
    private String medicopaciente;
    private String datapaciente;


    public AppPaciente(int id, String nomepaciente, String horariopaciente, String medicopaciente, String datapaciente) {
        this.id = id;
        this.nomepaciente = nomepaciente;
        this.horariopaciente = horariopaciente;
        this.medicopaciente = medicopaciente;
        this.datapaciente = datapaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getHorariopaciente() {
        return horariopaciente;
    }

    public void setHorariopaciente(String horariopaciente) {
        this.horariopaciente = horariopaciente;
    }

    public String getMedicopaciente() {
        return medicopaciente;
    }

    public void setMedicopaciente(String medicopaciente) {
        this.medicopaciente = medicopaciente;
    }

    public String getDatapaciente() {
        return datapaciente;
    }

    public void setDatapaciente(String datapaciente) {
        this.datapaciente = datapaciente;
    }
}
