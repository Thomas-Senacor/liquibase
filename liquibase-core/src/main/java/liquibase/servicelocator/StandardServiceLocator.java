package liquibase.servicelocator;

import liquibase.Scope;
import liquibase.exception.ServiceNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

public class StandardServiceLocator implements ServiceLocator {

    @Override
    public <T> List<T> findInstances(Class<T> interfaceType) throws ServiceNotFoundException {
        List<T> allInstances = new ArrayList<>();

        for (T t : ServiceLoader.load(interfaceType, Scope.getCurrentScope().getClassLoader(true))) {
            if (t instanceof ServiceActivator) {
                Object actualService = ((ServiceActivator) t).activate();
                if (actualService == null) {
                    Scope.getCurrentScope().getLog(getClass()).info("No " + interfaceType.getName() + " plugin activated by " + t.getClass().getName());
                } else {
                    allInstances.add((T) actualService);
                }
            } else {
                allInstances.add(t);
            }
        }

        return Collections.unmodifiableList(allInstances);

    }

    @Override
    public <T> List<Class<? extends T>> findClasses(Class<T> interfaceType) throws ServiceNotFoundException {
        List<Class<T>> allInstances = new ArrayList<>();

        for (T t : findInstances(interfaceType)) {
            allInstances.add((Class<T>) t.getClass());
        }

        return Collections.unmodifiableList(allInstances);
    }
}
