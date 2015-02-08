package cz.gattserver.pubs.util;

import java.util.List;

public interface MappingService {

    <T, U> List<U> map(final List<T> source, final Class<U> destType);

    void map(Object source, Object destination);

    <U> U map(Object source, Class<U> destType);

}
