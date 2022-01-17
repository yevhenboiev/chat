package ru.simbirsoft.chat.utills;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.entity.Videos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@Scope("prototype")
public class SearchVideoYoutube {

    private final String apikey;
    private YouTube youtube;

    public SearchVideoYoutube(@Value("${youtube.apikey}") String apikey) {
        if (youtube == null) {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                    httpRequest -> {
                    }).setApplicationName("api-chat-simbirsoft").build();
        }
        this.apikey = apikey;
    }

    public String getChannelId(String channel) throws IOException {
        String channelId = null;
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apikey);
        search.setQ(channel);
        search.setType("channel");
        search.setMaxResults(501L);

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> searchResultIterator = searchResultList.iterator();
            if (searchResultIterator.hasNext()) {
                while (searchResultIterator.hasNext()) {
                    SearchResult singleChannel = searchResultIterator.next();
                    if (singleChannel.getId().getKind().equals("youtube#channel") & singleChannel.getSnippet().getTitle().equals(channel)) {
                        channelId = singleChannel.getId().getChannelId();
                        break;
                    }
                }
            }
        }
        return channelId;
    }

    public List<Videos> getVideoList(String movie, String channelId, Long resultCount, Boolean sort) throws IOException {
        List<Videos> videoList = new ArrayList<>();

        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apikey);
        if (!movie.isEmpty()) {
            search.setQ(movie);
        }

        search.setType("video");

        if (sort) {
            search.setOrder("date");
        }

        if (!channelId.isEmpty()) {
            search.setChannelId(channelId);
        }

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 0) {
            search.setMaxResults(resultCount);
        }

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> searchResultIterator = searchResultList.iterator();
            if (searchResultIterator.hasNext()) {
                while (searchResultIterator.hasNext()) {
                    SearchResult singleVideo = searchResultIterator.next();
                    ResourceId rId = singleVideo.getId();

                    if (rId.getKind().equals("youtube#video")) {
                        Videos videos = new Videos();
                        videos.setId(rId.getVideoId());
                        videos.setTitle(singleVideo.getSnippet().getTitle());
                        videos.setPublished(new Date(singleVideo.getSnippet().getPublishedAt().getValue()));
                        YouTube.Videos.List youtubeVideos = youtube.videos().list("id,snippet,player,contentDetails,statistics")
                                .setId(rId.getVideoId());
                        youtubeVideos.setKey(apikey);
                        VideoListResponse videoListResponse = youtubeVideos.execute();

                        if(!videoListResponse.getItems().isEmpty()) {
                            Video video = videoListResponse.getItems().get(0);
                            videos.setViewCount(video.getStatistics().getViewCount());
                            videos.setLikeCount(video.getStatistics().getLikeCount());
                        }
                        videoList.add(videos);
                    }
                }
            }
        }
        return videoList;
    }
}
