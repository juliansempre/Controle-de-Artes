package com.aerocopias.controledeartes.autentificacao.login.controller;

public class SessãoController {
    private static String nomeUsuarioAutenticado;

    public static String getNomeUsuarioAutenticado() {
        return nomeUsuarioAutenticado;
    }

    public static void setNomeUsuarioAutenticado(String nome) {
        nomeUsuarioAutenticado = nome;
    }
}
