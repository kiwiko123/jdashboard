package com.kiwiko.jdashboard.webapp.clients.users.api.parameters;

import java.util.ArrayList;
import java.util.List;

public class GetUsersQuery {
    private List<GetUserQuery> queries = new ArrayList<>();

    public List<GetUserQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<GetUserQuery> queries) {
        this.queries = queries;
    }
}
