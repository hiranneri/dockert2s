package br.com.docker.t2s.repository;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryTyper {

    private QueryTyper() {

    }

    public static <T> List<T> getResultList(Query query){
        return query.getResultList();
    }

    public static <T> Set<T> getResultSet(Query query) {
        List<T> list = getResultList(query);
        return new HashSet<>(list);
    }

    public static <T> T getSingleResult(Query query) {
        return (T) query.getSingleResult();
    }

    public static <T> T getPossibleSingleResult(Query query) {
        List<T> results = getResultList(query);
        return results.size() >= 1 ? results.get(0) : null;
    }
}
