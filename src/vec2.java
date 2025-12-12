public class vec2 {
    public float x;
    public float y;
    public vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public vec2 add(float x, float y){
        return new vec2(this.x+x,this.y+y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }


}
