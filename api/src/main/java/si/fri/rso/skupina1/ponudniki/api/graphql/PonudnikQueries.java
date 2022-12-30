package si.fri.rso.skupina1.ponudniki.api.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.skupina1.ponudniki.lib.Ponudnik;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudnikBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class PonudnikQueries {

    @Inject
    private PonudnikBean ponudnikBean;

    @GraphQLQuery
    public PaginationWrapper<Ponudnik> allPonudniki(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                 @GraphQLArgument(name = "sort") Sort sort,
                                                 @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(ponudnikBean.getPonudniki(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Ponudnik getPonudnik(@GraphQLArgument(name = "id") Integer id) {
        return ponudnikBean.getPonudnik(id);
    }
}
