package presentation;

import framework.utils.Injector;
import metier.IMetier;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        Injector.startApplication(PresSpringAnnotation.class);
        IMetier metier = Injector.getService(IMetier.class);
        if (metier != null) {
            double calcul = metier.calcul();
            System.out.println(calcul);
        } else {
            System.out.println("IMetier service not found");
        }
    }
}