<h1>Mini Projet (Framework Injection des dépendance </h1>

<h2>Enoncé :</h2>

<p>
Développer un mini Framework qui permet de faire l'injection des dépendances avec ses deux version XML et Annotations
Concevoir et créer un mini Framework d'injection des dépendances similaire à Spring IOC
Le Framework doit permettre à un programmeur de faire l'injection des dépendances entre les différents composant de son application respectant les possibilités suivantes : 
1- A travers un fichier XML de configuration en utilisant Jax Binding (OXM : Mapping Objet XML 
2- En utilisant les annotations
3- Possibilité d'injection via :
    a- Le constructeur
    b- Le Setter
    c- Attribut (accès direct à l'attribut : Field 
</p>
<h2>version XML</h2>


<h3>Le fichier dependencies.xml : </h3>

 <p>

     <?xml version="1.0" encoding="UTF-8"?>
     <injections>
         <injection id="dao" className="dao.IDaoImpl" />
         <injection id="metier" className="metier.IMetierImpl">
             <property name="dao" ref="dao" />
         </injection>
     </injections>

 </p> 

  

<h3>Injections class : </h3> 

<p> 

    package xmlVersion;

    import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlRootElement;
    import java.util.List;
    
    @XmlRootElement(name = "injections")
    public class Injections {
    @XmlElement(name = "injection")
    List<Injection> injections;
    }

 </p> 

  

 <h3>Injection class : </h3> 

 <p>

    package xmlVersion;

    import javax.xml.bind.annotation.XmlAttribute;
    import javax.xml.bind.annotation.XmlElement;
    import java.util.List;
    
    public class Injection {
    @XmlAttribute
    String id;
    
        @XmlAttribute(name = "className")
        String className;
    
        @XmlElement(name = "property")
        List<Property> properties;
    }

 </p> 

  

 <h3>Property class : </h3> 

 <p>

    package xmlVersion;
    
    import javax.xml.bind.annotation.XmlAttribute;
    
    public class Property {
    @XmlAttribute
    String name;
    
        @XmlAttribute
        String ref;
    }

 </p> 

  

 <h3>DependencyInjector class : </h3> 

 <p> 
    
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

 </p> 

<h3> Main : </h3> 

 <p>

    package presentation;
    
    import metier.IMetier;
    import xmlVersion.DependencyInjector;
    
    public class PresentationXMLVersion {
    public static void main(String[] args) throws Exception {
    DependencyInjector context = new DependencyInjector("dependencies.xml");
    IMetier metier = (IMetier) context.getBean("metier");
    System.out.println(metier.calcul());
    }
    }

 </p> 

