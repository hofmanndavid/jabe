package io.hdavid.util;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataChangeEvent;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.SerializableSupplier;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid;
import io.ebean.typequery.TQRootBean;
import io.hdavid.entity.BasicEbeanEntity;
import lombok.SneakyThrows;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO do we need this helper?
public class DataToGridLinker implements Grid.FetchItemsCallback<QuerySortOrder>, SerializableSupplier<Integer> {

    private final Supplier<TQRootBean> queryBeanSupplier;
    private final Class entity;
    public DataToGridLinker(Grid grid, Supplier<TQRootBean> qbs) {
        entity = qbs.get().getBeanType();
        queryBeanSupplier = qbs;
        grid.setDataProvider(this::fetchItems, this::get);
        CallbackDataProvider<BasicEbeanEntity, ?> dp = (CallbackDataProvider)grid.getDataProvider();
        dp.addDataProviderListener(dce -> {
            if (dce instanceof DataChangeEvent.DataRefreshEvent) {
                BasicEbeanEntity bee = (BasicEbeanEntity) ((DataChangeEvent.DataRefreshEvent) dce).getItem();
                bee.refresh();
            }
        });
    }

    @Override
    @SneakyThrows
    public Stream fetchItems(List<QuerySortOrder> sortOrder, int offset, int limit) {

        // TODO fixme, verificar si sortorder contiene una propiedad que no forma parte del entity, para tirar una excepcion...

        TQRootBean qb = queryBeanSupplier.get();
        if (!sortOrder.isEmpty()){
            qb.order(sortOrder.stream()
                    .map(qso -> qso.getSorted() + (qso.getDirection() == SortDirection.ASCENDING ? " asc" : " desc"))
                    .collect(Collectors.joining(", ")));
        }

        qb.setMaxRows(limit);
        qb.setFirstRow(offset);
        return qb.findList().stream();
    }

    @Override
    @SneakyThrows
    public Integer get() {
            return queryBeanSupplier.get().findCount();
    }


}
