package monetti.proyecto.abmalumnos;

/**
 * Created by root on 13/12/16.
 */

public class ComunicadorClases {

        private static Object objeto = null;

        public static void setObjeto(Object newObjeto) {
            objeto = newObjeto;
        }

        public static Object getObjeto() {
            return objeto;
        }


}
