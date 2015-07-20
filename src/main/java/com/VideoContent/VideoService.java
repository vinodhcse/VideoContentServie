package com.VideoContent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Request;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

@Path("/videoservice")
public class VideoService {
	
	@GET
	  @Produces("application/json")
	  public Response convertFtoC() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32)*5/9; 
		jsonObject.put("F Value", fahrenheit); 
		jsonObject.put("C Value", celsius);

		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

	
	@GET
	@Path("/videoURL")
	@Produces("application/json")
	public Response getVideoURL(@QueryParam("videoId") String  videoId, @QueryParam("language") String language) {
		JSONObject jsonObject = null;
		try {
			jsonObject = getVideoDetails(videoId, language);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
		//return jsonObject;
	}

	
	public JSONObject getVideoDetails(String videoId, String language) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("videoName", videoId);
		Connection.Response loginForm = Jsoup.connect("http://einthusan.com/movies/index.php?lang=tamil")
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").ignoreContentType(true)
	            .method(Connection.Method.GET)
	            .execute();

		Connection.Response loginFormResponse = Jsoup.connect("http://einthusan.com/etc/login.php")
	            .data("cookieexists", "false")
	            .data("username", "gvinodhcse")
	            .data("password", "vinkat")
	            .data("login", "Login")
	            //.data("submit", "Login")
	            .data("submit", "true")
	            .cookies(loginForm.cookies())
	            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").ignoreContentType(true)
	            .method(Connection.Method.POST).execute();
	           
		String videoUrl = "http://einthusan.com/movies/watch.php?lang="+ language +"&id="+videoId;
		Document document = Jsoup
				.connect(
						//"http://www.einthusan.com/movies/watch.php?tamilmoviesonline=Massu+Engira+Masilamani&lang=tamil&id=2669"
						videoUrl)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").ignoreContentType(true)
				.cookies(loginFormResponse.cookies())
				.get();
		//Request request = Jsoup.connect("http://173.192.200.84/movies_tamil/movie_high/2669.mp4?st=9_xBmJLljaiPFlIaFW0gkA&e=1436883389").request();
		
		
		
		
		//System.out.println(document);
		Elements newsHeadlines = document.select("#mp-itn b a");
		Elements authentication = document.select("#module_auth");
		Elements script = document.select("script"); // Get the script part
		System.out.println("script"+ script);
		boolean urlFound = false;
		for (Element tag : script){  
			
	        for (DataNode node : tag.dataNodes()) {
	            
	            String wholeData = node.getWholeData();
	            if (wholeData.contains(".mp4")) {
	            	System.out.println("Pattern matching" +node );
	            	
	            	String regex = "('http[^']*mp4?[^']*')";
	            	Pattern pattern = Pattern.compile(regex);
	            	Matcher matcher = pattern.matcher(wholeData);
	            	if (matcher.find()) {
	            		String url = matcher.group().replaceAll("'", "");
	            		jsonObject.put("videoUrl", url);
	            		urlFound = true;
	            		System.out.println("URL" + url);
	            		
	            		break;
	            	}
	            	//System.out.println(node.getWholeData());
	            }
	        }    
	        
	        if(urlFound) {
	        	break;
	        }
		}
		
		
		return jsonObject;	 
			 
	
		
	}
	
	@GET
	@Path("/playList")
	@Produces("application/json")
	public Response getPlayListCategories(@QueryParam("organize") String  organize, @QueryParam("language") String  language, @QueryParam("videoType") String  videoType,@QueryParam("maxRecords") int  maxRecords	) {
		List<String> filterList = new ArrayList<String>();
		try {
			filterList = getPlayListDetails(organize, language, videoType, maxRecords);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return Response.status(200).entity(filterList).type(MediaType.APPLICATION_JSON).build();
		//return jsonObject;
	}
	
	
	public List getPlayListDetails(String organize,String language, String videoType, int maxRecords) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("languageName", language);
		List<String> filterList = new ArrayList<String>();
		String siteUrl ="http://einthusan.com";
		if ("HD".equalsIgnoreCase(videoType)) {
			siteUrl += "/movies";
		} else if ("BLURAY".equalsIgnoreCase(videoType)) {
			siteUrl += "/bluray";
		} else {
			siteUrl += "/movies";
		}
		siteUrl+= "/index.php?lang="+language;
		String requestUrl = siteUrl +"&organize="+organize+"&org_type="+organize;
		Document document = Jsoup
				.connect(
						//"http://www.einthusan.com/movies/watch.php?tamilmoviesonline=Massu+Engira+Masilamani&lang=tamil&id=2669"
						requestUrl)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").ignoreContentType(true)
				.get();
		//Request request = Jsoup.connect("http://173.192.200.84/movies_tamil/movie_high/2669.mp4?st=9_xBmJLljaiPFlIaFW0gkA&e=1436883389").request();
		
		//System.out.println(document);
		//Elements playListFilters = document.select("#video-organizer-options-wrapper").select(".video-organizer-element-wrapper");
		Elements playListFilters = document.select(".video-organizer-element-wrapper").select("a");
		
		
		
		boolean urlFound = false;
		int recordsAdded =0;
		for (Element tag : playListFilters){  
			String filter =	tag.text();
			if (null != filter && filter != organize) {
				if (filter.contains(" ")){
					filter = filter.replaceAll(" ", "%20");
				}
				filterList.add(filter);
				recordsAdded +=1;
				if (recordsAdded > maxRecords) {
					break;
				}
			}
		   
		}
		
		
		
		
		return filterList;	 
			 
	
		
	}


	@GET
	@Path("/videoList")
	@Produces("application/json")
	public Response getVideoList(@QueryParam("language") String language, @QueryParam("videoType") String videoType,
			@QueryParam("pageNumber") int pageNumber, @QueryParam("organize") String organize,
			@QueryParam("filter") String filter) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("language", language);
		jsonObject.put("psgeNumber", pageNumber);
		List<VideoVO> videoList = new ArrayList<VideoVO>();
		String siteUrl ="http://einthusan.com";
		if ("HD".equalsIgnoreCase(videoType)) {
			siteUrl += "/movies";
		} else if ("BLURAY".equalsIgnoreCase(videoType)) {
			siteUrl += "/bluray";
		} else {
			siteUrl += "/movies";
		}
		siteUrl+= "/index.php?lang="+language;
		
		if (pageNumber > 0) {
			siteUrl += "&page="+pageNumber;
			
		}
	
		if (null!= organize && !organize.isEmpty()) {
			siteUrl += "&org_type="+organize;
		} else {
			siteUrl += "&org_type=Activity";
		}
		if (null!= filter && !filter.isEmpty()) {
			siteUrl += "&filtered="+filter;
		} else {
			siteUrl += "&filtered=RecentlyPosted";
		}
		//String videoUrl = "http://einthusan.com/movies/watch.php?lang="+ language +"&id="+videoId;
		Document document = Jsoup
				.connect(
						//"http://www.einthusan.com/movies/watch.php?tamilmoviesonline=Massu+Engira+Masilamani&lang=tamil&id=2669"
						siteUrl)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").ignoreContentType(true)
				//.cookies(loginFormResponse.cookies())
				.get();
		//Request request = Jsoup.connect("http://173.192.200.84/movies_tamil/movie_high/2669.mp4?st=9_xBmJLljaiPFlIaFW0gkA&e=1436883389").request();
		
		
		
		
		//System.out.println(document);
		Elements newsHeadlines = document.select("#mp-itn b a");
		//Elements authentication = document.select(".video-listing");
		Elements videoObjects = document.select(".video-listing").select(".video-object-wrapper");
		
		Elements pagesList = document.select(".numerical-nav").select("a");
		
		
		
		
		
		int totalPages = pagesList.size();
		
		
		boolean urlFound = false;
		for (Element tag : videoObjects){  
			String  videoUrl = tag.select(".video-object-thumb a").attr("href");
			videoUrl = videoUrl.replace("..", "http://einthusan.com/");
			String imageUrl = tag.select(".video-object-thumb a img").attr("src");
			String movieTitle = tag.select(".video-object-details a.movie-title").html();
			String shortDescription1 = tag.select(".movie-description-wrapper .seo-h1").html();
			String shortDescription2 = tag.select(".desc_body").html();
			String description = tag.select(".desc_body").html();
			//tag.select(".video-object-thumb a").attr("href").substring(tag.select(".video-object-thumb a").attr("href").lastIndexOf("=")+1)
			String videoId = videoUrl.substring(videoUrl.lastIndexOf("=")+1);
			if (videoUrl.indexOf("bluray") > -1) {
				videoId = videoUrl.substring(videoUrl.indexOf("id=")+ 3,videoUrl.lastIndexOf("&"));
			}

			
			VideoVO video = new VideoVO();
			video.setVideoName(movieTitle);
			video.setVideoId(videoId);
			video.setHdPosterUrl(imageUrl);
			video.setSdPosterUrl(imageUrl);
			video.setDescription(description);
			video.setShortDescriptionLine1(shortDescription1);
			video.setShortDescriptionLine2(shortDescription2);
			video.setContetnType("episode");
			video.setLength("1280");
			video.setTitle(movieTitle);
			video.setMovieURL(videoUrl);
			video.setTotalPagesInCategory(totalPages);
			videoList.add(video);
	        
	        
	       
		}
		
		
		//return jsonObject;	 
		//String result = jsonObject.toString();
		return Response.status(200).entity(videoList).type(MediaType.APPLICATION_JSON).build(); 
	
		
	}
	
	public VideoVO createVideoItem(Node node) {
		VideoVO videoItem = new VideoVO();
		
		return videoItem;
	}
}
