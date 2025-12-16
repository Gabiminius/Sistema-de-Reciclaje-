import java.util.*;

class Usuario {
    private int usuarioID;
    private String rut;
    private String nombre;
    private String correo;

    public Usuario(int usuarioID, String rut, String nombre, String correo) {
        this.usuarioID = usuarioID;
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getUsuarioID() { return usuarioID; }
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
}

class Hogar extends Usuario {
    private String direccion;
    private float longitud;
    private float latitud;

    public Hogar(int usuarioID, String rut, String nombre, String correo,
                 String direccion, float longitud, float latitud) {
        super(usuarioID, rut, nombre, correo);
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public void solicitarRecoleccion(List<DetalleMaterial> listaMateriales) {
        System.out.println("Solicitando recolección para dirección: " + direccion);
    }

    public void recibirNotificacion(String mensaje) {
        System.out.println(">>> NOTIFICACIÓN para " + getNombre() + ": " + mensaje);
    }

    public String getDireccion() { return direccion; }
    public float getLongitud() { return longitud; }
    public float getLatitud() { return latitud; }
}

class Reciclador extends Usuario {
    private String patenteVehiculo;
    private float capacidadVehiculo;
    private String linkDocumentos;
    private String estadoReciclador;
    private String direccionCooperativa;
    private float longitud;
    private float latitud;


    public Reciclador(int usuarioID, String rut, String nombre, String correo,
                      String patenteVehiculo, float capacidadVehiculo, String linkDocumentos,
                      String direccionCooperativa, float longitud, float latitud) {
        super(usuarioID, rut, nombre, correo);
        this.patenteVehiculo = patenteVehiculo;
        this.capacidadVehiculo = capacidadVehiculo;
        this.linkDocumentos = linkDocumentos;
        this.estadoReciclador = "Disponible";
        this.direccionCooperativa = direccionCooperativa;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public float getCapacidad() {
        return capacidadVehiculo;
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estadoReciclador = nuevoEstado;
        System.out.println("Estado actualizado a: " + nuevoEstado);
    }

    public Comprobante generarComprobante(int solicitudID) {
        return new Comprobante(solicitudID, "Completado");
    }

    public String getEstadoReciclador() { return estadoReciclador; }
    public String getPatenteVehiculo() { return patenteVehiculo; }
}

class Coordinador extends Usuario {
    public Coordinador(int usuarioID, String rut, String nombre, String correo) {
        super(usuarioID, rut, nombre, correo);
    }
    public void revisarPropuesta(int rutaID) {
        System.out.println("Coordinador " + getNombre() + " revisando propuesta de ruta ID: " + rutaID);
    }
    public void editarOrdenRuta(int rutaID, String nuevoOrden) {
        System.out.println("Coordinador editando orden de ruta ID " + rutaID + " a: " + nuevoOrden);
    }
    public void confirmarRuta(int rutaID) {
        System.out.println("Coordinador confirmando ruta ID: " + rutaID);
    }
    public void asignarConductor(int rutaID) {
        System.out.println("Coordinador asignando conductor a ruta ID: " + rutaID);
    }
}

class DetalleMaterial {
    private String descripcion;
    private float pesoAprox;

    public DetalleMaterial(String descripcion, float pesoAprox) {
        this.descripcion = descripcion;
        this.pesoAprox = pesoAprox;
    }

    public void crear(String desc, float peso) {
        this.descripcion = desc;
        this.pesoAprox = peso;
    }

    public float getPesoAprox() {
        return pesoAprox;
    }

    public String getDescripcion() { return descripcion; }
}

class SolicitudDeRecoleccion {
    private int solicitudID;
    private Date fecha;
    private Date hora;
    private String estado;
    private List<DetalleMaterial> detalles;
    private int hogarID;

    public SolicitudDeRecoleccion() {
        this.detalles = new ArrayList<>();
        this.estado = "Pendiente";
    }

    public void crear(Date fecha, Date hora, String dir) {
        this.fecha = fecha;
        this.hora = hora;
        System.out.println("Solicitud creada para: " + dir);
    }

    public void agregarDetalle(DetalleMaterial detalle) {
        detalles.add(detalle);
    }

