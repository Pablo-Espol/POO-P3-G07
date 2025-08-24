
package com.espol.tecnicentro.modelo;

import com.espol.tecnicentro.ListaBase.DatosBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fmalu
 */
public class Cliente extends Personal implements Serializable {
    private TipoCliente tipoCliente;
    public static final String nomArchivoCl = "Clientes.ser";

    public Cliente(String identificacion,String nombre,String telefono,String direccion,TipoCliente tipoCliente) {
        super(identificacion,nombre,telefono,direccion);
        this.tipoCliente = tipoCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }


    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

   @Override
    public String toString() {
        return getIdentificacion();
    }
    public static ArrayList<Cliente> cargaClientes(File directorio){
        ArrayList<Cliente> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivoCl);
        //se escribe la lista serializada
        if ( f.exists()) { //si no existe se crea la lista
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                lista = (ArrayList<Cliente>) is.readObject();

            } catch (Exception e) {
                //quizas lanzar una excepcion personalizada
                new Exception(e.getMessage());
            }
        }
        return lista;
    }
    public static boolean guardarLista(File directorio,ArrayList<Cliente> lista) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nomArchivoCl);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            guardado = true;
        } catch (IOException e) {

            //quizas lanzar una excepcion personalizada
            throw new Exception(e.getMessage());
        }
        return guardado;
    }
    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<Cliente> lista = DatosBase.getInstance().getListClient();
        boolean guardado = false;

        File f = new File(directorio, nomArchivoCl);
        if (lista.isEmpty()) {
            lista = DatosBase.getInstance().getListClient(); //  carga datos de ejemplo si la lista está vacía
        }

        //se escribe la lista serializada
        if (! f.exists()) { //si no existe se crea la lista
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(lista);
                guardado = true;
            } catch (IOException e) {
                //quizas lanzar una excepcion personalizada
                throw new Exception(e.getMessage());
            }
        }else guardado = true;//si existe no hace nada
        return guardado;
    }

}
