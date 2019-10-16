package com.hce.core.db;

public class RoutingDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDetermineCurrentLookupKey();
	}
}
