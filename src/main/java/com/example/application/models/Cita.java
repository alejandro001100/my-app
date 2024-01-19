package com.example.application.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "citas")
public class Cita {

    @Id
    private String id;
    private LocalDateTime fechaHora;
    private String doctorId;
    private String pacienteId;
    private String descripcion; // Detalles adicionales sobre la cita
    private EstadoCita estado; // Enum para el estado de la cita, por ejemplo: PENDIENTE, CONFIRMADA, CANCELADA

    // Constructor por defecto
    public Cita() {
    }

    // Constructor con todos los campos
    public Cita(LocalDateTime fechaHora, String doctorId, String pacienteId, String descripcion, EstadoCita estado) {
        this.fechaHora = fechaHora;
        this.doctorId = doctorId;
        this.pacienteId = pacienteId;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    // Enum para los estados de la cita
    public enum EstadoCita {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA

    }

}
