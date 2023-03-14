package org.example;

public class TileBag {

    private Set<Tile> bag;


    public TileBag() {
        bag= new HashSet<>();
    }

    public void initializeBag(){
        for (Color color:Color.values())
        {
            for(int i=0; i<22; i++)
            {
                Tile tile= new Tile(Color.values,i);
                bag.add(tile);
            }
        }
    }

    public Set<Tile> randomPick(int n)
    {
        
    }
}
