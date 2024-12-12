package sa.searcher.composite;

import sa.searcher.simpleQuery.IQuery;


public abstract class CompositeQuery implements IQuery{
	protected IQuery query1;
	protected IQuery query2;
	
	protected CompositeQuery(IQuery firstQuery, IQuery secondQuery) {
		this.query1 = firstQuery;
		this.query2 = secondQuery;
	}

}
