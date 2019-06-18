package br.com.senac.apppaciente.webservice;

public class Api {
    private static final String ROOT_URL = "https://wallacebuena.000webhostapp.com/apppaciente/v1/Api.php?apicall=";
    public static String URL_CREATE_APPPACIENTE = ROOT_URL + "createapppaciente";
    public static String URL_READ_APPPACIENTE = ROOT_URL + "getapppaciente";
    public static String URL_UPDATE_APPPACIENTE = ROOT_URL + "updateapppaciente";
    public static String URL_DELETE_APPPACIENTE = ROOT_URL + "deleteapppaciente&id=";
}