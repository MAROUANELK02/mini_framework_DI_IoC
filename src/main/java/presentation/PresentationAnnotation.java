package presentation;

import framework.MyFramework;
import metier.IMetier;

public class PresentationAnnotation {
    public static void main(String[] args) throws Exception {
        MyFramework context = new MyFramework(".");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}