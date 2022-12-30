package si.fri.rso.skupina1.ponudniki.api.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudbaBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class PonudbaQueries {

    @Inject
    private PonudbaBean ponudbaBean;

    @GraphQLQuery
    public PaginationWrapper<Ponudba> allPonudbe(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                 @GraphQLArgument(name = "sort") Sort sort,
                                                 @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(ponudbaBean.getPonudbe(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Ponudba getPonudba(@GraphQLArgument(name = "id") Integer id) {
        return ponudbaBean.getPonudba(id);
    }
}
