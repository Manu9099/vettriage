package com.clinica.triage_mascotas.service;


import com.clinica.triage_mascotas.model.*;
import com.clinica.triage_mascotas.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TriageRepository triageRepository;

    @Autowired
    private HistorialClinicoRepository historialRepository;

    // Colores
    private static final BaseColor VERDE       = new BaseColor(16, 185, 129);
    private static final BaseColor GRIS_OSCURO = new BaseColor(17, 24, 39);
    private static final BaseColor GRIS_MEDIO  = new BaseColor(55, 65, 81);
    private static final BaseColor ROJO        = new BaseColor(239, 68, 68);
    private static final BaseColor AMARILLO    = new BaseColor(245, 158, 11);
    private static final BaseColor BLANCO      = BaseColor.WHITE;

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarReportePaciente(Long pacienteId) throws Exception {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        List<Triage> triajes = triageRepository.findByPacienteId(pacienteId);
        List<HistorialClinico> historial = historialRepository
                .findByPacienteIdOrderByFechaDesc(pacienteId);

        Document doc = new Document(PageSize.A4, 40, 40, 60, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);
        doc.open();

        // ---- ENCABEZADO ----
        agregarEncabezado(doc, paciente);

        // ---- DATOS DEL PACIENTE ----
        agregarSeccion(doc, "DATOS DEL PACIENTE");
        agregarTablaDobleColumna(doc, new String[][]{
                {"Nombre",   paciente.getNombre()},
                {"Especie",  paciente.getEspecie()},
                {"Raza",     paciente.getRaza() != null ? paciente.getRaza() : "-"},
                {"Edad",     paciente.getEdadMeses() + " meses"},
                {"Peso",     paciente.getPesoKg() + " kg"},
                {"Sexo",     paciente.getSexo() != null ? paciente.getSexo() : "-"},
                {"Esterilizado", paciente.isEsterilizado() ? "Sí" : "No"},
        });

        // ---- DATOS DEL DUEÑO ----
        agregarSeccion(doc, "DATOS DEL DUEÑO");
        agregarTablaDobleColumna(doc, new String[][]{
                {"Nombre",    paciente.getDuenioNombre()},
                {"Teléfono",  paciente.getDuenioTelefono()},
                {"Email",     paciente.getDuenioEmail() != null ? paciente.getDuenioEmail() : "-"},
        });

        // ---- HISTORIAL DE TRIAJES ----
        agregarSeccion(doc, "HISTORIAL DE TRIAJES (" + triajes.size() + ")");
        if (triajes.isEmpty()) {
            agregarParrafo(doc, "No hay triajes registrados.", GRIS_MEDIO);
        } else {
            for (Triage t : triajes) {
                agregarCardTriage(doc, t);
            }
        }

        // ---- HISTORIAL CLÍNICO ----
        agregarSeccion(doc, "HISTORIAL CLÍNICO (" + historial.size() + ")");
        if (historial.isEmpty()) {
            agregarParrafo(doc, "No hay historial clínico registrado.", GRIS_MEDIO);
        } else {
            for (HistorialClinico h : historial) {
                agregarCardHistorial(doc, h);
            }
        }

        // ---- PIE DE PÁGINA ----
        agregarPie(doc);

        doc.close();
        return out.toByteArray();
    }

    // -----------------------------------------------
    // COMPONENTES
    // -----------------------------------------------

    private void agregarEncabezado(Document doc, Paciente p) throws Exception {

        // Barra superior verde
        PdfPTable barraVerde = new PdfPTable(1);
        barraVerde.setWidthPercentage(100);
        PdfPCell barra = new PdfPCell();
        barra.setBackgroundColor(VERDE);
        barra.setFixedHeight(8f);
        barra.setBorder(Rectangle.NO_BORDER);
        barraVerde.addCell(barra);
        doc.add(barraVerde);
        doc.add(Chunk.NEWLINE);

        // Header con logo y datos
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{1, 2});

        // Logo izquierda
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setPadding(10);

        PdfPTable logoBox = new PdfPTable(1);
        logoBox.setWidthPercentage(100);
        PdfPCell logoInner = new PdfPCell();
        logoInner.setBackgroundColor(new BaseColor(6, 78, 59));
        logoInner.setBorder(Rectangle.NO_BORDER);
        logoInner.setPadding(15);
        logoInner.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph logoTexto = new Paragraph("🐾",
                new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BLANCO));
        logoTexto.setAlignment(Element.ALIGN_CENTER);
        logoInner.addElement(logoTexto);

        Paragraph logoNombre = new Paragraph("VetTriage",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, VERDE));
        logoNombre.setAlignment(Element.ALIGN_CENTER);
        logoInner.addElement(logoNombre);

        Paragraph logoSlogan = new Paragraph("Clínica Veterinaria",
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, new BaseColor(167, 243, 208)));
        logoSlogan.setAlignment(Element.ALIGN_CENTER);
        logoInner.addElement(logoSlogan);

        logoBox.addCell(logoInner);
        logoCell.addElement(logoBox);
        header.addCell(logoCell);

        // Info derecha
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setPadding(10);
        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph titulo = new Paragraph("REPORTE CLÍNICO DEL PACIENTE",
                new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, GRIS_OSCURO));
        infoCell.addElement(titulo);

        doc.add(Chunk.NEWLINE);

        Paragraph linea1 = new Paragraph(
                "Paciente: " + p.getNombre() + " (" + p.getEspecie() + ")",
                new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, GRIS_MEDIO));
        infoCell.addElement(linea1);

        Paragraph linea2 = new Paragraph(
                "Propietario: " + p.getDuenioNombre(),
                new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, GRIS_MEDIO));
        infoCell.addElement(linea2);

        Paragraph linea3 = new Paragraph(
                "Generado: " + java.time.LocalDateTime.now().format(FMT),
                new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, GRIS_MEDIO));
        infoCell.addElement(linea3);

        // Badge número de reporte
        Paragraph badge = new Paragraph(
                "ID Paciente: #" + p.getId(),
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, VERDE));
        infoCell.addElement(badge);

        header.addCell(infoCell);
        doc.add(header);

        // Línea separadora
        PdfPTable linea = new PdfPTable(1);
        linea.setWidthPercentage(100);
        linea.setSpacingBefore(5);
        PdfPCell lineaCell = new PdfPCell();
        lineaCell.setBackgroundColor(VERDE);
        lineaCell.setFixedHeight(2f);
        lineaCell.setBorder(Rectangle.NO_BORDER);
        linea.addCell(lineaCell);
        doc.add(linea);
        doc.add(Chunk.NEWLINE);
    }

    private void agregarSeccion(Document doc, String titulo) throws Exception {
        doc.add(Chunk.NEWLINE);
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(new Phrase(titulo,
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BLANCO)));
        cell.setBackgroundColor(GRIS_OSCURO);
        cell.setPadding(8);
        cell.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(cell);
        doc.add(tabla);
    }

    private void agregarTablaDobleColumna(Document doc, String[][] datos) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1, 2});
        tabla.setSpacingBefore(4);

        for (String[] fila : datos) {
            PdfPCell label = new PdfPCell(new Phrase(fila[0],
                    new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, GRIS_MEDIO)));
            label.setPadding(6);
            label.setBackgroundColor(new BaseColor(243, 244, 246));
            label.setBorderColor(new BaseColor(229, 231, 235));

            PdfPCell valor = new PdfPCell(new Phrase(fila[1],
                    new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, GRIS_OSCURO)));
            valor.setPadding(6);
            valor.setBorderColor(new BaseColor(229, 231, 235));

            tabla.addCell(label);
            tabla.addCell(valor);
        }
        doc.add(tabla);
    }

    private void agregarCardTriage(Document doc, Triage t) throws Exception {
        PdfPTable card = new PdfPTable(1);
        card.setWidthPercentage(100);
        card.setSpacingBefore(6);

        BaseColor colorNivel = t.getNivelPrioridad().equals("URGENTE") ? ROJO :
                t.getNivelPrioridad().equals("MODERADO") ? AMARILLO : VERDE;

        // Cabecera del card
        PdfPCell cabecera = new PdfPCell();
        cabecera.setBackgroundColor(colorNivel);
        cabecera.setPadding(6);
        cabecera.setBorder(Rectangle.NO_BORDER);
        cabecera.addElement(new Paragraph(
                t.getNivelPrioridad() + "  |  Puntaje: " + t.getPuntajeTotal() + " pts  |  " +
                        (t.getFechaTriage() != null ? t.getFechaTriage().format(FMT) : ""),
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BLANCO)));
        card.addCell(cabecera);

        // Contenido
        PdfPCell contenido = new PdfPCell();
        contenido.setPadding(8);
        contenido.setBorderColor(colorNivel);

        Paragraph p = new Paragraph();
        p.add(new Chunk("Diagnóstico: ",
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, GRIS_OSCURO)));
        p.add(new Chunk(t.getDiagnosticoProbable() != null ? t.getDiagnosticoProbable() : "-",
                new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, GRIS_OSCURO)));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Recomendación: ",
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, GRIS_OSCURO)));
        p.add(new Chunk(t.getRecomendacion() != null ? t.getRecomendacion() : "-",
                new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, GRIS_OSCURO)));
        p.add(Chunk.NEWLINE);

        // Síntomas
        if (t.getSintomas() != null && !t.getSintomas().isEmpty()) {
            p.add(new Chunk("Síntomas: ",
                    new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, GRIS_OSCURO)));
            String sintomas = t.getSintomas().stream()
                    .map(Sintoma::getNombre)
                    .reduce((a, b) -> a + ", " + b).orElse("-");
            p.add(new Chunk(sintomas,
                    new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, GRIS_OSCURO)));
        }

        contenido.addElement(p);
        card.addCell(contenido);
        doc.add(card);
    }

    private void agregarCardHistorial(Document doc, HistorialClinico h) throws Exception {
        PdfPTable card = new PdfPTable(1);
        card.setWidthPercentage(100);
        card.setSpacingBefore(6);

        // Cabecera
        PdfPCell cabecera = new PdfPCell();
        cabecera.setBackgroundColor(GRIS_OSCURO);
        cabecera.setPadding(6);
        cabecera.setBorder(Rectangle.NO_BORDER);
        cabecera.addElement(new Paragraph(
                (h.getFecha() != null ? h.getFecha().format(FMT) : "") +
                        "  |  Dr: " + (h.getDoctor() != null ? h.getDoctor().getNombre() : "-"),
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BLANCO)));
        card.addCell(cabecera);

        // Contenido
        PdfPCell contenido = new PdfPCell();
        contenido.setPadding(8);
        contenido.setBorderColor(GRIS_MEDIO);

        Paragraph p = new Paragraph();
        agregarLinea(p, "Diagnóstico",   h.getDiagnostico());
        agregarLinea(p, "Tratamiento",   h.getTratamiento());
        agregarLinea(p, "Medicamentos",  h.getMedicamentos());
        agregarLinea(p, "Observaciones", h.getObservaciones());
        if (h.getPesoKg() != null)
            agregarLinea(p, "Peso", h.getPesoKg() + " kg");
        if (h.getTemperatura() != null)
            agregarLinea(p, "Temperatura", h.getTemperatura() + " °C");
        if (h.getProximaVisita() != null && !h.getProximaVisita().isEmpty())
            agregarLinea(p, "Próxima visita", h.getProximaVisita());

        contenido.addElement(p);
        card.addCell(contenido);
        doc.add(card);
    }

    private void agregarLinea(Paragraph p, String label, String valor) {
        if (valor == null || valor.isEmpty()) return;
        p.add(new Chunk(label + ": ",
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, GRIS_OSCURO)));
        p.add(new Chunk(valor,
                new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, GRIS_OSCURO)));
        p.add(Chunk.NEWLINE);
    }

    private void agregarParrafo(Document doc, String texto, BaseColor color) throws Exception {
        doc.add(new Paragraph(texto,
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, color)));
    }

    private void agregarPie(Document doc) throws Exception {
        doc.add(Chunk.NEWLINE);

        PdfPTable pie = new PdfPTable(2);
        pie.setWidthPercentage(100);
        pie.setWidths(new float[]{1, 2});

        // Lado izquierdo
        PdfPCell izq = new PdfPCell();
        izq.setBorder(Rectangle.TOP);
        izq.setBorderColor(VERDE);
        izq.setPaddingTop(8);
        izq.addElement(new Paragraph("🐾 VetTriage",
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, VERDE)));
        izq.addElement(new Paragraph("Sistema de Triaje Inteligente",
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, GRIS_MEDIO)));
        pie.addCell(izq);

        // Lado derecho
        PdfPCell der = new PdfPCell();
        der.setBorder(Rectangle.TOP);
        der.setBorderColor(VERDE);
        der.setPaddingTop(8);
        der.setHorizontalAlignment(Element.ALIGN_RIGHT);
        der.addElement(new Paragraph(
                "Documento generado el " + java.time.LocalDateTime.now().format(FMT),
                new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, GRIS_MEDIO)));
        der.addElement(new Paragraph(
                "Este documento es de carácter confidencial.",
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, GRIS_MEDIO)));
        pie.addCell(der);

        doc.add(pie);
    }
}