package com.example.application.views.vercitas;

import com.example.application.models.Cita;
import com.example.application.services.CitaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Ver Citas")
@Route(value = "ver-citas", layout = MainLayout.class)
public class VerCitasView extends VerticalLayout {

    private Grid<Cita> gridCitas;
    private final CitaService citaService;

    @Autowired
    public VerCitasView(CitaService citaService) {
        this.citaService = citaService;

        H4 titulo = new H4("Citas creadas");
        configurarGrid();
        add(titulo, gridCitas);
        cargarCitas();
    }

    private void configurarGrid() {
        gridCitas = new Grid<>(Cita.class);
        gridCitas.setSizeFull();
        gridCitas.setColumns("fechaHora", "doctorId", "pacienteId", "descripcion", "estado");
        // Ajusta estos nombres de columnas según los campos de tu clase Cita
    }

    private void cargarCitas() {
        gridCitas.setItems(citaService.obtenerTodasLasCitas());
    }
}