    public float calcularPesoSol() {
        float pesoTotal = 0;
        for (DetalleMaterial detalle : detalles) {
            pesoTotal += detalle.getPesoAprox();
        }
        return pesoTotal;
    }

    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public int getSolicitudID() { return solicitudID; }
    public void setSolicitudID(int id) { this.solicitudID = id; }
    public String getEstado() { return estado; }
    public List<DetalleMaterial> getDetalles() { return detalles; }
    public int getHogarID() { return hogarID; }
    public void setHogarID(int hogarID) { this.hogarID = hogarID; }
}

class RutaDeRecoleccion {
    private int rutaID;
    private Date fecha;
    private String estadoRuta;
    private String ordenRuta;
    private List<SolicitudDeRecoleccion> listaDeRecoleccion;

    public RutaDeRecoleccion(int rutaID, Date fecha, String estadoRuta) {
        this.rutaID = rutaID;
        this.fecha = fecha;
        this.estadoRuta = estadoRuta;
        this.ordenRuta = "";
        this.listaDeRecoleccion = new ArrayList<>();
    }

    public float calcularPesoRuta() {
        float pesoTotal = 0;
        for (SolicitudDeRecoleccion solicitud : listaDeRecoleccion) {
            pesoTotal += solicitud.calcularPesoSol();
        }
        return pesoTotal;
    }

    public void agregarSolicitud(SolicitudDeRecoleccion sol) {
        listaDeRecoleccion.add(sol);
    }

    public void setOrden(String nuevoOrden) {
        this.ordenRuta = nuevoOrden;
        System.out.println("Orden de ruta " + rutaID + " actualizado a: " + nuevoOrden);
    }

    public int getRutaID() { return rutaID; }
    public String getEstadoRuta() { return estadoRuta; }
    public void setEstadoRuta(String estado) { this.estadoRuta = estado; }
    public String getOrdenRuta() { return ordenRuta; }
    public List<SolicitudDeRecoleccion> getListaDeRecoleccion() { return listaDeRecoleccion; }
}

class Comprobante {
    private int solicitudID;
    private Date fechaHoraEmision;
    private String estadoFinal;

    public Comprobante(int solicitudID, String estadoFinal) {
        this.solicitudID = solicitudID;
        this.fechaHoraEmision = new Date();
        this.estadoFinal = estadoFinal;
    }

    public void emitirPDF() {
        System.out.println("=== COMPROBANTE PDF ===");
        System.out.println("Solicitud ID: " + solicitudID);
        System.out.println("Estado: " + estadoFinal);
        System.out.println("Fecha: " + fechaHoraEmision);
        System.out.println("======================");
    }
}

class ControladorSolicitud {
    private List<SolicitudDeRecoleccion> solicitudes;
    private List<Hogar> hogares;
    private int contadorID;

    public ControladorSolicitud() {
        this.solicitudes = new ArrayList<>();
        this.hogares = new ArrayList<>();
        this.contadorID = 1;
    }

    public void registrarHogar(Hogar hogar) {
        hogares.add(hogar);
    }

    private Hogar buscarHogarPorID(int hogarID) {
        for (Hogar h : hogares) {
            if (h.getUsuarioID() == hogarID) {
                return h;
            }
        }
        return null;
    }

    public SolicitudDeRecoleccion crearSolicitud(int hogarID, List<DetalleMaterial> listaMateriales) {
        Hogar hogar = buscarHogarPorID(hogarID);
        if (hogar == null) {
            System.out.println("Error: Hogar no encontrado");
            return null;
        }

        SolicitudDeRecoleccion solicitud = new SolicitudDeRecoleccion();
        solicitud.setSolicitudID(contadorID++);
        solicitud.setHogarID(hogarID);
        solicitud.crear(new Date(), new Date(), hogar.getDireccion());

        for (DetalleMaterial material : listaMateriales) {
            solicitud.agregarDetalle(material);
        }

        solicitudes.add(solicitud);
        System.out.println("Solicitud creada con ID: " + solicitud.getSolicitudID());

        notificarExitoCreacion(hogarID);

        return solicitud;
    }

    public void notificarExitoCreacion(int hogarID) {
        Hogar hogar = buscarHogarPorID(hogarID);
        if (hogar != null) {
            hogar.recibirNotificacion("Su solicitud de recolección ha sido creada exitosamente.");
        }
    }

