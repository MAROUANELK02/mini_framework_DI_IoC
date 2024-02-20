package metier;

import dao.IDao;

public class IMetierImpl implements IMetier {
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
