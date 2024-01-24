package com.example.application.views.vercitas;

import com.example.application.models.Cita;
import com.example.application.models.Doctor;
import com.example.application.models.Paciente;
import com.example.application.services.CitaService;
import com.example.application.services.DoctorService;
import com.example.application.services.PacienteService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Ver Citas")
@Route(value = "ver-citas")
public class VerCitasView extends VerticalLayout {

    private final CitaService citaService;
    private final DoctorService doctorService;
    private final PacienteService pacienteService;
    private Grid<Cita> gridCitas;

    @Autowired
    public VerCitasView(CitaService citaService, DoctorService doctorService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;

        configurarGrid();
        add(gridCitas);
        cargarCitas();
    }

    private void configurarGrid() {
        setSizeFull();
        gridCitas = new Grid<>(Cita.class, false);

        // Aquí se configuran las columnas del grid, utilizando los servicios para obtener los datos
        gridCitas.addColumn(cita -> {
            Optional<Doctor> doctor = doctorService.obtenerDoctorPorId(cita.getDoctorId());
            return doctor.map(d -> d.getNombre() + " " + d.getApellido()).orElse("No disponible");
        }).setHeader("Doctor");

        gridCitas.addColumn(cita -> {
            Optional<Doctor> doctor = doctorService.obtenerDoctorPorId(cita.getDoctorId());
            return doctor.map(Doctor::getEspecialidad).orElse("No disponible");
        }).setHeader("Especialidad");

        gridCitas.addColumn(Cita::getFechaHora).setHeader("Fecha y Hora");

        gridCitas.addColumn(cita -> {
            Optional<Paciente> paciente = pacienteService.obtenerPacientePorId(cita.getPacienteId());
            return paciente.map(p -> p.getNombre() + " " + p.getApellido()).orElse("No disponible");
        }).setHeader("Paciente");

        gridCitas.addColumn(cita -> {
            Optional<Paciente> paciente = pacienteService.obtenerPacientePorId(cita.getPacienteId());
            return paciente.map(Paciente::getCedula).orElse("No disponible");
        }).setHeader("Cédula");

        gridCitas.addColumn(cita -> {
            Optional<Paciente> paciente = pacienteService.obtenerPacientePorId(cita.getPacienteId());
            return paciente.map(Paciente::getEmail).orElse("No disponible");
        }).setHeader("Correo");
    }

    private void cargarCitas() {
        gridCitas.setItems(citaService.obtenerTodasLasCitas());
    }
}

