package com.example.application.views.verpacientes;

import com.example.application.models.Paciente;
import com.example.application.services.PacienteService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Ver Pacientes")
@Route(value = "ver-pacientes", layout = MainLayout.class)
public class VerPacientesView extends VerticalLayout {

    private Grid<Paciente> gridPacientes;
    private final PacienteService pacienteService;

    @Autowired
    public VerPacientesView(PacienteService pacienteService) {
        this.pacienteService = pacienteService;

        H4 titulo = new H4("Pacientes ingresados");
        configurarGrid();
        add(titulo, gridPacientes);
        cargarPacientes();
    }

    private void configurarGrid() {
        gridPacientes = new Grid<>(Paciente.class);
        gridPacientes.setSizeFull();
        gridPacientes.setColumns("nombres", "apellidos", "cedula", "direccion", "telefono", "email");
        // Ajusta estos nombres de columnas según los campos de tu clase Paciente
    }

    private void cargarPacientes() {
        gridPacientes.setItems(pacienteService.obtenerTodosLosPacientes());
    }
}
