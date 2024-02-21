package framework;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyFramework {
    private Map<String, Object> beans = new HashMap<>();

    public MyFramework(String basePackage) throws Exception {
        // Parcourir toutes les classes du package
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        // Créer des instances pour les classes annotées avec @Component
        for (Class<?> clazz : classes) {
            Component component = clazz.getAnnotation(Component.class);
            String beanName = !component.value().isEmpty() ? component.value() : clazz.getName();
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beans.put(beanName, instance);
        }

        // Injecter les dépendances
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = beans.get(field.getType().getName());
                    if (field.isAnnotationPresent(Qualifier.class)) {
                        Qualifier qualifier = field.getAnnotation(Qualifier.class);
                        dependency = beans.get(qualifier.value());
                    }
                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }

            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set")) {
                    Object dependency = beans.get(method.getParameterTypes()[0].getName());
                    if (method.isAnnotationPresent(Qualifier.class)) {
                        Qualifier qualifier = method.getAnnotation(Qualifier.class);
                        dependency = beans.get(qualifier.value());
                    }
                    method.invoke(bean, dependency);
                }
            }

            for (Constructor<?> constructor : bean.getClass().getConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    Object[] dependencies = new Object[constructor.getParameterCount()];
                    Parameter[] parameters = constructor.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        Object dependency = beans.get(parameters[i].getType().getName());
                        if (parameters[i].isAnnotationPresent(Qualifier.class)) {
                            Qualifier qualifier = parameters[i].getAnnotation(Qualifier.class);
                            dependency = beans.get(qualifier.value());
                        }
                        dependencies[i] = dependency;
                    }
                    constructor.setAccessible(true);
                    constructor.newInstance(dependencies);
                }
            }
        }
    }

    public Object getBean(String name) {
        return beans.get(name);
    }
}