package com.VideoContent;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class VideoVO {
	
	@XmlElement(name="VideoId")
	private String videoId;
	
	@XmlElement(name="VideoName")
	private String videoName;
	
	@XmlElement(name="Title")
	private String title;
	
	@XmlElement(name="ContentType")
	private String contetnType = "episode";
	
	@XmlElement(name="SDPosterUrl")
	private String sdPosterUrl;
	
	@XmlElement(name="HDPosterUrl")
	private String hdPosterUrl;
	
	@XmlElement(name="IsHD")
	private boolean hd;
	
	@XmlElement(name="HDBranded")
	private boolean hdBranded;
	
	@XmlElement(name="ShortDescriptionLine1")
	private String shortDescriptionLine1;
	
	@XmlElement(name="ShortDescriptionLine2")
	private String shortDescriptionLine2;

	@XmlElement(name="Description")
	private String description;
	
	@XmlElement(name="Rating")
	private String rating;
	
	@XmlElement(name="StarRating")
	private String starRating;
	
	@XmlElement(name="Length")
	private String length;
	
	@XmlElement(name="Categories")
	private List<String> categories;
	
	@XmlElement(name="URL")
	private String movieURL;
	
	@XmlElement(name="VideoURL")
	private String videoURL;
	
	@XmlElement(name="totalPagesInCategory")
	private int totalPagesInCategory;
	
	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContetnType() {
		return contetnType;
	}

	public void setContetnType(String contetnType) {
		this.contetnType = contetnType;
	}

	public String getSdPosterUrl() {
		return sdPosterUrl;
	}

	public void setSdPosterUrl(String sdPosterUrl) {
		this.sdPosterUrl = sdPosterUrl;
	}

	public String getHdPosterUrl() {
		return hdPosterUrl;
	}

	public void setHdPosterUrl(String hdPosterUrl) {
		this.hdPosterUrl = hdPosterUrl;
	}

	public boolean isHd() {
		return hd;
	}

	public void setHd(boolean hd) {
		this.hd = hd;
	}

	public boolean isHdBranded() {
		return hdBranded;
	}

	public void setHdBranded(boolean hdBranded) {
		this.hdBranded = hdBranded;
	}

	public String getShortDescriptionLine1() {
		return shortDescriptionLine1;
	}

	public void setShortDescriptionLine1(String shortDescriptionLine1) {
		this.shortDescriptionLine1 = shortDescriptionLine1;
	}

	public String getShortDescriptionLine2() {
		return shortDescriptionLine2;
	}

	public void setShortDescriptionLine2(String shortDescriptionLine2) {
		this.shortDescriptionLine2 = shortDescriptionLine2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getMovieURL() {
		return movieURL;
	}

	public void setMovieURL(String movieURL) {
		this.movieURL = movieURL;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public int getTotalPagesInCategory() {
		return totalPagesInCategory;
	}

	public void setTotalPagesInCategory(int totalPagesInCategory) {
		this.totalPagesInCategory = totalPagesInCategory;
	}
	
	
	
	
	
	
}
