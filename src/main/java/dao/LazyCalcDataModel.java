package dao;

import profiles.CalcProfile;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Kamalion
 */
public class LazyCalcDataModel extends LazyDataModel<CalcProfile>{
    
    private List<CalcProfile> datasource;
    
    public LazyCalcDataModel(List<CalcProfile> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<CalcProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        int dataSize = datasource.size();
        this.setRowCount(dataSize);
        return datasource;
    }
}
