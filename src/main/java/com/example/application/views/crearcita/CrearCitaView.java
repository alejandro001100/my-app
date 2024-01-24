package com.example.application.views.crearcita;

import com.example.application.models.Cita;
import com.example.application.models.Doctor;
import com.example.application.models.Paciente;
import com.example.application.services.CitaService;
import com.example.application.services.DoctorService;
import com.example.application.services.PacienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Crear Cita")
@Route(value = "crear-cita")
public class CrearCitaView extends VerticalLayout {

    private final CitaService citaService;
    private final DoctorService doctorService;
    private final PacienteService pacienteService;

    private Span mensajeBienvenida;
    private Select<Doctor> selectDoctor;
    private TextField pacienteCedulaField;
    private DateTimePicker fechaHora;
    private Button guardar;

    @Autowired
    public CrearCitaView(CitaService citaService, DoctorService doctorService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;

        configurarVista();
    }

    private void configurarVista() {
        H2 titulo = new H2("Crear Nueva Cita");
        mensajeBienvenida = new Span();
        pacienteCedulaField = new TextField("Cédula del Paciente");
        Button buscarPaciente = new Button("Buscar", e -> cargarDatosPaciente());

        fechaHora = new DateTimePicker("Fecha y Hora");
        fechaHora.setVisible(false);

        selectDoctor = new Select<>();
        selectDoctor.setLabel("Doctor");
        selectDoctor.setVisible(false);

        cargarDoctores();

        guardar = new Button("Guardar", e -> guardarCita());
        guardar.setVisible(false);

        add(titulo, pacienteCedulaField, buscarPaciente, mensajeBienvenida, fechaHora, selectDoctor, guardar);
    }

    private void cargarDoctores() {
        List<Doctor> doctores = doctorService.obtenerTodosLosDoctores();
        selectDoctor.setItems(doctores);
        selectDoctor.setItemLabelGenerator(doctor -> doctor.getNombre() + " " + doctor.getApellido() + " - " + doctor.getEspecialidad());
    }

    private void cargarDatosPaciente() {
        String cedula = pacienteCedulaField.getValue();
        Paciente paciente = pacienteService.obtenerPacientePorCedula(cedula);
        if (paciente != null) {
            mensajeBienvenida.setText("Bienvenido: " + paciente.getNombre() + " " + paciente.getApellido());
            mostrarCamposDeCita();
        } else {
            mensajeBienvenida.setText("Paciente no encontrado.");
            ocultarCamposDeCita();
        }
    }

    private void guardarCita() {
        if (fechaHora.isEmpty() || selectDoctor.isEmpty()) {
            Notification.show("Todos los campos son obligatorios.", 3000, Notification.Position.BOTTOM_START);
            return;
        }
        Doctor doctorSeleccionado = selectDoctor.getValue();
        Cita cita = new Cita();

        citaService.guardarCita(cita);
        Notification.show("Cita guardada con éxito");
        limpiarFormulario();
    }

    private void mostrarCamposDeCita() {
        fechaHora.setVisible(true);
        selectDoctor.setVisible(true);
        guardar.setVisible(true);
    }

    private void ocultarCamposDeCita() {
        fechaHora.setVisible(false);
        selectDoctor.setVisible(false);
        guardar.setVisible(false);
    }

    private void limpiarFormulario() {
        fechaHora.clear();
        selectDoctor.clear();
        pacienteCedulaField.clear();
    }
}
