package metier;

import dao.IDao;
import framework.Autowired;
import framework.Component;
import framework.Qualifier;

@Component("metier")
public class IMetierImpl implements IMetier {
    @Autowired
    @Qualifier("dao2")
    private IDao dao;

    @Override
    public double calcul() {
        double tmp = dao.getData();
        double res = tmp*540/Math.cos(tmp*Math.PI);
        return res;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