    public List<SolicitudDeRecoleccion> getSolicitudes() { return solicitudes; }
    public List<Hogar> getHogares() { return hogares; }
}

class ControladorRuta {
    private List<RutaDeRecoleccion> rutas;
    private List<Hogar> hogares;
    private int contadorRutaID;

    public ControladorRuta() {
        this.rutas = new ArrayList<>();
        this.hogares = new ArrayList<>();
        this.contadorRutaID = 1;
    }
    public List<SolicitudDeRecoleccion> agruparSolicitudes(String zona) {
        System.out.println("Agrupando solicitudes por zona: " + zona);
        List<SolicitudDeRecoleccion> solicitudesZona = new ArrayList<>();
        return solicitudesZona;
    }
    public RutaDeRecoleccion generarPropuesta(String zona, List<SolicitudDeRecoleccion> solicitudes) {
        System.out.println("\n=== GENERANDO PROPUESTA DE RUTA ===");
        System.out.println("Zona: " + zona);
        RutaDeRecoleccion ruta = new RutaDeRecoleccion(contadorRutaID++, new Date(), "Propuesta");

        for (SolicitudDeRecoleccion sol : solicitudes) {
            ruta.agregarSolicitud(sol);
        }

        rutas.add(ruta);
        System.out.println("Ruta ID " + ruta.getRutaID() + " creada con " +
                solicitudes.size() + " solicitudes");
        System.out.println("Peso total: " + ruta.calcularPesoRuta() + " kg");
        return ruta;
    }
    public void modificarOrden(int rutaID, String nuevoOrden) {
        for (RutaDeRecoleccion ruta : rutas) {
            if (ruta.getRutaID() == rutaID) {
                ruta.setOrden(nuevoOrden);
                System.out.println("Orden de ruta " + rutaID + " modificado.");
                return;
            }
        }
        System.out.println("Ruta no encontrada.");
    }
    public float calcularCargaRuta(int rutaID) {
        for (RutaDeRecoleccion ruta : rutas) {
            if (ruta.getRutaID() == rutaID) {
                return ruta.calcularPesoRuta();
            }
        }
        return 0;
    }

    public List<Reciclador> buscarPorCapacidad(float cargaTotal, List<Reciclador> recicladores) {
        List<Reciclador> aptos = new ArrayList<>();
        for (Reciclador reciclador : recicladores) {
            if (reciclador.getCapacidad() >= cargaTotal &&
                    reciclador.getEstadoReciclador().equals("Disponible")) {
                aptos.add(reciclador);
            }
        }
        return aptos;
    }
    public void setHogares(List<Hogar> hogares) {
        this.hogares = hogares;
    }

    public void notificarHorario(int rutaID) {
        for (RutaDeRecoleccion ruta : rutas) {
            if (ruta.getRutaID() == rutaID) {
                System.out.println("\n=== NOTIFICANDO HORARIOS ===");
                System.out.println("Ruta ID: " + rutaID);

                for (SolicitudDeRecoleccion sol : ruta.getListaDeRecoleccion()) {
                    Hogar hogar = buscarHogarPorID(sol.getHogarID());
                    if (hogar != null) {
                        String mensaje = "Su recolección está programada en la ruta " + rutaID +
                                ". Fecha: " + new Date().toString();
                        hogar.recibirNotificacion(mensaje);
                    }
                }
                return;
            }
        }
        System.out.println("Ruta no encontrada.");
    }

    private Hogar buscarHogarPorID(int hogarID) {
        for (Hogar h : hogares) {
            if (h.getUsuarioID() == hogarID) {
                return h;
            }
        }
        return null;
    }

    public void agregarRuta(RutaDeRecoleccion ruta) {
        rutas.add(ruta);
    }

    public List<RutaDeRecoleccion> getRutas() {
        return rutas;
    }

    public RutaDeRecoleccion buscarRutaPorID(int rutaID) {
        for (RutaDeRecoleccion ruta : rutas) {
            if (ruta.getRutaID() == rutaID) {
                return ruta;
            }
        }
        return null;
    }
}

class SistemaReciclaje {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE RECOLECCIÓN DE RECICLAJE ===\n");

        // Inicializar controladores
        ControladorSolicitud controladorSol = new ControladorSolicitud();
        ControladorRuta controladorRuta = new ControladorRuta();

