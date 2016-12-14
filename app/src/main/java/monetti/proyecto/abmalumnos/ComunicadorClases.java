package monetti.proyecto.abmalumnos;

import java.util.ArrayList;

/**
 * Created by root on 13/12/16.
 */

public class ComunicadorClases {

        private static ArrayList object = null;
        private static String opcion;

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


}
