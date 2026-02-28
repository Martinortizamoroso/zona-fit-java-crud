package zona_fit.presentacion;

import zona_fit.datos.ClienteDao;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.List;
import java.util.Scanner;

public class Menu {
    static boolean exit = false;
    public static void main(String[] args) {
        IClienteDAO clienteDAO = new ClienteDao();
        System.out.println("Bienvenidos a Zona Fit Gym");
        Scanner terminal = new Scanner(System.in);
        while (!exit){
            mostrarMenu(terminal, clienteDAO);
        }
    }

    public static void mostrarMenu(Scanner terminal, IClienteDAO clienteDAO) {
        System.out.println("""
                Por favor elija una opción:
                1) Listar clientes
                2) Buscar cliente por ID
                3) Agregar un nuevo cliente
                4) Modificar un cliente
                5) Eliminar un cliente
                6) Salir del sistema""");
        var opt = terminal.nextInt();
        terminal.nextLine();
        switch (opt) {
            case 1 -> listarClientes(clienteDAO);
            case 2 -> buscarClientePorId(terminal,clienteDAO);
            case 3 -> agregarCliente(terminal, clienteDAO);
            case 4 -> modificarCliente(terminal, clienteDAO);
            case 5 -> eliminarCliente(terminal, clienteDAO);
            case 6 -> salirSistetema();
            default -> {
                System.out.println("Ingrese una opcion valida");
            }
        }
    }

    public static void listarClientes(IClienteDAO clienteDAO) {
        try {
            var clientes = clienteDAO.listaClientes();
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados actualmente.");
            }
            for (Cliente cliente : clientes) {
                printClienteFormat(cliente);
            }
        } catch (Exception e) {
            System.out.println("No se puede cargar la lista de clientes, error: " + e.getMessage());
        }
    }
    public  static void buscarClientePorId(Scanner terminal, IClienteDAO clienteDAO) {
        try {
            System.out.println("Ingrese el ID del cliente que desea buscar: ");
            var id = terminal.nextInt();
            terminal.nextLine();
            Cliente cliente = new Cliente(id);
            if(clienteDAO.buscarClientePorId(cliente)){
                printClienteFormat(cliente);
            }
            else
            {
                System.out.println("Cliente no encontrado");
            }

        } catch (Exception e) {
            System.out.println("No se ha podido realizar la busqueda, error: " + e.getMessage());
        }

    }

    public static void agregarCliente(Scanner terminal, IClienteDAO clienteDAO) {
        try {
            Cliente nuevoCliente = new Cliente();
            System.out.print("Ingrese el nombre del nuevo cliente: ");
            nuevoCliente.setNombre(terminal.nextLine());
            System.out.print("Ingrese el apellido del nuevo cliente: ");
            nuevoCliente.setApellido(terminal.nextLine());
            System.out.print("Ingrese el codigo de membresia del nuevo cliente: ");
            nuevoCliente.setMembresia(terminal.nextInt());
            if (clienteDAO.agregarCliente(nuevoCliente))
            {
                System.out.println("El cliente se agrego corerctamente");
            }
        } catch (Exception e) {
            System.out.println("No se puede agregar un nuevo cliente a la lista de clientes, error: " + e.getMessage());
        }
    }

    public static void modificarCliente(Scanner terminal, IClienteDAO clienteDAO) {
        try {
            List<Cliente> clientes = clienteDAO.listaClientes();
            System.out.print("Ingrese el ID del cliente que desea modificar: ");
            var id = terminal.nextInt();
            var terminado = false;
            Cliente clienteEncontrado = null;
            Cliente clienteOriginal = new Cliente();
            for (Cliente cliente : clientes) {
                if (cliente.getId() == id) {
                    clienteEncontrado = cliente;
                    clienteOriginal.setNombre(cliente.getNombre());
                    clienteOriginal.setApellido(cliente.getApellido());
                    clienteOriginal.setMembresia(cliente.getMembresia());
                    clienteOriginal.setId(cliente.getId());
                    break;
                }
            }
            if (clienteEncontrado != null) {
                System.out.println("cliente encontrado: ");
                printClienteFormat(clienteEncontrado);
                while (!terminado) {
                    System.out.println("""
                            Que campo desea modificar del nuevo cliente?:
                            1) Nombre
                            2) Apellido
                            3) Membresia
                            4) Salir""");
                    var opt = terminal.nextInt();
                    terminal.nextLine();
                    switch (opt) {
                        case 1 -> {
                            System.out.print("Ingrese el nuevo nombre para el cliente: ");
                            clienteEncontrado.setNombre(terminal.nextLine());
                        }
                        case 2 -> {
                            System.out.print("Ingrese el nuevo apellido para el cliente: ");
                            clienteEncontrado.setApellido(terminal.nextLine());
                        }
                        case 3 -> {
                            System.out.print("Ingrese el nuevo codigo de membresia: ");
                            clienteEncontrado.setMembresia(terminal.nextInt());
                            terminal.nextLine();
                        }
                        case 4 -> {
                            if (clienteEncontrado.getNombre().equals(clienteOriginal.getNombre()) &&
                                    clienteEncontrado.getApellido().equals(clienteOriginal.getApellido()) &&
                                    clienteEncontrado.getMembresia() == clienteOriginal.getMembresia()) {
                                System.out.println("No se han realizado cambios");
                            } else {
                                clienteDAO.modificarCliente(clienteEncontrado);
                                System.out.println("Se han realizado los cambios correctamente");
                                listarClientes(clienteDAO);
                            }
                            terminado = true;
                        }

                    }
                }
            } else {
                System.out.println("No se pudo encontrar el cliente con el id: " + id);
            }

        } catch (Exception e) {
            System.out.println("No se ha podido modificar los datos del cliente, error: " + e.getMessage());
        }
    }

    public static void eliminarCliente(Scanner terminal, IClienteDAO clienteDAO) {
        try {
            listarClientes(clienteDAO);
            List<Cliente> clientes = clienteDAO.listaClientes();
            System.out.print("Ingrese el ID del cliente que desea eliminar: ");
            var id = terminal.nextInt();
            terminal.nextLine();
            for (Cliente cliente : clientes) {
                if (cliente.getId() == id) {
                    System.out.println("Cliente encontrado: " + cliente);
                    System.out.print("Seguro que desea eliminarlo? Y/N");
                    var opt = terminal.nextLine();
                    switch (opt) {
                        case "Y", "y" -> {
                            clienteDAO.eliminarCliente(cliente);
                            System.out.println("el cliente se ha eliminado correctamente");
                            listarClientes(clienteDAO);
                        }
                        case "N","n" -> System.out.println("No se ha realizado ningun cambio");
                        default -> System.out.println("Opcion incorrecta");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("No se ha podido eliminar el cliente, error: " + e.getMessage());
        }
    }
    public static void salirSistetema()
    {
        System.out.println("Saliendo del sistema :D ");
        exit = true;
    }
    public static void printClienteFormat(Cliente cliente) {
        System.out.println("""
            ID Cliente: %d
            Nombre: %s
            Apellido: %s
            Tipo de membresia: %d
            """.formatted(cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getMembresia()));
    }
}
