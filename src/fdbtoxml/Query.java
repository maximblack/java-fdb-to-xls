package fdbtoxml;

import java.util.ArrayList;
import java.util.Iterator;

public class Query {

	public final String queryName;

	public final String queryText;

	private static ArrayList<Query> queries = new ArrayList<Query>();

	public Query(String queryName, String query) {

		this.queryName = queryName;

		this.queryText = query;

	}

	// All queries here...
	public static ArrayList<Query> getQueries() {

		queries.add(new Query(
				"View all tables",
				"select rdb$relation_name from rdb$relations where rdb$view_blr is null and (rdb$system_flag is null or rdb$system_flag = 0);"));

		queries.add(new Query("SALES table", "SELECT * FROM SALES"));
		queries.add(new Query("SALARY table", "SELECT * FROM SALARY_HISTORY"));

		return queries;

	}

	public static String getQuery(String queryName) {

		Iterator<Query> iterator = queries.iterator();

		while (iterator.hasNext()) {

			Query query = iterator.next();

			if (query.queryName == queryName)
				return query.queryText;

		}

		return null;

	}

}
