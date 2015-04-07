package discussion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class Discussion {

	public String name;
	public String firstSnippet;
	public List<String> snippetKeys;
	private List<Snippet> snippets;
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static AsyncDatastoreService asyncDatastore = DatastoreServiceFactory.getAsyncDatastoreService();
	
	public Discussion(Entity e){
		name = (String) e.getProperty("name");
		firstSnippet = (String) e.getProperty("firstSnippet");
		snippetKeys = (List<String>) e.getProperty("snippetKeys");
	}
	
	public static void createNewDiscussion(String name, String snippetStartId, String jsonSnippets){
		Entity e = new Entity("Discussion", name);
		e.setUnindexedProperty("firstSnippet", name +"-"+snippetStartId);
		e.setUnindexedProperty("name", name);
		List<Snippet> snippets = Snippet.createNewSnippetsOfType(name, jsonSnippets);
		List<String> datastoreSnippetKeys = new ArrayList<String>();
		for(Snippet s : snippets){
			s.save();
			datastoreSnippetKeys.add(s.getDatastoreKey());
		}
		e.setUnindexedProperty("snippetKeys", datastoreSnippetKeys);
		asyncDatastore.put(e);
	}
	
	public static Discussion getDiscussion(String name){
		try {
			Entity e = datastore.get(KeyFactory.createKey("Discussion", name));
			return new Discussion(e);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<Snippet> getSnippets(){
		if (this.snippets != null){
			return this.snippets;
		}
		
		List<Future<Entity>> futures = new ArrayList<Future<Entity>>();
		for(String key : snippetKeys){
			futures.add(asyncDatastore.get(KeyFactory.createKey("Snippet",key)));
		}
		List<Snippet> snippets = new ArrayList<Snippet>();
		for(Future<Entity> f : futures){
			try {
				snippets.add(new Snippet(f.get()));
			} catch (InterruptedException e) {
				// Log, but don't do antyhing drastic... we are okay without some data if interuppted.
				e.printStackTrace();
			} catch (ExecutionException e) {
				// Log, but don't do antyhing drastic... we are okay without some data if interuppted.
				e.printStackTrace();
			}
		}
		this.snippets = snippets;
		return snippets;
	}
	
}
