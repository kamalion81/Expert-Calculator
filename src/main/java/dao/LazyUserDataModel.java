package dao;

import profiles.UserProfile;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Kamalion
 */
public class LazyUserDataModel extends LazyDataModel<UserProfile>{
    
    public List<UserProfile> datasource;
    
    public LazyUserDataModel(List<UserProfile> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<UserProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        int dataSize = getDatasource().size();
        this.setRowCount(dataSize);
        return getDatasource();
    }

    /**
     * @return the datasource
     */
    public List<UserProfile> getDatasource() {
        return datasource;
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(List<UserProfile> datasource) {
        this.datasource = datasource;
    }
}
