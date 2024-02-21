package xmlVersion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private Map<String, Object> instances = new HashMap<>();

    public DependencyInjector(String xmlFile) throws Exception {
        // Lire le fichier XML
        JAXBContext jaxbContext = JAXBContext.newInstance(Injections.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
        Injections injections = (Injections) unmarshaller.unmarshal(xmlStream);

        // Créer des instances et les stocker dans le map
        for (Injection injection : injections.injections) {
            Class<?> clazz = Class.forName(injection.className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            instances.put(injection.id, instance);
        }

        // Injecter les dépendances
        for (Injection injection : injections.injections) {
            if (injection.properties != null) {
                for (Property property : injection.properties) {
                    Object instance = instances.get(injection.id);
                    Object dependency = instances.get(property.ref);
                    Method setter = instance.getClass().getMethod("set" + property.name.substring(0, 1).toUpperCase() + property.name.substring(1), dependency.getClass().getInterfaces()[0]);
                    setter.invoke(instance, dependency);
                }
            }
        }
    }

    public Object getBean(String id) {
        return instances.get(id);
    }
}