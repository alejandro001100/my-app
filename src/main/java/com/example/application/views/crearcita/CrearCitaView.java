package com.example.application.views.crearcita;

import com.example.application.models.Cita;
import com.example.application.models.Cita.EstadoCita;
import com.example.application.services.CitaService;
import com.example.application.models.Cita.EstadoCita;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Crear Cita")
@Route(value = "crear-cita")
public class CrearCitaView extends VerticalLayout {

    private final CitaService citaService;

    @Autowired
    public CrearCitaView(CitaService citaService) {
        this.citaService = citaService;

        // Componentes del formulario
        H2 titulo = new H2("Crear Nueva Cita");
        DateTimePicker fechaHora = new DateTimePicker("Fecha y Hora");
        Select<String> doctorId = new Select<>(); // Aquí deberías llenar las opciones con los doctores disponibles
        doctorId.setLabel("Doctor");
        Select<String> pacienteId = new Select<>(); // Similarmente para los pacientes
        pacienteId.setLabel("Paciente");
        Select<EstadoCita> estado = new Select<>();
        estado.setLabel("Estado de la Cita");
        estado.setItems(EstadoCita.values());
        estado.setLabel("Estado de la Cita");
        TextArea descripcion = new TextArea("Descripción");
        descripcion.setPlaceholder("Detalles adicionales sobre la cita...");

        Button guardar = new Button("Guardar", e -> guardarCita(fechaHora, doctorId, pacienteId, estado, descripcion));

        FormLayout formLayout = new FormLayout(fechaHora, doctorId, pacienteId, estado, descripcion);
        formLayout.setMaxWidth("500px");
        add(titulo, formLayout, guardar);
    }

    private void guardarCita(DateTimePicker fechaHora, Select<String> doctorId, Select<String> pacienteId, Select<EstadoCita> estado, TextArea descripcion) {
        if (fechaHora.isEmpty() || doctorId.isEmpty() || pacienteId.isEmpty() || estado.isEmpty()) {
            Notification.show("Todos los campos son obligatorios.", 3000, Notification.Position.BOTTOM_START);
            return;
        }
        Cita cita = new Cita(fechaHora.getValue(), doctorId.getValue(), pacienteId.getValue(), descripcion.getValue(), estado.getValue());

        citaService.guardarCita(cita);
        Notification.show("Cita guardada con éxito");
        fechaHora.clear();
        doctorId.clear();
        pacienteId.clear();
        estado.clear();
        descripcion.clear();
    }
}
