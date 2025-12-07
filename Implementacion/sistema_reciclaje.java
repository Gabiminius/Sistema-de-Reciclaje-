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
}

class Coordinador extends Usuario {
    public Coordinador(int usuarioID, String rut, String nombre, String correo) {
        super(usuarioID, rut, nombre, correo);
    }

    public void asignarConductor(int rutaID) {
        System.out.println("Asignando conductor a ruta ID: " + rutaID);
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
}

class RutaDeRecoleccion {
    private int rutaID;
    private Date fecha;
    private List<SolicitudDeRecoleccion> listaDeRecoleccion;

    public RutaDeRecoleccion(int rutaID, Date fecha) {
        this.rutaID = rutaID;
        this.fecha = fecha;
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

    public int getRutaID() { return rutaID; }
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
        System.out.println("Emitiendo PDF del comprobante para solicitud: " + solicitudID);
        System.out.println("Estado: " + estadoFinal);
        System.out.println("Fecha: " + fechaHoraEmision);
    }
}

class ControladorSolicitud {
    private List<SolicitudDeRecoleccion> solicitudes;
    private int contadorID;

    public ControladorSolicitud() {
        this.solicitudes = new ArrayList<>();
        this.contadorID = 1;
    }

    public SolicitudDeRecoleccion crearSolicitud(Hogar hogar, List<DetalleMaterial> listaMateriales) {
        SolicitudDeRecoleccion solicitud = new SolicitudDeRecoleccion();
        solicitud.setSolicitudID(contadorID++);
        solicitud.crear(new Date(), new Date(), hogar.getDireccion());
        
        for (DetalleMaterial material : listaMateriales) {
            solicitud.agregarDetalle(material);
        }
        
        solicitudes.add(solicitud);
        System.out.println("Solicitud creada con ID: " + solicitud.getSolicitudID());
        return solicitud;
    }

    public List<SolicitudDeRecoleccion> getSolicitudes() { return solicitudes; }
}

class ControladorRuta {
    private List<RutaDeRecoleccion> rutas;

    public ControladorRuta() {
        this.rutas = new ArrayList<>();
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

    public void agregarRuta(RutaDeRecoleccion ruta) {
        rutas.add(ruta);
    }
}

class SistemaReciclaje {
    public static void main(String[] args) {
        Hogar hogar = new Hogar(1, "12345678-9", "Juan Pérez", "juan@email.com",
                                "Av. Los carrera 1203", -36.8f, -73.0f);

        List<DetalleMaterial> materiales = new ArrayList<>();
        materiales.add(new DetalleMaterial("Botellas plásticas", 5.5f));
        materiales.add(new DetalleMaterial("Cartón", 3.2f));

        ControladorSolicitud controladorSol = new ControladorSolicitud();
        SolicitudDeRecoleccion solicitud = controladorSol.crearSolicitud(hogar, materiales);

        System.out.println("Peso total de la solicitud: " + solicitud.calcularPesoSol() + " kg");

        Reciclador reciclador = new Reciclador(2, "98765432-1", "Constanza Cristinich",
                                                "conicris@email.com", "AB-1234", 100.0f,
                                                "http://docs.com", "Coop. Recicla", 
                                                -36.8f, -73.0f);

        ControladorRuta controladorRuta = new ControladorRuta();
        List<Reciclador> recicladores = new ArrayList<>();
        recicladores.add(reciclador);
        
        List<Reciclador> aptos = controladorRuta.buscarPorCapacidad(
            solicitud.calcularPesoSol(), recicladores);

        System.out.println("Recicladores aptos encontrados: " + aptos.size());

        Comprobante comprobante = reciclador.generarComprobante(solicitud.getSolicitudID());
        comprobante.emitirPDF();
    }
}