        // Crear hogares
        Hogar hogar1 = new Hogar(1, "12345678-9", "Juan Pérez", "juan@email.com",
                "Av. Los Carrera 1203", -36.8f, -73.0f);
        Hogar hogar2 = new Hogar(2, "98765432-1", "María González", "maria@email.com",
                "Calle O'Higgins 456", -36.82f, -73.05f);

        controladorSol.registrarHogar(hogar1);
        controladorSol.registrarHogar(hogar2);
        controladorRuta.setHogares(controladorSol.getHogares());

        // Crear recicladores
        Reciclador reciclador1 = new Reciclador(3, "11111111-1", "Constanza Cristinich",
                "conicris@email.com", "AB-1234", 100.0f,
                "http://docs.com", "Coop. Recicla",
                -36.8f, -73.0f);

        List<Reciclador> recicladores = new ArrayList<>();
        recicladores.add(reciclador1);

        // Crear coordinador
        Coordinador coordinador = new Coordinador(100, "00000000-0", "Pedro Coordinador",
                "coord@email.com");

        System.out.println("--- PASO 1: CREAR SOLICITUDES ---\n");

        // Crear primera solicitud usando hogarID
        List<DetalleMaterial> materiales1 = new ArrayList<>();
        materiales1.add(new DetalleMaterial("Botellas plásticas", 5.5f));
        materiales1.add(new DetalleMaterial("Cartón", 3.2f));
        SolicitudDeRecoleccion solicitud1 = controladorSol.crearSolicitud(1, materiales1);

        System.out.println();

        // Crear segunda solicitud
        List<DetalleMaterial> materiales2 = new ArrayList<>();
        materiales2.add(new DetalleMaterial("Papel", 4.0f));
        materiales2.add(new DetalleMaterial("Vidrio", 6.5f));
        SolicitudDeRecoleccion solicitud2 = controladorSol.crearSolicitud(2, materiales2);

        System.out.println("\n--- PASO 2: GENERAR PROPUESTA DE RUTA ---\n");

        // Generar propuesta de ruta
        List<SolicitudDeRecoleccion> solicitudes = new ArrayList<>();
        solicitudes.add(solicitud1);
        solicitudes.add(solicitud2);
        RutaDeRecoleccion ruta = controladorRuta.generarPropuesta("Centro", solicitudes);

        System.out.println("\n--- PASO 3: COORDINADOR REVISA Y EDITA ---\n");

        // Coordinador revisa la propuesta
        coordinador.revisarPropuesta(ruta.getRutaID());

        // Coordinador edita el orden
        coordinador.editarOrdenRuta(ruta.getRutaID(), "Hogar 2 -> Hogar 1");
        controladorRuta.modificarOrden(ruta.getRutaID(), "Hogar 2 -> Hogar 1");

        System.out.println();

        // Coordinador confirma la ruta
        coordinador.confirmarRuta(ruta.getRutaID());
        ruta.setEstadoRuta("Confirmada");

        System.out.println("\n--- PASO 4: BUSCAR Y ASIGNAR RECICLADOR ---\n");

        // Buscar recicladores con capacidad suficiente
        float cargaTotal = controladorRuta.calcularCargaRuta(ruta.getRutaID());
        System.out.println("Carga total de la ruta: " + cargaTotal + " kg");

        List<Reciclador> aptos = controladorRuta.buscarPorCapacidad(cargaTotal, recicladores);
        System.out.println("Recicladores aptos encontrados: " + aptos.size());

        if (!aptos.isEmpty()) {
            Reciclador seleccionado = aptos.get(0);
            seleccionado.actualizarEstado("En ruta");
            coordinador.asignarConductor(ruta.getRutaID());
        }

        System.out.println("\n--- PASO 5: NOTIFICAR HORARIOS ---\n");

        // Notificar horarios a los hogares
        controladorRuta.notificarHorario(ruta.getRutaID());

        System.out.println("\n--- PASO 6: GENERAR COMPROBANTE ---\n");

        // Generar comprobante
        Comprobante comprobante = reciclador1.generarComprobante(solicitud1.getSolicitudID());
        comprobante.emitirPDF();

        System.out.println("\n--- RESUMEN FINAL ---\n");
        System.out.println("Total de solicitudes: " + controladorSol.getSolicitudes().size());
        System.out.println("Total de rutas: " + controladorRuta.getRutas().size());
        System.out.println("Rutas asignadas a " + reciclador1.getNombre() );
       
    }
}
