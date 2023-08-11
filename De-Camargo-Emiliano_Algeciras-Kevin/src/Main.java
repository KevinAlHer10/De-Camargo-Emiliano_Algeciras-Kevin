import com.backend.integrador.dao.impl.DentistDao;
import com.backend.integrador.dao.impl.DentistDaoH2;
import com.backend.integrador.dao.model.Dentist;
import com.backend.integrador.service.DentistCacheService;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
            DentistService dentistService = new DentistService(new DentistDaoH2());

            dentistService.register(new Dentist("Emiliano", "De Camargo", 666333666));

            dentistService.register(new Dentist("Kevin", "Algerciras", 3455647));
            dentistService.findAll();


            System.out.println(" PRUEBA DEL CACHE ########################## ");

            DentistCacheService dentistCacheService = new DentistCacheService(new DentistDao());

            dentistCacheService.register(new Dentist("Emilano cache", "De Camargo cache", 666333666));

            dentistCacheService.register(new Dentist("Kevin cache", "Algerciras cache", 3455647));
            dentistCacheService.findAll();
        }
    }
}