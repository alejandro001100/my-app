package com.example.application.views.verdoctores;

import com.example.application.models.Doctor;
import com.example.application.services.DoctorService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Ver Doctores")
@Route(value = "ver-doctores", layout = MainLayout.class)
public class VerDoctoresView extends VerticalLayout {

    private Grid<Doctor> gridDoctores;
    private final DoctorService doctorService;

    @Autowired
    public VerDoctoresView(DoctorService doctorService) {
        this.doctorService = doctorService;

        H4 titulo = new H4("Doctores ingresados");
        configurarGrid();
        add(titulo, gridDoctores);
        cargarDoctores();
    }

    private void configurarGrid() {
        gridDoctores = new Grid<>(Doctor.class);
        gridDoctores.setSizeFull();
        gridDoctores.setColumns("nombre", "apellido", "especialidad", "email");
        // Ajusta estos nombres de columnas según los campos de tu clase Doctor
    }

    private void cargarDoctores() {
        gridDoctores.setItems(doctorService.obtenerTodosLosDoctores());
    }
}

