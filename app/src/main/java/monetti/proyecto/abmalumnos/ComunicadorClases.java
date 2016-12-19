package monetti.proyecto.abmalumnos;

import android.text.Editable;

import java.util.ArrayList;

/**
 * Contiene los valores de nombreUsuario, tipoUsuario y modificar/alta
 */

public class ComunicadorClases {

        private static ArrayList object = null;
        private static String opcion;
        private static Editable dniUsuario;

        public static void setObject(ArrayList newObject) {
            object = newObject;
        }

        public static ArrayList getObject() {
            return object;
        }

        public static void setOpcion(String opcionNombre){
            opcion = opcionNombre;
        }

        public static String getOpcion(){
            return opcion;
        }
        public static void setDniUsuario(Editable dniUsuarioIngresado){
            dniUsuario = dniUsuarioIngresado;
        }

        public static Editable getDniUsuario(){
            return dniUsuario;
        }


}
