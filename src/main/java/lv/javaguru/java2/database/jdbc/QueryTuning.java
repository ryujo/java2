package lv.javaguru.java2.database.jdbc;

import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by algis on 16.22.7.
 */
public class QueryTuning {
    private int queryOffset;
    private int queryLimit;
    private List<Pair<String,Boolean>> sortOrder = new ArrayList<Pair<String,Boolean>>();

    public void setQueryOffset(int offset) {
        this.queryOffset = Math.max(offset,0);
    }
    public void setQueryLimit(int limit){
        this.queryLimit = limit;
    }
    public void setPagedQuery(int page){
        setQueryOffset(this.queryLimit * (page - 1));
    }

    public void resetSortOrder(){
        sortOrder.clear();
    }
    public void addSortOrderAsc(String column){
        addSortOrder(column,false);
    }
    public void addSortOrder(String column,Boolean direction){
        sortOrder.add(new Pair(column,direction));
    }

    public String tuneQuery(String sql){
        String tunedSql = sql +  getOrderByPhrase() + getPaginationPhrase();
        return tunedSql;
    }

    private String getPaginationPhrase(){
        if(queryLimit == 0){
            if(queryOffset == 0)
                return "";
            else
                return " LIMIT " + queryOffset + "," + Integer.MAX_VALUE;
        }
        else{
            if(queryOffset == 0)
                return " LIMIT " + queryLimit;
            else
                return " LIMIT " + queryOffset + "," + queryLimit;
        }
    }

    private String getOrderByPhrase(){
        if(sortOrder.isEmpty())
            return "";
        String orderByPhrase = " ORDER BY ";
        for (Pair<String, Boolean> sortColumn : sortOrder) {
            orderByPhrase += sortColumn.getKey() + " " + (sortColumn.getValue() ? "DESC" : "ASC") + " ,";
        }
        return orderByPhrase.substring(0, orderByPhrase.length() -1 );
    }


}
