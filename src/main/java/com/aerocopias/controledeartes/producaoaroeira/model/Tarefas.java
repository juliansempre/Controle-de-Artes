package com.aerocopias.controledeartes.producaoaroeira.model;

public class Tarefas {
    //Atributos
    private int id;
    private String tarefa;
    //Contrutor

    public Tarefas(int id, String tarefa) {
        this.id = id;
        this.tarefa = tarefa;
    }
    //Operação Getters e setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }
    //Operação to string:
    public String toString(){
        return getTarefa();
    }
}
