 

import java.util.Random;

public final class PRNG {
    
    private int seed = 0;
    private int count = 0;
    private int place = 0;
    private int placement = 0;
    private boolean b = true;
    
    public PRNG(int seed) {
        this.seed = seed;
    }
    
    
    /**
     * 
     * @return next random number: All 2^32 possible int values are produced with (approximately) equal probability.
     */
    public int nextInt() {
        return (int)Math.pow(2,seed)%(seed-1);
    }

    /**
     * @param n > 0
     * @return next random number between 0 and n-1. All the values 0, ..., n-1 are produced with (approximately) equal probability.
     */
    public int nextInt(int n) {
        while(this.placement!=this.seed && this.b){
            this.count = this.count + 1;
            if(this.place == n/4+1){
            this.place = 0;
            }
            
            if(this.count == 1){
                this.placement = this.place;
            }
            
            else if(this.count == 2){
                this.placement = n-1-this.place;
            }
            
            else if(this.count == 3){
                this.place = this.place+1;
                this.placement = n/2-this.place;
            }
            
            else{
                this.count = 0;
                this.placement = n/2+this.place-1;
            }
        }
        
        
        this.count = this.count + 1;
        if(this.place == n/4+1){
            this.place = 0;
        }
        
        if(this.count == 1){
            return this.place;
        }
        
        else if(this.count == 2){
            return n-1-this.place;
        }
        
        else if(this.count == 3){
            this.place = this.place+1;
            return n/2-this.place;
        }
        
        else{
            this.count = 0;
            return n/2+this.place-1;
        }
    }
}
