package ext;

import dao.IDao;
import framework.Component;

@Component("dao2")
public class IDaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version Capteurs");
        return 6000;
    }
}
