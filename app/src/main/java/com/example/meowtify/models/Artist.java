package com.example.meowtify.models;

import java.util.List;

public class Artist {
    public int followers;
    public List<genre> genres;
    public String name;
    public int popularity;

}
enum  genre{
      dance_pop,latin,miami_hip_hop,pop,pop_dance,pop_rap
}
