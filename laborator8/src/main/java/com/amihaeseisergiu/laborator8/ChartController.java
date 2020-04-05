package com.amihaeseisergiu.laborator8;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChartController {
    
    public void create(List<Album> top)
    {
        StringBuilder albumIds = new StringBuilder();
        albumIds.append(top.get(0).getId());
        for(int i = 1; i < top.size(); i++)
        {
            albumIds.append(",");
            albumIds.append(top.get(i).getId());
        }
        
        ResultSet rs = Database.executeStatement("insert into charts (top) values (ranking(" + albumIds.toString() + "))");
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Chart findById(int id)
    {
        ResultSet rs = Database.executeStatement("select * from charts where id=" + id);
        try {
            while(rs.next())
            {
                int chartId = rs.getInt("id");
                BigDecimal[] albums = (BigDecimal[])rs.getArray("top").getArray();
                List<Album> albumList = new ArrayList<>();
                AlbumController ac = new AlbumController();
                for(int i = 0; i < albums.length; i++)
                {
                    albumList.add(ac.findById(Integer.valueOf(albums[i].toString())));
                }
                return new Chart(chartId, albumList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int getNumberOfCharts()
    {
        ResultSet rs = Database.executeStatement("select count(*) from charts");
        try {
            while(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public List<Chart> getAllCharts()
    {
        List<Chart> charts = new ArrayList<>();
        for(int i = 1; i <= getNumberOfCharts(); i++)
        {
            charts.add(findById(i));
        }
        return charts;
    }
    
    public void displayRanking()
    {
        Map<Integer, AtomicInteger> ranking = new LinkedHashMap<>();
        
        for(Chart chart : getAllCharts())
        {
            int chartSize = chart.getAlbums().size();
            for(int i = 0; i < chartSize; i++)
            {
                if(ranking.containsKey(chart.getAlbums().get(i).getArtistId()))
                    ranking.get(chart.getAlbums().get(i).getArtistId()).set(ranking.get(chart.getAlbums().get(i).getArtistId()).get() + chartSize - i);
                else ranking.put(chart.getAlbums().get(i).getArtistId(), new AtomicInteger(i));
            }
        }
        ranking.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.valueOf(e2.getValue().get()).compareTo(e1.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        ArtistController artistController = new ArtistController();
        for(var entry : ranking.entrySet())
        {
            Artist artist = artistController.findById(entry.getKey());
            System.out.println(artist + " " + entry.getValue().get());
        }
    }
}
