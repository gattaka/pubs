package cz.gattserver.pubs.util;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MappingServiceDozerImpl implements MappingService {

	@Autowired
	private Mapper mapper;

	@Override
	public <T, U> List<U> map(final List<T> source, final Class<U> destType) {
		final List<U> dest = new ArrayList<U>();
		for (T element : source) {
			if (element != null) {
				dest.add(mapper.map(element, destType));
			}
		}
		return dest;
	}

	@Override
	public void map(Object source, Object destination) {
		mapper.map(source, destination);
	}

	@Override
	public <U> U map(Object source, Class<U> destType) {
		if (source == null)
			return null;
		return mapper.map(source, destType);
	}

}
