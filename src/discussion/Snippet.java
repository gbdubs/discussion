package discussion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Snippet{
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static AsyncDatastoreService asyncDatastore = DatastoreServiceFactory.getAsyncDatastoreService();
	public String discussion;
	public long id;
	public String color;
	public String summary;
	public String response;
	public String prompt;
	public List<String> answerTexts;
	public List<Long> answerIds;
	
	public static List<Snippet> createNewSnippetsOfType(String discussionName, String rawData) {
		Type listType = new TypeToken<ArrayList<Snippet>>(){}.getType();
		List<Snippet> toReturn = new Gson().fromJson(rawData, listType);
		return toReturn;
	}

	public Snippet(Entity e){
		this.discussion = (String) e.getProperty("discussion");
		this.id = (long) e.getProperty("id");
		this.color = (String) e.getProperty("color");
		this.summary = ((Text) e.getProperty("summary")).getValue();
		this.response = ((Text) e.getProperty("response")).getValue();
		this.prompt = (String) e.getProperty("prompt");
		this.answerTexts = (List<String>) e.getProperty("answerTexts");
		this.answerIds = (List<Long>) e.getProperty("answerIds");
	}
	
	// No-args constructor for GSON
	public Snippet(){
		
	}
	
	public Snippet(String discussion, int id, String color, List<String> answerTexts, List<Long> answerIds){
		this.discussion = discussion;
		this.id = id;
		this.color = color;
		this.answerTexts = answerTexts;
		this.answerIds = answerIds;
	}
	
	public void save() {
		Key key = KeyFactory.createKey("Snippet", getDatastoreKey());
		Entity e;
		try {
			e = datastore.get(key);
		} catch (EntityNotFoundException enfe) {
			e = new Entity(key);
		}
		e.setProperty("discussion", discussion);
		e.setUnindexedProperty("id", new Long(id));
		e.setUnindexedProperty("color", color);
		e.setUnindexedProperty("summary", new Text(summary));
		e.setUnindexedProperty("response", new Text(response));
		e.setUnindexedProperty("prompt", prompt);
		e.setUnindexedProperty("answerTexts", answerTexts);
		e.setUnindexedProperty("answerIds", answerIds);
		
		asyncDatastore.put(e);
	}

	public static List<Snippet> getSnippetsForDiscussion(String discussion){
		Query q = new Query("Snippet");
		Filter filter = new FilterPredicate("discussion", FilterOperator.EQUAL, discussion);
		q.setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		List<Snippet> snippets = new ArrayList<Snippet>();
		for(Entity e : pq.asIterable()){
			snippets.add(new Snippet(e));
		}
		return snippets;
	}
	
	public String getDatastoreKey(){
		return discussion+"-"+id;
	}
	
	public String toString(){
		return new Gson().toJson(this);
	}
	
	public String getDiscussion(){
		return this.discussion;
	}
	
	public String getId(){
		return "" + this.id;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getSummary(){
		return this.summary;
	}
	
	public String getResponse(){
		return this.response;
	}
	
	public List<String> getAnswerTexts(){
		return this.answerTexts;
	}
	
	public List<Long> getAnswerIds(){
		return this.answerIds;
	}
	
	public String getPrompt(){
		return this.prompt;
	}

	public static Snippet getSnippet(String key){
		try{
			return new Snippet(datastore.get(KeyFactory.createKey("Snippet", key)));
		} catch (EntityNotFoundException enfe){
			return null;
		}
	}
	
	public boolean equals(Object s){
		if (! (s instanceof Snippet)){
			return false;
		} else {
			return ((Snippet) s).getDatastoreKey().equals(getDatastoreKey());
		}	
	}
	
	public String getRandomSecondaryColor(){
		String[] colors = {"#2A044A", "#A0C55F", "#7AB317", "#0D6759", "#0B2E59"};
		int i = (int) (Math.random() * colors.length);
		while (colors[i].equals(color)){
			i = (int) (Math.random() * colors.length);
		}
		return colors[i];
	}
	
	public String getLeftCoordinate(){
		return "" + ((id * 21) % 100 - 25) + "vw";
	}
	public String getTopCoordinate(){
		return "" + ((id * 34) % 90 - 10) + "vh";
	}
}
