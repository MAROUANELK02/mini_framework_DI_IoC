package presentation;

import metier.IMetier;
import xmlVersion.DependencyInjector;

public class PresSpringXML {
    public static void main(String[] args) throws Exception {
        DependencyInjector context = new DependencyInjector("dependencies.xml");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
