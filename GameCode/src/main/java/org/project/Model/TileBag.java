package org.project.Model;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * Class that initializes the bag that contains all the tiles
 */

public class TileBag {
    @Expose
    private Set<Tile> bag;

    public TileBag() {
        bag= new HashSet<>();
        initializeBag();
    }

    /**
     * The method creates and adds 22 tiles of each of the available colors to the tile bag.
     */
    public void initializeBag(){
        int j=0; //index of tile
        for (Color color:Color.values()) {

            for(int i=0; i<22; i++) {
                Tile tile= new Tile(color,j);
                bag.add(tile);
                j++;
            }
        }
    }

    /**
     * @param n number of tiles that we want to pick
     * @return Set of the picked tiles
     */
    public Set<Tile> randomPick(int n) {

        Set<Tile> tilePicked= new HashSet<Tile>();
        Random random= new Random();

        for(int i=0;i<n;i++) {

           if(bag.size()>0){
               //generation of a random number
               int randomIndex= random.nextInt(bag.size());

               //conversion from set to array
               Tile[] array=bag.toArray(new Tile[bag.size()]);

               //getting the element from the array
               Tile randomTile= array[randomIndex];

               //remove the element from the bag and add the element to the set
               bag.remove(randomTile);
               tilePicked.add(randomTile);
           }
       }
       return tilePicked;
    }

    /**
     * @return a single picked tile
     */
    public Tile pickSingle(){
        Random random= new Random();
        int randomIndex= random.nextInt(bag.size());
        //conversion from set to array
        Tile[] array=bag.toArray(new Tile[bag.size()]);
        //getting the element from the array
        Tile randomTile= array[randomIndex];
        //remove the element from the bag and add the element to the set
        bag.remove(randomTile);
        return randomTile;
    }

    public Set<Tile> getBag() {
        return bag;
    }
}
